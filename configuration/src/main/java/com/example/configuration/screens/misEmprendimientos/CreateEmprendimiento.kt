package com.example.configuration.screens.misEmprendimientos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui_theme.ui.theme.ProyectoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun createEmprendimiento(onBackPage: () -> Unit = {}){
    var name by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }

    var nameError by rememberSaveable { mutableStateOf("") }
    var descripcionError by rememberSaveable { mutableStateOf("") }
    var phoneError by rememberSaveable { mutableStateOf("") }
    var selectError by rememberSaveable { mutableStateOf("") }
    var imageError by rememberSaveable { mutableStateOf("") }

    //Control del despliegue de lista
    var expanded by remember { mutableStateOf(false) }
    val categorias = listOf(
        "Alimentos y Bebidas",
        "Mascotas",
        "Ropa y Accesorios",
        "Medio Ambiente",
        "Decoración del Hogar",
        "Bienestar y Salud",
        "Movilidad y Transporte",
        "Entretenimiento"
    )
    //Categoria que se seleccione
    var selectCategoria by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                navigationIcon = {
                    IconButton( onClick = onBackPage ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                title = {
                    Text("Crear emprendimiento", modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 3.dp),
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
            modifier = Modifier.padding(innerPadding)
                .padding(20.dp).fillMaxSize().fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Nombre del negocio
            Text(
                text = "Nombre",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (it.isNotBlank()) nameError = ""
                                },
                label = { Text("Ingrese nombre del negocio",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold)
                },
                isError = nameError.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(5.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background
                )
            )
            if (nameError.isNotBlank()) {
                Text(
                    text = nameError,
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            //Descripción del negocio
            Text(
                text = "Descripción",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = {
                    descripcion = it
                    if (it.isNotBlank()) descripcionError = ""
                },
                label = { Text("Ingrese nombre del negocio",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold)
                },
                isError = descripcionError.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(5.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background
                )
            )
            if (descripcionError.isNotBlank()) {
                Text(
                    text = descripcionError,
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            //Lista de categorias desplegable
            Text(
                text = "Categoria",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectCategoria,
                    onValueChange = {
                        if (it.isNotBlank()) selectError = ""
                    },
                    readOnly = true,
                    label = { Text("Seleccione una categoria del negocio",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor().fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded, onDismissRequest = { expanded = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.onSurfaceVariant)
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria) },
                            onClick = {
                                selectCategoria = categoria
                                expanded = false
                            }
                        )
                    }
                }
            }
            if (selectError.isNotBlank()) {
                Text(
                    text = selectError,
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            //Numero telefonico
            Text(
                text = "Teléfono",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    if (it.isNotBlank()) phoneError = ""
                },
                label = { Text("Ingrese numero telefonico del negocio",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold)
                },
                isError = phoneError.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(5.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background
                )
            )
            if (phoneError.isNotBlank()) {
                Text(
                    text = phoneError,
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            //Imagen del negocio
            Text(
                text = "Imagen",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            OutlinedTextField(
                value = image,
                onValueChange = {
                    phone = it
                    if (it.isNotBlank()) imageError = ""
                },
                label = { Text("Ingrese la URL de imagen",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold)
                },
                isError = imageError.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(5.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background
                )
            )
            if (imageError.isNotBlank()) {
                Text(
                    text = imageError,
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            Column(
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize().fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.width(277.dp).padding(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        "Crear emprendimiento",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White, fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, /*, showSystemUi = true*/)
@Composable
fun muestra() {
    ProyectoTheme(darkTheme = false) {
        Column(modifier = Modifier.fillMaxSize())
        {
            createEmprendimiento()
        }
    }
}