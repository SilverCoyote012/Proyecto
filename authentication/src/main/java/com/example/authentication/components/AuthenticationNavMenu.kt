package com.example.authentication.components

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AuthenticationNavMenu(
    selectMenu: String,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(onClick = onLoginClick) {
            Text(
                text = "Iniciar Sesion",
                color = if (selectMenu == "login") Color.Blue else Color.Black
            )
        }
        TextButton(onClick = onRegisterClick) {
            Text(
                text = "Registrarse",
                color = if (selectMenu == "register") Color.Blue else Color.Black
            )
        }
    }
}