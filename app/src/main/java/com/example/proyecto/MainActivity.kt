package com.example.proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.data_core.database.DataBase
import com.example.data_core.firebase.FirebaseServiceEmprendimiento
import com.example.data_core.firebase.FirebaseServiceUser
import com.example.data_core.model.EmprendimientoModel
import com.example.data_core.model.EmprendimientoModelFactory
import com.example.data_core.model.UserModel
import com.example.data_core.model.UserModelFactory
import com.example.data_core.repository.EmprendimientoRepository
import com.example.data_core.repository.UserRepository
import com.example.proyecto.navigation.AppNavHost
import com.example.ui_theme.ui.theme.ProyectoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java,
            "database"
        ).fallbackToDestructiveMigration().build()

        val UserRepository = UserRepository(database.userDao(), FirebaseServiceUser())
        val viewModelFactoryUser = UserModelFactory(UserRepository)
        val viewModelUser = ViewModelProvider(this, viewModelFactoryUser)[UserModel::class.java]

        val EmprendimientoRepository = EmprendimientoRepository(database.emprendimientoDao(),
            FirebaseServiceEmprendimiento())
        val viewModelFactoryEmprendimiento = EmprendimientoModelFactory(EmprendimientoRepository)
        val viewModelEmprendimiento = ViewModelProvider(this, viewModelFactoryEmprendimiento)[EmprendimientoModel::class.java]

        setContent {
            ProyectoTheme {
                AppNavHost(
                    viewModelUser = viewModelUser,
                    vievModelEmprendimiento = viewModelEmprendimiento
                )
            }
        }
    }
}