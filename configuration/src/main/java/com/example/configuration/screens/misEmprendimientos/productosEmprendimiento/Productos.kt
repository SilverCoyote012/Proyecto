package com.example.configuration.screens.misEmprendimientos.productosEmprendimiento

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
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
import com.example.data_core.repository.EmprendimientoRepository
import com.example.data_core.repository.ProductoRepository
import com.example.ui_theme.ui.theme.PinkBrown_themeLight
import com.example.ui_theme.ui.theme.ProyectoTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun cardProductoEdit(
    producto: Producto,
    onEditClick: (Producto) -> Unit = { },
    swipeState: SwipeToDismissBoxState
){
    SwipeToDismissBox(
        state = swipeState,
        modifier = Modifier
            .fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 20.dp)
            .clip(RoundedCornerShape(16.dp)),
        backgroundContent = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar producto",
                modifier = Modifier
                    .fillMaxWidth().fillMaxSize()
                    .background(MaterialTheme.colorScheme.onTertiary)
                    .wrapContentSize(Alignment.CenterEnd),
                tint = Color.White
            )
        }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onBackground)
        )
        {
            Row(
                modifier = Modifier.padding(10.dp).fillMaxWidth()
            ) {
                AsyncImage(
                    model = producto.imagen,
                    contentDescription = "Imagen del producto",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(80.dp).size(100.dp).clip(RoundedCornerShape(5.dp))
                )
                Column(modifier = Modifier.fillMaxWidth().padding(start = 4.dp, 2.dp)) {
                    Text(
                        producto.nombreProducto,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp, color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontStyle = FontStyle.Italic, fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        producto.descripcion,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp), style = TextStyle(lineHeight = 15.sp),
                        fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "$${producto.precio}",
                            modifier = Modifier.weight(1f),
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                        IconButton(
                            onClick = { onEditClick(producto) },
                            modifier = Modifier.size(25.dp)
                        )
                        {
                            Icon(
                                painterResource(com.example.configuration.R.drawable.editar),
                                contentDescription = "Editar producto",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosEmprendimiento(
    idEmpren: String,
    viewModel: ProductoModel,
    viewModelEmpren: EmprendimientoModel,
    viewModelAccion: HistorialModel,
    onBackPage: () -> Unit = {},
    onCreateEditClick: (Producto?) -> Unit = {}
){
    val emprendimiento by viewModelEmpren.emprendimiento.collectAsState()
    val emprenSelect = emprendimiento.find { idEmpren == it.id }
    val nameEmpren = emprenSelect?.nombreEmprendimiento ?: "Error en obtención de datos"
    val imageEmpren = emprenSelect?.imagen ?: "Error en obtención de imagen"
    LaunchedEffect(idEmpren) {
        idEmpren.let { viewModel.getProductoByEmprendimientoId(it) }
    }

    val productos by viewModel.producto.collectAsState()
    val productosEmpren = productos.filter { it.idEmprendimiento == idEmpren }

    val SnackBarHostState = remember { SnackbarHostState() }
    var deleteProduct by remember { mutableStateOf<Producto?>(null) }
    val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

    deleteProduct?.let{ producto ->
        LaunchedEffect(producto) {
            val result = SnackBarHostState.showSnackbar(
                message = "Producto eliminado",
                actionLabel = "Deshacer"
            )
            if ( result == SnackbarResult.ActionPerformed){
                viewModel.addProducto(producto)
                val accion = "Recupero el producto '${producto.nombreProducto}' en emprendimiento '$nameEmpren"
                viewModelAccion.addHistorial( Historial( idUsuario = emprenSelect?.idUsuario ?: UUID.randomUUID().toString(), accion = accion, fecha = fecha))
            }
            deleteProduct = null
        }
    }

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
                     Text(nameEmpren, modifier = Modifier
                         .fillMaxWidth()
                         .padding(end = 3.dp),
                         textAlign = TextAlign.Right,
                         style =
                             TextStyle(
                                 fontSize = 25.sp,
                                 color = MaterialTheme.colorScheme.surface,
                                 fontWeight = FontWeight.ExtraBold
                             )
                     )
                 }
             )
         }, snackbarHost = { SnackbarHost(SnackBarHostState) }
     ){ innerPadding ->
         Column(
             modifier = Modifier.padding(innerPadding).fillMaxSize().fillMaxWidth()
         ) {
             AsyncImage(
                 imageEmpren,
                 contentDescription = "Imagen del negocio '${nameEmpren}'",
                 contentScale = ContentScale.Crop,
                 modifier = Modifier.fillMaxWidth().height(100.dp)
             )
             Card(
                 modifier = Modifier.height(25.dp),
                 colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                 shape = RoundedCornerShape(0.dp),
                 elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
             ) {
                 Text(
                     "Productos", modifier = Modifier
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
         }
         Box(
             modifier = Modifier.padding(10.dp)
                 .padding(2.dp).fillMaxWidth().fillMaxSize()
         ){
             if (productos.isEmpty()){
                 Text(
                     text = "No tienes productos registrados.",
                     style = TextStyle(
                         fontSize = 20.sp, textAlign = TextAlign.Center,
                         fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic,
                         color = Color.Gray
                     ),
                     modifier = Modifier.fillMaxWidth().fillMaxSize()
                         .padding(top = 410.dp)
                 )
             }else{
                 LazyColumn( modifier = Modifier.padding(2.dp).padding(top = 210.dp)) {
                     items(productosEmpren, key = { it.id } ){ producto ->
                         var visible by remember { mutableStateOf(true)}
                         AnimatedVisibility(
                             visible = visible,
                             enter = slideInHorizontally() + scaleIn(),
                             exit = slideOutHorizontally() + fadeOut()
                         ){
                             val dismissState = rememberSwipeToDismissBoxState(
                                 confirmValueChange = {
                                     if (it == SwipeToDismissBoxValue.EndToStart) {
                                         visible = false
                                         viewModel.deleteProducto(producto)
                                         deleteProduct = producto
                                         val accion = "Elimino el producto '${producto.nombreProducto}' del emprendimiento '$nameEmpren"
                                         viewModelAccion.addHistorial( Historial( idUsuario = emprenSelect?.idUsuario ?: UUID.randomUUID().toString(), accion = accion, fecha = fecha))
                                         true
                                     }
                                     false
                                 }
                             )
                             cardProductoEdit(
                                 producto = producto, onEditClick = { onCreateEditClick(producto) }, swipeState = dismissState
                             )
                         }
                     }
                 }
             }
             Spacer(modifier = Modifier.height(16.dp))

             FloatingActionButton(
                 onClick = { onCreateEditClick(null) },
                 containerColor = PinkBrown_themeLight,
                 contentColor = MaterialTheme.colorScheme.onPrimary,
                 modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
             ) {
                 Icon(Icons.Default.Add, contentDescription = "Crear producto")
             }
         }
     }
}