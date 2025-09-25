package com.example.proyecto.ui.screensTest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data_core.database.User
import com.example.data_core.model.UserModel
import kotlinx.coroutines.delay
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.authentication.R
import com.example.ui_theme.ui.theme.ProyectoTheme

@Composable
fun HomeTest(viewModel: UserModel, onLogoutClick: () -> Unit = {}) {
    // null = cargando, User? = cargado, false = error/ningún usuario
    var userState by remember { mutableStateOf<User?>(null) }
    var hasError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val user = viewModel.getCurrentUser()
            if (user != null) {
                userState = user
            } else {
                hasError = true
            }
        } catch (e: Exception) {
            hasError = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when {
            hasError -> Text("No se pudo cargar el usuario.")
            userState != null -> {
                Text("Data: ${userState!!}")
                Text("Id: ${userState!!.id}")
                Text("Nombre: ${userState!!.name}")
                Text("Email: ${userState!!.email}")
                Text("Contraseña: ${userState!!.password}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    viewModel.logout()
                    onLogoutClick()
                }) {
                    Text("Cerrar sesión")
                }

            }
            else -> Text("Cargando usuario...")
        }
    }
}


@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(key1 = true) {
        delay(5500)
        navController.popBackStack()
        navController.navigate("login")
    }
    InicioLogo()
}

@Composable
fun InicioLogo(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(R.drawable.logospotme),
            contentDescription = "Icono de SpotMe",
            modifier = Modifier.size(165.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text("SpotMe", fontSize = 55.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MPI(){
    ProyectoTheme(darkTheme = true){
        InicioLogo()
    }
}