package com.example.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
    viewModelUser: UserModel,
    vievModelEmprendimiento: EmprendimientoModel,
    vievModelProducto: ProductoModel,
    viewModelHistorial: HistorialModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "configuracionMenu"
    ) {
        composable("SplashScreen") { SplashScreen(navController) }
        composable("homeTest") { HomeTest(
            viewModel = viewModelUser,
            onLogoutClick = { navController.navigate("login") }
        ) }
        composable("login") { LoginScreen(
            viewModel = viewModelUser,
            onLoginClick = {  },
            onRegisterClick = { navController.navigate("register") },
            onLoginSuccess = { navController.navigate("homeTest") }
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
                onPerfilClick = { navController.navigate("EditarUsuario") },
                onEmprendimientosClick = { navController.navigate("UsuarioEmprendimientos") },
                onHistorialClick = { navController.navigate("HistorialUser") },
                onConfigClick = { navController.navigate("Configuracion") }
            )
        }
        composable("Configuracion") { configuracionApp() }
        composable("HistorialUser") { historialUsuario( onBackPage = { navController.popBackStack()}, viewModel = viewModelHistorial ) }
        composable("EditarUsuario") { editUser() }

        composable("UsuarioEmprendimientos") {
            UserEmprendimientos(
                onCreateEmprenClick = { navController.navigate("CrearEmprendimiento")},
                onEmprenClick = { navController.navigate("EmprendimientoProductos")}
            )
        }
        composable("CrearEmprendimientos") { createEmprendimiento() }
        composable("EmprendimientoProductos") {
            ProductosEmprendimiento(
                onEditClick = { navController.navigate("EditProducto")},
                onCreateClick = { navController.navigate("CreateProducto")}
            )
        }
        composable("EditProducto") { EditarProducto() }
        composable("CreateProducto") { CrearProducto() }
    }
}