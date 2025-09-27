package com.example.authentication.screens

import androidx.compose.foundation.background
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

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
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text("SpotMe", fontSize = 55.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        AuthenticationNavMenu(
            selectMenu = "register",
            onLoginClick = onLoginClick,
            onRegisterClick = onRegisterClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        RegisterFields(
            viewModel = viewModel,
            onLoginClick = onLoginClick
        )
    }
}

@Composable
fun RegisterFields(
    viewModel: UserModel,
    onLoginClick: () -> Unit = {}
){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                viewModel.registerWithGoogleAuthentication(idToken) { success ->
                    if (success) {
                        Toast.makeText(context, "Registro con Google exitoso", Toast.LENGTH_SHORT).show()
                        onLoginClick()
                    } else {
                        Toast.makeText(context, "Error al registrar con Google", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (_: Exception) {

        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nombre
        Text(
            text = "Nombre",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.width(290.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Ingrese su nombre completo",
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
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Email
        Text(
            text = "Email",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            modifier = Modifier.width(290.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Ingrese un correo",
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
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Contraseña
        Text(
            text = "Contraseña",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            modifier = Modifier.width(290.dp),
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
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Registro con correo
        Button(
            onClick = {
                when {
                    email.isBlank() || name.isBlank() || password.isBlank() -> {
                        Toast.makeText(context, "Alguno de los campos está vacío", Toast.LENGTH_SHORT).show()
                    }
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        Toast.makeText(context, "Error en el formato de Email", Toast.LENGTH_SHORT).show()
                    }
                    password.length < 8 -> {
                        Toast.makeText(context, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        try {
                            viewModel.registerWithEmailAndPassword(User(
                                id = "",
                                name = name,
                                email = email,
                                password = password,
                                authType = "email"
                            )) {
                                success ->
                                if (success) {
                                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Error al registrar", Toast.LENGTH_SHORT).show()
                                }
                            }
                            onLoginClick()
                        } catch (_: Exception) { }
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
            Text("Registrarse", color = Color.White, fontWeight = FontWeight.ExtraBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            },
            modifier = Modifier
                .width(216.dp)
                .height(39.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Registrarse con Google", color = Color.White, fontWeight = FontWeight.ExtraBold)
        }
    }
}
