package com.example.emprendimientos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.Producto
import com.example.data_core.model.EmprendimientoModel
import com.example.data_core.model.ProductoModel
import com.example.emprendimientos.R

@Composable
fun cardEmprendimiento(
    emprendimiento: Emprendimiento?
) {
    Box(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = emprendimiento?.imagen,
            contentDescription = "Imagen del emprendimiento",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Card(
            modifier = Modifier.fillMaxWidth(0.9f).height(25.dp),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                emprendimiento?.nombreEmprendimiento ?: "",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().fillMaxSize().padding(3.dp),
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(0f, 4f),
                        blurRadius = 5f
                    )
                ),
                maxLines = 1,
            )
        }
    }
}

@Composable
fun ProductoItem(producto: Producto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFEAD6CA)) // tono beige como en tu maqueta
            .padding(8.dp)
    ) {
        AsyncImage(
            model = producto.imagen,
            contentDescription = producto.nombreProducto,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = producto.nombreProducto,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = producto.descripcion, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "$${producto.precio}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmprendimientoScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: EmprendimientoModel,
    viewModelProductos: ProductoModel,
    idEmprendimiento: String
) {
    // Obtenemos el emprendimiento directamente
    val emprendimientos by viewModel.emprendimiento.collectAsState()
    val emprendimientoFiltrado = emprendimientos.filter { it.id == idEmprendimiento }
    val emprendimiento = if (emprendimientoFiltrado.isNotEmpty()) emprendimientoFiltrado[0] else null

    val productos by viewModelProductos.producto.collectAsState()
    val productosFiltrados = productos.filter { it.idEmprendimiento == idEmprendimiento }
    val producto = if (productosFiltrados.isNotEmpty()) productosFiltrados else emptyList()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(emprendimiento?.nombreEmprendimiento ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Imagen + nombre
            item {
                cardEmprendimiento(emprendimiento)
            }

            // Teléfono y categorías
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { /* Acción de llamada */ }) {
                        Icon(
                            painter = painterResource(R.drawable.telefono),
                            contentDescription = "Teléfono"
                        )
                    }
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEAD6CA)
                        )
                    ) {
                        Text(emprendimiento?.categoria ?: "")
                    }
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEAD6CA)
                        )
                    ) {
                        Text(emprendimiento?.nombreEmprendimiento?.take(10) ?: "")
                    }
                }
            }

            // Lista de productos del emprendimiento
            item {
                Text(
                    text = "Productos",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF7B3F00),
                    modifier = Modifier.padding(12.dp)
                )
            }

            items(productosFiltrados.size) { index ->
                ProductoItem(producto[index])
            }
        }
    }
}
