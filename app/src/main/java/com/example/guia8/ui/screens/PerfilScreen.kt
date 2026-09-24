package com.example.guia8.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onSettingsClick: () -> Unit,
    onVolverClick: () -> Unit
) {

    Scaffold(

        // Barra superior de la pantalla Perfil
        topBar = {

            TopAppBar(

                title = {
                    Text("Perfil")
                },

                navigationIcon = {

                    TextButton(
                        onClick = onVolverClick
                    ) {
                        Text("Volver")
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                )
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(
                    space = 20.dp,
                    alignment = Alignment.CenterVertically
                ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = "Perfil de usuario",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Aquí se mostrará la información del usuario.",
                style = MaterialTheme.typography.bodyLarge
            )

            Button(
                onClick = onSettingsClick
            ) {

                Text("Ir a Configuración")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {

    MaterialTheme {

        PerfilScreen(
            onSettingsClick = {},
            onVolverClick = {}
        )
    }
}