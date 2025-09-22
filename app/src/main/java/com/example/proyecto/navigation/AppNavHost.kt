package com.example.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data_core.model.UserModel

import com.example.proyecto.ui.screensTest.*
import com.example.authentication.screens.*
import com.example.authentication.components.*

@Composable
fun AppNavHost(
    viewModel: UserModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("homeTest") { HomeTest(viewModel) }
        composable("login") { LoginScreen(
            viewModel = viewModel,
            onLoginClick = {  },
            onRegisterClick = { navController.navigate("register") },
            onLoginSuccess = { navController.navigate("homeTest") }
        ) }
        composable("register") { RegisterScreen(
            viewModel = viewModel,
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