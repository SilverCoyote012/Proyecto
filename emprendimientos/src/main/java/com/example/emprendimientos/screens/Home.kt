package com.example.emprendimientos.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.example.emprendimientos.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data_core.database.Emprendimiento
import com.example.data_core.model.EmprendimientoModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topMenu(
    onLogoClick: () -> Unit = {},
    onEmprendimientosClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
) {
    TopAppBar(
        title = {  },
        navigationIcon = {
            IconButton(onClick = { onLogoClick() }) {
                Icon(
                    painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        actions = {
            IconButton(onClick = { onEmprendimientosClick() }) {
                Icon(
                    painterResource(R.drawable.mercado_catalogo),
                    contentDescription = "Emprendimientos",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            IconButton(onClick = { onPerfilClick() }) {
                Icon(
                    painterResource(R.drawable.perfil),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmprendimientoItem(
    emprendimiento: Emprendimiento,
    onSelectEmprendimiento: (String) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onSelectEmprendimiento(emprendimiento.id) },
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Box(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = emprendimiento.imagen,
                contentDescription = "Imagen del emprendimiento",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
            )
            Text(
                text = emprendimiento.nombreEmprendimiento,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp),
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(0f, 4f),
                        blurRadius = 5f
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 16.dp, 12.dp)
        ) {
            Column {
                Text(
                    text = emprendimiento.categoria,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = emprendimiento.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 2
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmprendimientosScreen(
    viewModel: EmprendimientoModel,
    onLogoClick: () -> Unit = {},
    onEmprendimientosClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onSelectEmprendimiento: (String) -> Unit = {},
) {
    val emprendimientos by viewModel.emprendimientosFirebase.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEmprendimientosFromFirebase()
    }

    Scaffold(
        topBar = {
            topMenu(
                onLogoClick = onLogoClick,
                onEmprendimientosClick = onEmprendimientosClick,
                onPerfilClick = onPerfilClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .background(Color.White)
        ) {
            item {
                Text(
                    text = "Emprendimientos Generales",
                    style = MaterialTheme.typography.titleLarge,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            items(emprendimientos) { emprendimiento ->
                EmprendimientoItem(
                    emprendimiento = emprendimiento,
                    onSelectEmprendimiento = { id ->
                        onSelectEmprendimiento(id)
                    }
                )
            }
        }
    }
}
