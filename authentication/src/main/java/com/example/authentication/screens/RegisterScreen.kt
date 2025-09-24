package com.example.authentication.screens

import androidx.compose.foundation.background
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authentication.R

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
        horizontalAlignment = Alignment.CenterHorizontally,
        //modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = "Nombre",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .width(290.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    "Ingrese su nombre completo",
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
            text = "Email",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            modifier = Modifier
                .width(290.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text("Ingrese un correo",
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
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            modifier = Modifier
                .width(290.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Ingrese una contraseña",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold)
                    },
            modifier = Modifier
                .width(290.dp)
                .heightIn(min = 56.dp)
                .padding(bottom = 5.dp),
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
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Registrarse", color = Color.White, fontWeight = FontWeight.ExtraBold)
        }
    }
}