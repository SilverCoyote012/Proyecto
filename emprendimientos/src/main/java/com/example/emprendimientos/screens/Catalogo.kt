package com.example.emprendimientos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.data_core.model.EmprendimientoModel
import com.example.emprendimientos.R

@Composable
fun menuCategory(
    selectedCategory: String?,
    onCategoryClick: (String?) -> Unit = {},
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally)
    ) {
        val categoriesIcons = listOf(
            R.drawable.alimentos_bebidas to "Alimentos y Bebidas",
            R.drawable.mascotas to "Mascotas",
            R.drawable.ropa_accesorios to "Ropa y Accesorios",
            R.drawable.medio_ambiente to "Medio Ambiente",
            R.drawable.hogar_decoracion to "Decoración del Hogar",
            R.drawable.bienestar_salud to "Bienestar y Salud",
            R.drawable.movilidad_transporte to "Movilidad y Transporte",
            R.drawable.entretenimiento to "Entretenimiento"
        )

        val categoriesText = listOf(
            "Alimentos y Bebidas",
            "Mascotas",
            "Ropa y Accesorios",
            "Medio Ambiente",
            "Decoración del Hogar",
            "Bienestar y Salud",
            "Movilidad y Transporte",
            "Entretenimiento"
        )




        // AQUI SE HACE EL CAMBIO DE PREFERENCIA SOBRE ICONO O TEXTO EN LA CATEGORIA



        //MENU CON ICONOS
        items(categoriesIcons.size) { index ->
            val (icon, category) = categoriesIcons[index]
            IconButton(
                onClick = {
                    if (selectedCategory == category) {
                        onCategoryClick(null)
                    } else {
                        onCategoryClick(category)
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = category,
                    tint = if (selectedCategory == category)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        //MENU CON TEXTO
        items(categoriesText.size) { index ->
            val category = categoriesText[index]
            androidx.compose.material3.TextButton(
                onClick = {
                    if (selectedCategory == category) {
                        onCategoryClick(null)
                    } else {
                        onCategoryClick(category)
                    }
                }
            ) {
                Text(
                    text = category,
                    color = if (selectedCategory == category)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    onLogoClick: () -> Unit = {},
    onEmprendimientosClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onSelectEmprendimiento: (String) -> Unit = {},
    viewModel: EmprendimientoModel
) {
    val emprendimientos by viewModel.emprendimientosFirebase.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getEmprendimientosFromFirebase()
    }

    val filteredEmprendimientos = remember(emprendimientos, selectedCategory) {
        if (selectedCategory == null) {
            emprendimientos
        } else {
            emprendimientos.filter { emprendimiento ->
                emprendimiento.categoria.equals(selectedCategory, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                topMenu(
                    onLogoClick = onLogoClick,
                    onEmprendimientosClick = onEmprendimientosClick,
                    onPerfilClick = onPerfilClick
                )
                menuCategory(
                    selectedCategory = selectedCategory,
                    onCategoryClick = { category ->
                        selectedCategory = category
                    }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .background(Color.White)
        ) {
            items(filteredEmprendimientos) { emprendimiento ->
                EmprendimientoItem(
                    emprendimiento = emprendimiento,
                    onSelectEmprendimiento = { id ->
                        onSelectEmprendimiento(id)
                    }
                )
            }
        }
    }
}