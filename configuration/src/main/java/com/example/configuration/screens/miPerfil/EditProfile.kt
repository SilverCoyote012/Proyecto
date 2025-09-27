package com.example.configuration.screens.miPerfil

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data_core.database.User
import com.example.data_core.model.UserModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editUser(onBackPage: () -> Unit = {}, viewModel: UserModel){
    var name by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var editName by remember { mutableStateOf(false) }
    var editPass by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var userState by remember { mutableStateOf<User?>(null) }
    var hasError by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        try {
            val user = viewModel.getCurrentUser()
            if (user != null) {
                userState = user
            } else {
                hasError = "No se pudo cargar el usuario"
            }
        } catch (e: Exception) {
            hasError = "Ocurrió un error al cargar el usuario"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onBackPage) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                title = {
                    Text("Mi Perfil", modifier = Modifier.fillMaxWidth().padding(end = 3.dp),
                        textAlign = TextAlign.Right,
                        style =
                            TextStyle(
                                fontSize = 27.sp,
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.ExtraBold
                            )
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
        modifier = Modifier.fillMaxSize().fillMaxWidth()
            .padding(innerPadding).padding(top = 20.dp, start = 10.dp, end = 10.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors =  CardDefaults.cardColors(
                    MaterialTheme.colorScheme.onPrimary),
                shape = RoundedCornerShape(5.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 10.dp, bottom = 5.dp, end = 10.dp)
                ){
                    Text("Nombre", modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    IconButton(
                        onClick = { editName = !editName },
                        modifier = Modifier.size(25.dp))
                    {
                        Icon(
                            painterResource(com.example.configuration.R.drawable.editar),
                            contentDescription = "Editar",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                if (!editName) {
                    Text(
                        text = userState?.name ?: hasError,
                        modifier = Modifier.padding(start = 15.dp, bottom = 10.dp, end = 10.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.ExtraLight,
                        fontStyle = FontStyle.Italic,
                        fontSize = 17.sp
                    )
                }else{
                    AnimatedVisibility(editName) {
                        Row( verticalAlignment = Alignment.CenterVertically ) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = {
                                    Text(
                                        "Ingrese un nuevo nombre",
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                modifier = Modifier
                                    .width(350.dp)
                                    .heightIn(min = 50.dp)
                                    .padding(start = 10.dp, bottom = 10.dp, end = 10.dp),
                                singleLine = true,
                                shape = RoundedCornerShape(5.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary
                                )
                            )
                            IconButton(
                                onClick = {
                                    if (!name.isBlank()){

                                    }else{
                                        Toast.makeText(context, "Campo vacio", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.size(25.dp))
                            {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Editar",
                                    modifier = Modifier.size(25.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors =  CardDefaults.cardColors(
                    MaterialTheme.colorScheme.onPrimary),
                shape = RoundedCornerShape(5.dp)
            ){
                Text("Email",
                    modifier = Modifier.padding(top = 10.dp, start = 15.dp, bottom = 5.dp, end = 10.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(
                    userState?.email?: hasError,
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.ExtraLight,
                    fontStyle = FontStyle.Italic,
                    fontSize = 17.sp
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))
            //Se verifica si el usuario sea de Google, para que no pueda editar su contraseña
            val typeUser = userState?.authType
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors =  CardDefaults.cardColors(
                    MaterialTheme.colorScheme.onPrimary),
                shape = RoundedCornerShape(5.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 15.dp, bottom = 5.dp, end = 10.dp)
                ){
                    Text("Contraseña",
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    IconButton(
                        onClick = {
                            if (typeUser == "email") {
                                editPass = !editPass
                            }
                        },
                        modifier = Modifier.size(25.dp))
                    {
                        Icon(
                            painterResource(com.example.configuration.R.drawable.editar),
                            contentDescription = "Editar",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                if (!editPass) {
                    Text(
                        text = if (typeUser == "google") "No puede editar su contraseña de Google aquí" else userState?.password?.let { "*".repeat(it.length) } ?: hasError,
                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.ExtraLight,
                        fontStyle = FontStyle.Italic,
                        fontSize = 17.sp
                    )
                }else{
                    AnimatedVisibility(editPass) {
                        Row( verticalAlignment = Alignment.CenterVertically ) {
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = {
                                    Text(
                                        "Ingrese su nueva contraseña",
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                        },
                                modifier = Modifier
                                    .width(390.dp)
                                    .heightIn(min = 50.dp)
                                    .padding(start = 10.dp, bottom = 10.dp, end = 10.dp),
                                singleLine = true,
                                shape = RoundedCornerShape(5.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary
                                )
                            )
                            IconButton(
                                onClick = {
                                    when {
                                        typeUser == "email" -> editPass = !editPass
                                        password.isBlank() -> Toast.makeText(context, "Campo vacio", Toast.LENGTH_SHORT).show()
                                        password.length < 8 -> Toast.makeText(context, "Debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()

                                        else -> {
                                            viewModel.changePassword(userState!!, password) { success ->
                                                if (success) {
                                                    Toast.makeText(context, "Contraseña cambiada", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(context, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    }
                                },
                                enabled = typeUser == "google",
                                modifier = Modifier.size(25.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Editar",
                                    modifier = Modifier.size(25.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
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
//        Column(modifier = Modifier) {
//            editUser()
//        }
//    }
//}