package com.example.emprendimientos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data_core.model.EmprendimientoModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectedScreen(
    category: String,
    onLogoClick: () -> Unit = {},
    onEmprendimientosClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onSelectEmprendimiento: (String) -> Unit = {},
    onBackToAll: () -> Unit = {},
    viewModel: EmprendimientoModel
) {
    val emprendimientos by viewModel.emprendimientosFirebase.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEmprendimientosFromFirebase()
    }

    val filteredEmprendimientos = emprendimientos.filter { emprendimiento ->
        emprendimiento.categoria.equals(category, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            Column {
                topMenu(
                    onLogoClick = onLogoClick,
                    onEmprendimientosClick = onEmprendimientosClick,
                    onPerfilClick = onPerfilClick
                )
                CategoryHeader(
                    categoryName = category,
                    emprendimientosCount = filteredEmprendimientos.size,
                    onBackToAll = onBackToAll
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredEmprendimientos) { emprendimiento ->
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

@Composable
private fun CategoryHeader(
    categoryName: String,
    emprendimientosCount: Int,
    onBackToAll: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = categoryName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "$emprendimientosCount emprendimientos encontrados",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}