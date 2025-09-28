package com.example.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.authentication.screens.*
import com.example.data_core.model.*
import com.example.proyecto.ui.screensTest.*
import com.example.configuration.screens.configuracion.*
import com.example.configuration.screens.historial.*
import com.example.configuration.screens.miPerfil.*
import com.example.configuration.screens.misEmprendimientos.*
import com.example.configuration.screens.misEmprendimientos.productosEmprendimiento.*
import com.example.emprendimientos.screens.CatalogoScreen
import com.example.emprendimientos.screens.EmprendimientoScreen
import com.example.emprendimientos.screens.EmprendimientosScreen

@Composable
fun AppNavHost(
    // Modo oscuro y claro
    selectModo: Boolean,
    onSelectModo: (Boolean) -> Unit,
    // Mostrar iconos o texto
    selectOpcion: Boolean,
    onSelectOpcion: (Boolean) -> Unit,
    // Aqui se reciben los models de la base de datos creados en el MainActivty
    viewModelUser: UserModel,
    viewModelEmprendimiento: EmprendimientoModel,
    viewModelProducto: ProductoModel,
    viewModelHistorial: HistorialModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "SplashScreen" // Se inicia en la aplicacion en la pantalla de splash
    ) {
        composable("SplashScreen") { SplashScreen(navController) }
        composable("homeTest") { HomeTest(
            viewModel = viewModelUser,
            onLogoutClick = { navController.navigate("SplashScreen") }
        ) }
        composable("login") { LoginScreen(
            viewModel = viewModelUser,
            onLoginClick = {  },
            onRegisterClick = { navController.navigate("register") },
            onLoginSuccess = { navController.navigate("home") }
        ) }
        composable("register") { RegisterScreen(
            viewModel = viewModelUser,
            onLoginClick = { navController.navigate("login") },
            onRegisterClick = {  }
        ) }

        // Emprendimientos
        composable("home") {
            EmprendimientosScreen(
                // Para manejar la pantalla donde se muestra un emprendimiento a detalle, se pasa el id del emprendimiento a la pantalla donde se mostrara
                onSelectEmprendimiento = { idEmprendimiento ->
                    navController.navigate("emprendimiento_detalle/$idEmprendimiento")
                },
                viewModel = viewModelEmprendimiento,
                onLogoClick = { navController.navigate("home") },
                onEmprendimientosClick = { navController.navigate("catalogo") },
                onPerfilClick = { navController.navigate("configuracionMenu") }
            )
        }
        composable("catalogo") {
            CatalogoScreen(
                viewModel = viewModelEmprendimiento,
                onLogoClick = { navController.navigate("home") },
                // Para manejar la pantalla donde se muestra un emprendimiento a detalle, se pasa el id del emprendimiento a la pantalla donde se mostrara
                onSelectEmprendimiento = { idEmprendimiento ->
                    navController.navigate("emprendimiento_detalle/$idEmprendimiento")
                },
                onPerfilClick = { navController.navigate("configuracionMenu") },
                selectOpcion = selectOpcion
            )
        }
        composable(
            "emprendimiento_detalle/{idEmprendimiento}",
            arguments = listOf(navArgument("idEmprendimiento") { type = NavType.StringType })
        ) { backStackEntry ->
            val idEmprendimiento = backStackEntry.arguments?.getString("idEmprendimiento") ?: ""
            EmprendimientoScreen(
                onNavigateBack = { navController.navigate("home") },
                viewModel = viewModelEmprendimiento,
                viewModelProductos = viewModelProducto,
                idEmprendimiento = idEmprendimiento
        ) }

// Configuration
        composable("configuracionMenu") {
            MenuConfig (
                onNavigateBack = { navController.navigate("home") },
                onLogOut = { navController.navigate("SplashScreen") },
                onPerfilClick = { navController.navigate("EditarUsuario") },
                onEmprendimientosClick = { navController.navigate("UsuarioEmprendimientos") },
                onHistorialClick = { navController.navigate("HistorialUser") },
                onConfigClick = { navController.navigate("Configuracion") },
                viewModel = viewModelUser
            )
        }
        composable("Configuracion") {
            configuracionApp(
                onBackPage = { navController.popBackStack()}, selectModo = selectModo,
                onSelectModo = { nuevoModo -> onSelectModo(nuevoModo) }, selectOpcion = selectOpcion,
                onSelectOpcion= { select -> onSelectOpcion(select) }
            )
        }
        composable("HistorialUser") { historialUsuario( onBackPage = { navController.popBackStack()}, viewModel = viewModelHistorial, viewModelUser = viewModelUser ) }
        composable("EditarUsuario") { EditUser( onBackPage = { navController.popBackStack()}, viewModel = viewModelUser) }

        composable("UsuarioEmprendimientos") {
            UserEmprendimientos(
                onBackPage = { navController.popBackStack() },
                onCreateEmprenClick = { navController.navigate("CrearEmprendimiento") },
                // Para manejar la pantalla donde se muestra un emprendimiento a detalle, se pasa el id del emprendimiento a la pantalla donde se mostrara
                onEmprenClick = { idEmprendimiento ->
                    navController.navigate("EmprendimientoProductos/$idEmprendimiento")
                },
                viewModel = viewModelEmprendimiento,
                viewModelUser = viewModelUser,
                viewModelAccion = viewModelHistorial
            )
        }
        composable("CrearEmprendimiento") {
            createEmprendimiento(
                onBackPage = { navController.popBackStack() },
                viewModel = viewModelEmprendimiento, viewModelUser = viewModelUser, viewModelAccion = viewModelHistorial
            )
        }

        composable("EmprendimientoProductos/{idEmprendimiento}",
            arguments = listOf(navArgument("idEmprendimiento") { type = NavType.StringType })
        ) { backStackEntry ->
            val idEmprendimiento = backStackEntry.arguments?.getString("idEmprendimiento") ?: ""
            ProductosEmprendimiento(
                idEmpren = idEmprendimiento,
                viewModel = viewModelProducto,
                viewModelEmpren = viewModelEmprendimiento,
                viewModelAccion = viewModelHistorial,
                onBackPage = { navController.popBackStack() },
                // Para manejar la pantalla donde se muestra un producto a detalle, se pasa el id del producto a la pantalla donde se mostrara
                onCreateEditClick = { producto ->
                    viewModelProducto.productEdit.value = producto
                    navController.navigate("CrearEditarProducto/$idEmprendimiento")
                }
            )
        }
        composable(
            "CrearEditarProducto/{idEmprendimiento}",
            arguments = listOf(navArgument("idEmprendimiento") { type = NavType.StringType })
        ) { backStackEntry ->
            val idEmprendimiento = backStackEntry.arguments?.getString("idEmprendimiento") ?: ""
            val existingProduct = viewModelProducto.productEdit.collectAsState().value

            CreateEditProducto(
                viewModel = viewModelProducto,
                viewModelEmpren = viewModelEmprendimiento,
                viewModelAccion = viewModelHistorial,
                existingProduct = existingProduct, // puede ser null
                idEmpren = idEmprendimiento,
                onBackPage = { navController.popBackStack() }
            )
        }
    }
}