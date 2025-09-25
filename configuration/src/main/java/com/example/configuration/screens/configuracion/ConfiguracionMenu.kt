package com.example.configuration.screens.configuracion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui_theme.ui.theme.ProyectoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuConfig(
    onNavigateBack: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onEmprendimientosClick: () -> Unit = {},
    onHistorialClick: () -> Unit = {},
    onConfigClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth().fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton( onClick =  { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                },
                title = {
                    Text(
                        "Mi perfil",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding).padding(12.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().size(85.dp)
                    .clickable { onPerfilClick() },
                colors =  CardDefaults.cardColors(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp),
            ) {
                Text("Mi perfil",
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 4.dp),
                    fontSize = 17.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text("Consulta tu información personal y editala cuando lo necesites.",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                    lineHeight = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth().size(85.dp)
                    .clickable { onEmprendimientosClick() },
                colors =  CardDefaults.cardColors(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
            ) {
                Text("Mis emprendimientos",
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 4.dp),
                    fontSize = 17.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text("Consulta tus emprendimientos y editalos cuando lo necesites.",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                    lineHeight = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth().size(85.dp)
                    .clickable { onHistorialClick() },
                colors =  CardDefaults.cardColors(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
            ) {
                Text("Historial",
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 4.dp),
                    fontSize = 17.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text("Consulta tu acciones a lo largo del uso de la app.",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                    lineHeight = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth().size(85.dp)
                    .clickable { onConfigClick() },
                colors =  CardDefaults.cardColors(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
            ) {
                Text("Configuración",
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 4.dp),
                    fontSize = 17.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text("Ajustes y preferencias de la aplicación.",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                    lineHeight = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true/*, showSystemUi = true*/)
@Composable
fun muestra() {
    ProyectoTheme(darkTheme = false) {
        MenuConfig()
    }
}
