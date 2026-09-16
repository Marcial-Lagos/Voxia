package com.example.guia8

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    // Scaffold entrega la estructura principal de la pantalla
    Scaffold(

        // Barra superior de la aplicación
        topBar = {

            TopAppBar(

                title = {
                    Text("Voxia")
                },

                // Utilizamos colores provenientes de MaterialTheme
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }

    ) { innerPadding ->

        // Column organiza todos los elementos verticalmente
        Column(

            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),

            // Mantiene un espacio uniforme entre los elementos
            verticalArrangement = Arrangement.spacedBy(20.dp),

            // Centra horizontalmente los elementos
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Título principal de bienvenida
            Text(
                text = "¡Bienvenido a Voxia!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )


            // Texto secundario
            Text(
                text = "Tu espacio para comunicarte y compartir.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )


            // Logo de la aplicación
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de Voxia",

                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),

                contentScale = ContentScale.Fit
            )


            // Línea divisoria para separar visualmente el contenido
            HorizontalDivider()


            // Tarjeta informativa
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = "Comienza a explorar",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Accede a las funciones principales de Voxia desde esta pantalla.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }


            // Botón principal
            Button(
                onClick = {
                    // Acción futura del botón
                }
            ) {

                Text("Comenzar")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    MaterialTheme {
        HomeScreen()
    }
}