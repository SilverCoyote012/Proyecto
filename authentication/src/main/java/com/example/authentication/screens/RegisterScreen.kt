package com.example.authentication.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.authentication.components.AuthenticationNavMenu
import com.example.data_core.database.User
import com.example.data_core.model.UserModel

@Composable
fun RegisterScreen(
    viewModel: UserModel,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AuthenticationNavMenu(
            selectMenu = "register",
            onLoginClick = onLoginClick,
            onRegisterClick = onRegisterClick
        )
        Spacer(modifier = Modifier.height(24.dp))
        RegisterFields(viewModel = viewModel)
    }
}

@Composable
fun RegisterFields(
    viewModel: UserModel
){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nombre",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .width(290.dp)
                .padding(bottom = 8.dp),
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Ingrese su Nombre Completo") },
            modifier = Modifier
                .width(290.dp)
                .heightIn(min = 56.dp),
            singleLine = true,
            shape = RoundedCornerShape(5.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

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
                when {
                    email.isBlank() && name.isBlank() && password.isBlank() -> Toast.makeText(context, "Alguno de los campos estas vacios", Toast.LENGTH_SHORT).show()
                    !email.isBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email!!).matches() -> Toast.makeText(context, "Error en el formato de Email", Toast.LENGTH_SHORT).show()
                    password.length < 8 -> Toast.makeText(context, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()

                    else -> {
                        viewModel.addUser(User(name = name, email = email, password = password))
                    }
                }
            },
            modifier = Modifier
                .width(216.dp)
                .height(39.dp),

        ) {
            Text(text = "Registrarse", color = Color.White)
        }
    }
}