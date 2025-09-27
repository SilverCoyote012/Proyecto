package com.example.authentication.screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
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

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
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
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            "SpotMe",
            fontSize = 55.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        AuthenticationNavMenu(
            selectMenu = "login",
            onLoginClick = onLoginClick,
            onRegisterClick = onRegisterClick,
        )

        Spacer(modifier = Modifier.height(24.dp))

        LoginFields(viewModel = viewModel, onLoginSuccess = onLoginSuccess)
    }
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@Composable
fun LoginFields(
    viewModel: UserModel,
    onLoginSuccess: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                viewModel.loginWithGoogleAuthentication(
                    context = context,
                    lifecycleOwner = lifecycleOwner,
                    idToken = idToken
                ) { success ->
                    if (success) onLoginSuccess()
                    else Toast.makeText(context, "Error al ingresar con Google", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (_: Exception) { }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = "Email",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.width(290.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text("Ingrese su correo",
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

        Text(
            text = "Contraseña",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.width(290.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text("Ingrese su contraseña",
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
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // --- Login con Email y Password ---
        Button(
            onClick = {
                when {
                    email.isBlank() || password.isBlank() ->
                        Toast.makeText(context, "Alguno de los campos está vacío", Toast.LENGTH_SHORT).show()
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                        Toast.makeText(context, "Error en el formato de Email", Toast.LENGTH_SHORT).show()
                    else -> {
                        viewModel.loginWithEmailAndPassword(
                            context = context,
                            lifecycleOwner = lifecycleOwner,
                            user = User(
                                id = "",
                                name = "",
                                email = email,
                                password = password,
                                authType = "email"
                            )
                        ) { success ->
                            if (success) onLoginSuccess()
                            else Toast.makeText(context, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier.width(216.dp).height(39.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Iniciar sesión", color = Color.White, fontWeight = FontWeight.ExtraBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Login con Google ---
        Button(
            onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            },
            modifier = Modifier.width(216.dp).height(39.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Iniciar Sesión con Google", color = Color.White, fontWeight = FontWeight.ExtraBold)
        }
    }
}
