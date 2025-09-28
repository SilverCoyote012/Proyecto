package com.example.authentication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


// Componente separa para poder usarce en diferentes ventanas (login y registro)


@Composable
fun AuthenticationNavMenu(
    selectMenu: String,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
){
    Row(
        modifier = Modifier
            .width(290.dp)
            .background(MaterialTheme.colorScheme.secondary),
        horizontalArrangement = Arrangement.Center,
    ) {
        TextButton(onClick = onLoginClick) {
            Column() {
                Text(
                    text = "Login",
                    color = if (selectMenu == "login") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                if (selectMenu == "login") {
                    HorizontalDivider(
                        modifier = Modifier.width(35.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        TextButton(onClick = onRegisterClick) {
            Column() {
                Text(
                    text = "Registro",
                    color = if (selectMenu == "register") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                if (selectMenu == "register") {
                    HorizontalDivider(
                        modifier = Modifier.width(55.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}