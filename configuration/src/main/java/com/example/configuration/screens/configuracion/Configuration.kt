package com.example.configuration.screens.configuracion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui_theme.ui.theme.GrayWhite_themeLight
import com.example.ui_theme.ui.theme.PinkBrown_themeLight
import com.example.ui_theme.ui.theme.ProyectoTheme

@Composable
fun cardModo(selectModo: Boolean, onSelectModo: (Boolean) -> Unit = {} ){
    Card(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        colors =  CardDefaults.cardColors(MaterialTheme.colorScheme.onSurfaceVariant),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text("Modos", textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 1.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.ExtraBold, fontSize = 20.sp
        )
        Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(3f), horizontalAlignment = Alignment.CenterHorizontally) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(85.dp),
                    colors =  CardDefaults.cardColors(Color.White),
                    shape = RoundedCornerShape(5.dp)
                ){}
                if (!selectModo) {
                    Text("Claro", modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp, color = PinkBrown_themeLight
                    )
                }else {
                    Text("Claro", modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center, color = GrayWhite_themeLight
                    )
                }
                RadioButton(
                    selected = !selectModo,
                    onClick = { onSelectModo(false) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = PinkBrown_themeLight,
                        unselectedColor = GrayWhite_themeLight,
                        disabledUnselectedColor = GrayWhite_themeLight
                    )
                )
            }

            Spacer(modifier = Modifier.padding(15.dp))

            Column(Modifier.weight(3f), horizontalAlignment = Alignment.CenterHorizontally) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .size(85.dp),
                    colors =  CardDefaults.cardColors(Color.Black),
                    shape = RoundedCornerShape(5.dp)
                ){}
                if (!selectModo) {
                    Text("Oscuro", modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center, color = Color.Black)
                }else{
                    Text("Oscuro", modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp, color = PinkBrown_themeLight
                    )
                }
                RadioButton(
                    selected = selectModo,
                    onClick = { onSelectModo(true) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = PinkBrown_themeLight,
                        unselectedColor = GrayWhite_themeLight,
                        disabledUnselectedColor = GrayWhite_themeLight
                    )
                )
            }
        }
    }
}

@Composable
fun cardPreferencia(onSelectOpcion: (Boolean) -> Unit = {}, selectOpcion: Boolean){
    Card(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        colors =  CardDefaults.cardColors(MaterialTheme.colorScheme.onSurfaceVariant),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text("Preferencias", textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 1.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.ExtraBold, fontSize = 20.sp
        )
        Card(
            modifier = Modifier.padding(10.dp),
            colors =  CardDefaults.cardColors(MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Elementos", modifier = Modifier.fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp, end = 10.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.ExtraBold, fontSize = 17.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
                Column( modifier = Modifier.weight(1f).height(50.dp).padding(bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    if (!selectOpcion) {
                        Text(
                            "Iconos", modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
                            textAlign = TextAlign.Center, color = GrayWhite_themeLight
                        )
                    } else {
                        Text(
                            "Iconos", modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
                            textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp, color = PinkBrown_themeLight
                        )
                    }
                    RadioButton(
                        selected = selectOpcion,
                        onClick = { onSelectOpcion(true) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = PinkBrown_themeLight,
                            unselectedColor = GrayWhite_themeLight,
                            disabledUnselectedColor = GrayWhite_themeLight
                        )
                    )
                }
                Column(modifier = Modifier.weight(1f).height(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    if (!selectOpcion) {
                        Text(
                            "Texto", modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
                            textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp, color = PinkBrown_themeLight
                        )
                    } else {
                        Text(
                            "Texto", modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
                            textAlign = TextAlign.Center, color = Color.Black
                        )
                    }
                    RadioButton(
                        selected = !selectOpcion,
                        onClick = { onSelectOpcion(false) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = PinkBrown_themeLight,
                            unselectedColor = GrayWhite_themeLight,
                            disabledUnselectedColor = GrayWhite_themeLight
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun configuracionApp(
    onBackPage: () -> Unit = {},
    selectModo: Boolean,
    onSelectModo: (Boolean) -> Unit = {},
    selectOpcion: Boolean,
    onSelectOpcion: (Boolean) -> Unit = {}
){
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                navigationIcon = {
                    IconButton( onClick = onBackPage ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                title = {
                    Text("Configuración", modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 3.dp),
                        textAlign = TextAlign.Right,
                        style =
                            TextStyle(
                                fontSize = 27.sp,
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.ExtraBold
                            )
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 10.dp, end = 10.dp, top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                cardModo(
                    selectModo = selectModo,
                    onSelectModo = { nuevoModo -> onSelectModo(nuevoModo) }
                )

                Spacer(modifier = Modifier.padding(15.dp))

                //Manejo de Icono a Texto en la pantalla de Catalogo de Emprendimientos
                cardPreferencia(
                    selectOpcion = selectOpcion,
                    onSelectOpcion = { select -> onSelectOpcion(select) }
                )
            }
        }
    }
}

//@Preview(showBackground = true/*, showSystemUi = true*/)
//@Composable
//fun muestra() {
//    ProyectoTheme(darkTheme = false) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            cardPreferencia()
//        }
//    }
//}