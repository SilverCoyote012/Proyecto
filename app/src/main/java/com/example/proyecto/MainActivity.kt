package com.example.proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType
import com.example.data_core.database.DataBase
import com.example.data_core.firebase.FirebaseServiceEmprendimiento
import com.example.data_core.firebase.FirebaseServiceHistorial
import com.example.data_core.firebase.FirebaseServiceProducto
import com.example.data_core.firebase.FirebaseServiceUser
import com.example.data_core.model.EmprendimientoModel
import com.example.data_core.model.EmprendimientoModelFactory
import com.example.data_core.model.HistorialModel
import com.example.data_core.model.HistorialModelFactory
import com.example.data_core.model.ProductoModel
import com.example.data_core.model.ProductoModelFactory
import com.example.data_core.model.UserModel
import com.example.data_core.model.UserModelFactory
import com.example.data_core.repository.EmprendimientoRepository
import com.example.data_core.repository.HistorialRepository
import com.example.data_core.repository.ProductoRepository
import com.example.data_core.repository.UserRepository
import com.example.proyecto.navigation.AppNavHost
import com.example.ui_theme.ui.theme.ProyectoTheme
import kotlinx.coroutines.selects.select

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Creacion de la base de datos
        val database = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java,
            "database"
        ).fallbackToDestructiveMigration().build()

        // En la siguiente seccion de codigo, se crean los repositorios y los viewModels de cada entidad de la base de datos
        val UserRepository = UserRepository(database.userDao(), FirebaseServiceUser())
        val viewModelFactoryUser = UserModelFactory(UserRepository)
        val viewModelUser = ViewModelProvider(this, viewModelFactoryUser)[UserModel::class.java]

        val EmprendimientoRepository = EmprendimientoRepository(database.emprendimientoDao(),
            FirebaseServiceEmprendimiento())
        val viewModelFactoryEmprendimiento = EmprendimientoModelFactory(EmprendimientoRepository)
        val viewModelEmprendimiento = ViewModelProvider(this, viewModelFactoryEmprendimiento)[EmprendimientoModel::class.java]

        val ProductoRepository = ProductoRepository(database.productoDao(), FirebaseServiceProducto())
        val viewModelFactoryProducto = ProductoModelFactory(ProductoRepository)
        val viewModelProducto = ViewModelProvider(this, viewModelFactoryProducto)[ProductoModel::class.java]

        val HistorialRepository = HistorialRepository(database.historialDao(),
            FirebaseServiceHistorial()
        )
        val viewModelFactoryHistorial = HistorialModelFactory(HistorialRepository)
        val viewModelHistorial = ViewModelProvider(this, viewModelFactoryHistorial)[HistorialModel::class.java]



        setContent {
            var selectModo by remember { mutableStateOf(false)  }  //Inicia en modo claro
            var selectOpcion by remember { mutableStateOf(true)  } //Incia en muestra de iconos en categorias
            ProyectoTheme(darkTheme = selectModo) {
                AppNavHost(
                    // Manejo de modo claro y oscuro
                    selectModo = selectModo,
                    onSelectModo = { nuevoModo -> selectModo = nuevoModo },
                    // Manejo de mostreo de categorias en iconos o textos
                    selectOpcion = selectOpcion,
                    onSelectOpcion = { select -> selectOpcion = select },
                    // Los models que se crearon, son pasados a la funcion AppNavHost, esto para poder manejarlo en diferentes lugares del proyecto
                    viewModelUser = viewModelUser,
                    viewModelEmprendimiento = viewModelEmprendimiento,
                    viewModelProducto = viewModelProducto,
                    viewModelHistorial = viewModelHistorial
                )
            }
        }
    }
}