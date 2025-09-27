package com.example.configuration.screens.historial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data_core.database.Historial
import com.example.data_core.database.User
import com.example.data_core.model.HistorialModel
import com.example.data_core.model.UserModel
import com.example.ui_theme.ui.theme.ProyectoTheme

@Composable
fun cardAccion(accion: Historial){
    Card(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        colors =  CardDefaults.cardColors(MaterialTheme.colorScheme.onSurfaceVariant),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text(accion.accion, fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Italic, fontSize = 13.sp,
            modifier = Modifier.padding(start = 5.dp, top = 4.dp, bottom = 2.dp, end = 5.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer, lineHeight = 15.sp
        )
        Text(accion.fecha, fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic, fontSize = 10.sp,
            modifier = Modifier.padding(start = 5.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun historialUsuario(onBackPage: () -> Unit = {}, viewModel: HistorialModel, viewModelUser: UserModel){
    var userState by remember { mutableStateOf<User?>(null) }
    var hasError by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        try {
            val user = viewModelUser.getCurrentUser()
            if (user != null) {
                userState = user

            } else {
                hasError = "No se pudo cargar el usuario"
            }
        } catch (e: Exception) {
            hasError = "Ocurrió un error al cargar el usuario"
        }
    }

    val historial by viewModel.historial.collectAsState()
    val historialFiltre = historial.filter {it.idUsuario == userState?.id }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onBackPage ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                title = {
                    Text("Historial de acciones", modifier = Modifier.fillMaxWidth().padding(end = 3.dp),
                        textAlign = TextAlign.Right,
                        style =
                            TextStyle(
                                fontSize = 25.sp,
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.ExtraBold
                            )
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(2.dp).fillMaxSize()) {
            if (historialFiltre.isEmpty()) {
                Text(
                    text = "No has realizado acciones.",
                    style = TextStyle(
                        fontSize = 20.sp, textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth().fillMaxSize().padding(top = 210.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.padding(5.dp).padding(top = 10.dp)

                ) {
                    items(historialFiltre){accion ->
                        cardAccion(accion)
                    }
                }
            }
        }

    }
}

//@Preview(showBackground = true/*, showSystemUi = true*/)
//@Composable
//fun muestra() {
//    ProyectoTheme(darkTheme = false) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            historialUsuario()
//        }
//    }
//}
