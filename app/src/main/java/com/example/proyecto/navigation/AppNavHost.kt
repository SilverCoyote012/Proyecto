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

@Composable
fun AppNavHost(
    selectModo: Boolean,
    onSelectModo: (Boolean) -> Unit,
    viewModelUser: UserModel,
    viewModelEmprendimiento: EmprendimientoModel,
    viewModelProducto: ProductoModel,
    viewModelHistorial: HistorialModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "SplashScreen"
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
            onLoginSuccess = { navController.navigate("configuracionMenu") }
        ) }
        composable("register") { RegisterScreen(
            viewModel = viewModelUser,
            onLoginClick = { navController.navigate("login") },
            onRegisterClick = {  }
        ) }

//        // Emprendimientos
//        composable("home") {}
//        composable("catalogo") {}
//        composable("emprendimiento_detalle") {}
//
// Configuration
        composable("configuracionMenu") {
            MenuConfig (
                onNavigateBack = {  },
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
                onSelectModo = { nuevoModo -> onSelectModo(nuevoModo) }
            )
        }
        composable("HistorialUser") { historialUsuario( onBackPage = { navController.popBackStack()}, viewModel = viewModelHistorial ) }
        composable("EditarUsuario") { EditUser( onBackPage = { navController.popBackStack()}, viewModel = viewModelUser) }

        composable("UsuarioEmprendimientos") {
            UserEmprendimientos(
                onBackPage = { navController.popBackStack() },
                onCreateEmprenClick = { navController.navigate("CrearEmprendimiento") },
                onEmprenClick = { idEmprendimiento ->
                    navController.navigate("EmprendimientoProductos/$idEmprendimiento")
                },
                viewModel = viewModelEmprendimiento,
                viewModelUser = viewModelUser
            )
        }
        composable("CrearEmprendimiento") {
            createEmprendimiento(
                onBackPage = { navController.popBackStack() },
                viewModel = viewModelEmprendimiento, viewModelUser = viewModelUser
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
                onBackPage = { navController.popBackStack() },
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
                existingProduct = existingProduct, // puede ser null
                idEmpren = idEmprendimiento,
                onBackPage = { navController.popBackStack() }
            )
        }
    }
}