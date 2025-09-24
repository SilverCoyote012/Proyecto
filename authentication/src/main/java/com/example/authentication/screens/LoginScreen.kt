package com.example.authentication.screens

import androidx.compose.foundation.background
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authentication.R
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
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(R.drawable.logospotme),
            contentDescription = "Icono de SpotMe",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text("SpotMe", fontSize = 45.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

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

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = "Email",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .width(290.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                "Ingrese su correo",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold)
                    },
            modifier = Modifier
                .width(290.dp)
                .heightIn(min = 56.dp)
                .padding(bottom = 5.dp),
            singleLine = true,
            shape = RoundedCornerShape(5.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Contraseña",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .width(290.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Ingrese su contraseña",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold)
                    },
            modifier = Modifier
                .width(290.dp)
                .heightIn(min = 56.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            )
        )

        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = {
                when {
                    email.isBlank() && password.isBlank() -> Toast.makeText(context, "Alguno de los campos estas vacios", Toast.LENGTH_SHORT).show()
                    !email.isBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email!!).matches() -> Toast.makeText(context, "Error en el formato de Email", Toast.LENGTH_SHORT).show()
                    else -> {
                        onLoginSuccess
                    }
                }
            },
            modifier = Modifier
                .width(216.dp)
                .height(39.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Iniciar sesión", color = Color.White, fontWeight = FontWeight.ExtraBold)
        }
    }
}