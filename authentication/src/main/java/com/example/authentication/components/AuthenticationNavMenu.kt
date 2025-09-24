package com.example.authentication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AuthenticationNavMenu(
    selectMenu: String,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TextButton(onClick = onLoginClick) {
            Text(
                text = "Iniciar Sesion",
                color = if (selectMenu == "login") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
        TextButton(onClick = onRegisterClick) {
            Text(
                text = "Registrarse",
                color = if (selectMenu == "register") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
    }
}