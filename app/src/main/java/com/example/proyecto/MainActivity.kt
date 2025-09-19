package com.example.proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.proyecto.navigation.AppNavHost
import com.example.proyecto.ui.theme.ProyectoTheme
import com.example.data_core.database.UserDataBase
import com.example.data_core.model.UserModel
import com.example.data_core.repository.UserRepository
import com.example.data_core.model.UserModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = Room.databaseBuilder(
            applicationContext,
            UserDataBase::class.java,
            "user_db"
        ).build()

        val repository = UserRepository(database.userDao())
        val viewModelFactory = UserModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[UserModel::class.java]

        setContent {
            ProyectoTheme {
                AppNavHost(
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoTheme {
        Greeting("Android")
    }
}