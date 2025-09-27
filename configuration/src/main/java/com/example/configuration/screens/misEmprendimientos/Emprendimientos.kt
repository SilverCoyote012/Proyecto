package com.example.configuration.screens.misEmprendimientos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.User
import com.example.data_core.model.EmprendimientoModel
import com.example.data_core.model.UserModel
import com.example.ui_theme.ui.theme.PinkBrown_themeLight
import com.example.ui_theme.ui.theme.ProyectoTheme

@Composable
fun cardEmprendimientoUser(
    emprendimiento: Emprendimiento,
    onEmprenClick: (String) -> Unit,
    swipeState: SwipeToDismissBoxState
){
    SwipeToDismissBox(
        state = swipeState,
        modifier = Modifier
            .height(180.dp).padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 20.dp)
            .clip(RoundedCornerShape(16.dp)),
        backgroundContent = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar emprendimiento",
                modifier = Modifier
                    .fillMaxWidth().fillMaxSize()
                    .background(MaterialTheme.colorScheme.onTertiary)
                    .wrapContentSize(Alignment.CenterEnd),
                tint = Color.White
            )
        }
    ) {
        Box(
            modifier = Modifier.clickable( onClick = { onEmprenClick(emprendimiento.id) } ).fillMaxSize().height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = emprendimiento.imagen,
                contentDescription = "Imagen URL",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp))
            )
            Card(
                modifier = Modifier.fillMaxWidth(0.9f).height(25.dp),
                colors = CardDefaults.cardColors(
                    MaterialTheme.colorScheme.onSecondaryContainer
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    emprendimiento.nombreEmprendimiento,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().fillMaxSize().padding(3.dp),
                    color = Color.White,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(0f, 4f),
                            blurRadius = 5f
                        )
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEmprendimientos(
    onBackPage: () -> Unit = {},
    onCreateEmprenClick: () -> Unit = {},
    onEmprenClick: (String) -> Unit,
    viewModel: EmprendimientoModel,
    viewModelUser: UserModel
) {
    var userState by remember { mutableStateOf<User?>(null) }
    var hasError by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        try {
            val user = viewModelUser.getCurrentUser()
            if (user != null) {
                userState = user
            } else {
                hasError = "No se pudo cargar el usuario"
            }
        } catch (e: Exception) {
            hasError = "Ocurrió un error al cargar el usuario"
        }
    }

    val emprendimientos by viewModel.emprendimiento.collectAsState()

    LaunchedEffect(userState) {
        userState?.id?.let { viewModel.getEmprendimientosByUserId(it) }
    }

    val SnackBarHostState = remember { SnackbarHostState() }
    var deleteEmpren by remember { mutableStateOf<Emprendimiento?>(null) }

    deleteEmpren?.let{ emprendimiento ->
        LaunchedEffect(emprendimiento) {
            val result = SnackBarHostState.showSnackbar(
                message = "Emprendimiento eliminado",
                actionLabel = "Deshacer"
            )
            if ( result == SnackbarResult.ActionPerformed){
                viewModel.addEmprendimiento(emprendimiento)
            }
            deleteEmpren = null
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
                    Text("Mis emprendimientos", modifier = Modifier
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
        },
        snackbarHost = { SnackbarHost(SnackBarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
                    .padding(2.dp).fillMaxWidth().fillMaxSize()
        ){
            if (emprendimientos.isEmpty()) {
                Text(
                    text = "No tienes emprendimientos registrados.",
                    style = TextStyle(
                        fontSize = 20.sp, textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth().fillMaxSize().padding(top = 210.dp)
                )
            } else{
                LazyColumn(
                    modifier = Modifier.padding(2.dp)
                ) {
                    items(emprendimientos, key = { it.id }){emprendimiento ->
                        var visible by remember { mutableStateOf(true)}
                        AnimatedVisibility(
                            visible = visible,
                            enter = slideInHorizontally() + scaleIn(),
                            exit = slideOutHorizontally() + fadeOut()
                        ) {
                            val dismissState = rememberSwipeToDismissBoxState(
                                confirmValueChange = {
                                    if (it == SwipeToDismissBoxValue.EndToStart) {
                                        visible = false
                                        viewModel.deleteEmprendimiento(emprendimiento)
                                        deleteEmpren = emprendimiento
                                        true
                                    }
                                    false
                                }
                            )
                            cardEmprendimientoUser(
                                emprendimiento = emprendimiento, onEmprenClick = { onEmprenClick(emprendimiento.id) } , swipeState = dismissState
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            FloatingActionButton(
                onClick = { onCreateEmprenClick() },
                containerColor = PinkBrown_themeLight,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear emprendimiento")
            }
        }
    }
}
