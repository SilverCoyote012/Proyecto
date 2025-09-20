package com.example.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data_core.model.UserModel

import com.example.emprendimientos.screens.*
import com.example.configuration.screens.*
//import com.example.authentication.screens.*

@Composable
fun AppNavHost(
    viewModel: UserModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = ""
    ) {
        // Emprendimientos
        composable("home") {}
        composable("catalogo") {}
        composable("emprendimiento_detalle") {}

        // Configuration
        composable("configuracion") {}
        composable("historial") {}
        composable("editar_perfil") {}
        composable("crear_emprendimiento") {}
        composable("emprendimiento_creado") {}
    }
}
