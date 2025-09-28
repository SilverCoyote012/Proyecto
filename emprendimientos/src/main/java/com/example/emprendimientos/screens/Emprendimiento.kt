package com.example.emprendimientos.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.Producto
import com.example.data_core.model.EmprendimientoModel
import com.example.data_core.model.ProductoModel

@Composable
fun cardEmprendimiento(
    emprendimiento: Emprendimiento?,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(200.dp).fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = emprendimiento?.imagen,
            contentDescription = "Imagen del emprendimiento",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.TopStart).padding(top = 15.dp, start = 10.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Regresar",
                tint = Color.White
            )
        }
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onBackground
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            shape = RectangleShape
        ) {
            Text(
                text = emprendimiento?.nombreEmprendimiento ?: "Nombre del emprendimiento",
                textAlign = TextAlign.Center, fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth().height(35.dp),
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.White.copy(alpha = 0.2f),
                        offset = Offset(4f, 0f),
                        blurRadius = 2f
                    )
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ProductoItem(producto: Producto) {
    Card(
        modifier = Modifier
            .fillMaxWidth().fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 10.dp).height(90.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = producto.imagen,
                contentDescription = producto.nombreProducto,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(80.dp).size(100.dp).clip(RoundedCornerShape(5.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombreProducto,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 14.sp, color = MaterialTheme.colorScheme.onTertiaryContainer,
                    fontStyle = FontStyle.Italic, fontWeight = FontWeight.ExtraBold,
                    maxLines = 1
                )

                Text(
                    text = producto.descripcion,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp), style = TextStyle(lineHeight = 15.sp),
                    fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 2
                )

                Text(
                    text = "$${producto.precio}",
                    modifier = Modifier.weight(1f),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                    maxLines = 1
                )
            }
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
    LaunchedEffect(idEmprendimiento) {
        //Log.d("DEBUG", "Loading data for emprendimiento: $idEmprendimiento")
        viewModel.getEmprendimientosFromFirebase()
        viewModelProductos.getProductosByEmprendimiento(idEmprendimiento)
    }

    val emprendimientos by viewModel.emprendimientosFirebase.collectAsState()
    val productos by viewModelProductos.productosFirebase.collectAsState()

    val emprendimiento = emprendimientos.find { it.id == idEmprendimiento }
    val productosFiltrados = productos.filter { it.idEmprendimiento == idEmprendimiento }

    //Log.d("DEBUG", "Emprendimiento found: $emprendimiento")
    //Log.d("DEBUG", "Productos filtrados: ${productosFiltrados.size}")

    LazyColumn(
        modifier = Modifier
            .padding().fillMaxSize().background(MaterialTheme.colorScheme.background),
    ) {
        item {
            cardEmprendimiento(emprendimiento, onNavigateBack)
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    //modifier = Modifier.size(30.dp).width(30.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        IconButton(
                            onClick = {  },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Teléfono",
                                tint = Color.Black, modifier = Modifier.size(15.dp).padding(0.dp)
                            )
                        }
                        Text(
                            text = emprendimiento?.telefono ?: "No disponible",
                            color = Color.Black,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Light, fontSize = 12.sp, fontStyle = FontStyle.Italic
                            ),
                            maxLines = 1
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = emprendimiento?.categoria ?: "Categoria",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Light, fontSize = 12.sp, fontStyle = FontStyle.Italic
                        )
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                ) {
                    Text(
                        text = emprendimiento?.nombreEmprendimiento?.take(10) ?: "Nombre Apt",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Light, fontSize = 12.sp, fontStyle = FontStyle.Italic
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        item {
            Text(
                text = "Productos",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold
                ), fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        items(productosFiltrados) { producto ->
            ProductoItem(producto)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
