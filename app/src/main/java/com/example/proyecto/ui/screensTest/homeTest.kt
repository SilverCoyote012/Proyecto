package com.example.proyecto.ui.screensTest

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data_core.database.User
import com.example.data_core.model.UserModel

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

