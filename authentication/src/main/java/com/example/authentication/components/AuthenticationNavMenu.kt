package com.example.authentication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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
            Column() {
                Text(
                    text = "Login",
                    color = if (selectMenu == "login") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                if (selectMenu == "login") {
                    Divider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 2.dp,
                        modifier = Modifier.width(35.dp)
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
                    Divider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 2.dp,
                        modifier = Modifier.width(55.dp)
                    )
                }
            }
        }
    }
}