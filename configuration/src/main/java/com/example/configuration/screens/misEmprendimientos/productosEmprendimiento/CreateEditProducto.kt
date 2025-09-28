package com.example.configuration.screens.misEmprendimientos.productosEmprendimiento

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.Historial
import com.example.data_core.database.Producto
import com.example.data_core.model.EmprendimientoModel
import com.example.data_core.model.HistorialModel
import com.example.data_core.model.ProductoModel
import com.example.ui_theme.ui.theme.ProyectoTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditProducto(
    onBackPage: () -> Unit = {},
    viewModel: ProductoModel,
    existingProduct: Producto? = null,
    idEmpren: String,
    viewModelEmpren: EmprendimientoModel,
    viewModelAccion: HistorialModel
){
    val emprenSelect by viewModelEmpren.emprendimientoSelect.collectAsState()
    LaunchedEffect(idEmpren) {
        viewModelEmpren.getEmprendimientoById(idEmpren)
    }
    val nameEmpren = emprenSelect?.nombreEmprendimiento ?: "Cargando..."
    val imageEmpren = emprenSelect?.imagen ?: ""

    var nameProduct by remember { mutableStateOf(existingProduct?.nombreProducto ?: "") }
    var descripProduct by remember { mutableStateOf(existingProduct?.descripcion ?: "") }
    var costoProduct by remember { mutableStateOf(existingProduct?.precio ?: "") }
    var imagenProduct by remember { mutableStateOf(existingProduct?.imagen ?: "") }

    var nameError by remember { mutableStateOf("") }
    var descripError by remember { mutableStateOf("") }
    var costoError by remember { mutableStateOf("") }
    var imagenError by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onBackPage ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                title = {
                    Text(
                        text = if (existingProduct == null) "Crear producto" else "Editar producto",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 3.dp),
                        textAlign = TextAlign.Center,
                        style =
                            TextStyle(
                                fontSize = 25.sp,
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.ExtraBold
                            )
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize().fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    modifier = Modifier.height(25.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(
                        "'${nameEmpren}'",
                        modifier = Modifier
                            .fillMaxWidth().fillMaxHeight().padding(top = 2.dp),
                        textAlign = TextAlign.Center,
                        style =
                            TextStyle(
                                fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontWeight = FontWeight.ExtraBold
                            )
                    )
                }
                AsyncImage(
                    imageEmpren,
                    contentDescription = "Imagen del negocio ${nameEmpren}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(90.dp)
                )

                Column(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 25.dp)
                        .fillMaxWidth()
                ) {
                    //Nombre del producto
                    Text(
                        text = "Nombre",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    OutlinedTextField(
                        value = nameProduct,
                        onValueChange = {
                            nameProduct = it
                            if (it.isNotBlank()) nameError = ""
                        },
                        label = {
                            Text(
                                "Ingrese nombre del producto",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
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

                    //Imagen del producto
                    Text(
                        text = "Imagen",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    OutlinedTextField(
                        value = imagenProduct,
                        onValueChange = {
                            imagenProduct = it
                            if (it.isNotBlank()) imagenError = ""
                        },
                        label = {
                            Text(
                                "Ingrese URL de imagen del producto",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        isError = imagenError.isNotEmpty(),
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
                    if (imagenError.isNotBlank()) {
                        Text(
                            text = imagenError,
                            color = MaterialTheme.colorScheme.error,
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    //Descripción del producto
                    Text(
                        text = "Descripción",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    OutlinedTextField(
                        value = descripProduct,
                        onValueChange = {
                            descripProduct = it
                            if (it.isNotBlank()) descripError = ""
                        },
                        label = {
                            Text(
                                "Ingrese descripción del producto",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        isError = descripError.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 4,
                        shape = RoundedCornerShape(5.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedContainerColor = MaterialTheme.colorScheme.background
                        )
                    )
                    if (descripError.isNotBlank()) {
                        Text(
                            text = descripError,
                            color = MaterialTheme.colorScheme.error,
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    //Costo del producto
                    Text(
                        text = "Costo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    OutlinedTextField(
                        value = costoProduct,
                        onValueChange = { cost ->
                            if (cost.matches(Regex("\\d*"))) {
                                costoProduct = cost
                                if (cost.isNotBlank()) costoError = ""
                            }
                        },
                        label = {
                            Text(
                                "Ingrese costo del producto",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        isError = costoError.isNotEmpty(),
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
                    if (costoError.isNotBlank()) {
                        Text(
                            text = costoError,
                            color = MaterialTheme.colorScheme.error,
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }

                    Column(
                        modifier = Modifier.padding()
                            .fillMaxSize().fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                when {
                                    nameProduct.isBlank() || descripProduct.isBlank() || costoProduct.isBlank() || imagenProduct.isBlank() -> {
                                        Toast.makeText(
                                            context,
                                            "Alguno de los campos está vacío",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        if (nameProduct.isEmpty()) nameError = "Campo por llenar"
                                        if (descripProduct.isEmpty()) descripError =
                                            "Campo por llenar"
                                        if (costoProduct.isEmpty()) costoError = "Campo por llenar"
                                        if (imagenProduct.isEmpty()) imagenError =
                                            "Campo por llenar"
                                    }

                                    !android.util.Patterns.WEB_URL.matcher(imagenProduct)
                                        .matches() -> {
                                        Toast.makeText(
                                            context,
                                            "Error en el formato de URL de imagen",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        imagenError = "Error en formato"
                                    }

                                    nameProduct.length > 30 -> {
                                        Toast.makeText(
                                            context,
                                            "Exceso de caracteres",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        nameError = "Máximo 30 caracteres"
                                    }

                                    descripProduct.length > 70 -> {
                                        Toast.makeText(
                                            context,
                                            "Exceso de caracteres",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        descripError = "Máximo 70 caracteres"
                                    }

                                    else -> {
                                        try {
                                            val producto = Producto(
                                                id = existingProduct?.id ?: UUID.randomUUID()
                                                    .toString(),
                                                idEmprendimiento = idEmpren,
                                                nombreProducto = nameProduct,
                                                descripcion = descripProduct,
                                                imagen = imagenProduct,
                                                precio = costoProduct
                                            )
                                            val fecha = LocalDateTime.now()
                                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                                            if (existingProduct == null) {
                                                viewModel.addProducto(producto)
                                                val accion =
                                                    "Creación de producto '$nameProduct' en emprendimiento '$nameEmpren"
                                                viewModelAccion.addHistorial(
                                                    Historial(
                                                        idUsuario = emprenSelect?.idUsuario
                                                            ?: UUID.randomUUID().toString(),
                                                        accion = accion,
                                                        fecha = fecha
                                                    )
                                                )
                                            } else {
                                                viewModel.updateProducto(producto)
                                                val accion =
                                                    "Actualizo producto '$nameProduct' del emprendimiento '$nameEmpren"
                                                viewModelAccion.addHistorial(
                                                    Historial(
                                                        idUsuario = emprenSelect?.idUsuario
                                                            ?: UUID.randomUUID().toString(),
                                                        accion = accion,
                                                        fecha = fecha
                                                    )
                                                )
                                            }
                                            onBackPage()
                                        } catch (_: Exception) {
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.width(277.dp).padding(bottom = 40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(30.dp)
                        ) {
                            Text(
                                text = if (existingProduct == null) "Crear producto" else "Actualizar producto",
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White, fontSize = 15.sp
                            )
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
//        Column(modifier = Modifier.fillMaxSize()) {
//            CreateEditProducto()
//        }
//    }
//}