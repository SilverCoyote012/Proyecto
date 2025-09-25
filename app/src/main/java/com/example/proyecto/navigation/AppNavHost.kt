package com.example.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.proyecto.ui.screens.*
import com.example.authentication.screens.*
import com.example.authentication.components.*
import com.example.data_core.model.*

@Composable
fun AppNavHost(
    viewModelUser: UserModel,
    vievModelEmprendimiento: EmprendimientoModel,
    vievModelProducto: ProductoModel,
    vievModelHistorial: HistorialModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "SplashScreen"
    ) {
        composable("homeTest") { HomeTest(
            viewModel = viewModelUser,
            onLogoutClick = { navController.navigate("login") }
        ) }
        composable("SplashScreen") { SplashScreen(navController) }
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
//        // Configuration
//        composable("configuracion") {}
//        composable("historial") {}
//        composable("editar_perfil") {}
//        composable("crear_emprendimiento") {}
//        composable("emprendimiento_creado") {}
    }
}