package com.example.authentication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authentication.components.AuthenticationNavMenu
import com.example.data_core.model.UserModel

@Composable
fun LoginScreen(
    viewModel: UserModel,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AuthenticationNavMenu(
            selectMenu = "login",
            onLoginClick = onLoginClick,
            onRegisterClick = onRegisterClick,
        )
        Spacer(modifier = Modifier.height(24.dp))
        LoginFields(
            viewModel = viewModel,
            onLoginSuccess = onLoginSuccess
        )
    }
}

@Composable
fun LoginFields(
    viewModel: UserModel,
    onLoginSuccess: () -> Unit = {}
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Email",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .width(290.dp)
                .padding(bottom = 8.dp),
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Ingrese su Correo") },
            modifier = Modifier
                .width(290.dp)
                .heightIn(min = 56.dp),
            singleLine = true,
            shape = RoundedCornerShape(5.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Contraseña",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .width(290.dp)
                .padding(bottom = 8.dp),
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Ingrese su Contraseña") },
            modifier = Modifier
                .width(290.dp)
                .heightIn(min = 56.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (viewModel.getUserByEmail(email) != null) {
                    onLoginSuccess()
                }
            },
            modifier = Modifier
                .width(216.dp)
                .height(39.dp),
        ) {
            Text(text = "Iniciar Sesion", color = Color.White)
        }
    }
}