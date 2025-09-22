package com.example.proyecto.ui.screensTest

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data_core.model.UserModel
import com.example.data_core.database.User

@Composable
fun HomeTest(viewModel: UserModel) {
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Pantalla de Pruebas - CRUD de Usuarios",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botón para crear usuario con datos predeterminados
        Button(
            onClick = {
                val testUser = User(
                    name = "Juan Pérez",
                    email = "juan.perez@example.com",
                    password = "password123"
                )
                viewModel.addUser(testUser)
                result = "Usuario creado: ${testUser.name}"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Usuario de Prueba 1")
        }

        Button(
            onClick = {
                val testUser = User(
                    name = "María García",
                    email = "maria.garcia@example.com",
                    password = "password456"
                )
                viewModel.addUser(testUser)
                result = "Usuario creado: ${testUser.name}"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Usuario de Prueba 2")
        }

        Button(
            onClick = {
                val testUser = User(
                    name = "Carlos López",
                    email = "carlos.lopez@example.com",
                    password = "password789"
                )
                viewModel.addUser(testUser)
                result = "Usuario creado: ${testUser.name}"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Usuario de Prueba 3")
        }

        Divider()

        // Botón para obtener todos los usuarios
        Button(
            onClick = {
                // Observar los usuarios desde el ViewModel
                result = "Consultando usuarios... (revisa los logs o implementa observación)"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Obtener Todos los Usuarios")
        }

        // Botón para crear múltiples usuarios de una vez
        Button(
            onClick = {
                val testUsers = listOf(
                    User(name = "Ana Martín", email = "ana.martin@test.com", password = "pass1"),
                    User(name = "Luis Rodríguez", email = "luis.rodriguez@test.com", password = "pass2"),
                    User(name = "Carmen Díaz", email = "carmen.diaz@test.com", password = "pass3")
                )

                testUsers.forEach { user ->
                    viewModel.addUser(user)
                }
                result = "Creados ${testUsers.size} usuarios de prueba"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Múltiples Usuarios")
        }

        Divider()

        // Área para mostrar resultados
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Resultado:",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = if (result.isEmpty()) "Presiona un botón para probar las funciones" else result,
                    fontSize = 14.sp
                )
            }
        }

        // Botón para limpiar resultados
        OutlinedButton(
            onClick = { result = "" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Limpiar Resultado")
        }
    }
}