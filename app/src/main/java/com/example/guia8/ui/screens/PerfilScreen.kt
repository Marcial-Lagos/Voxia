package com.example.guia8.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.guia8.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    viewModel: UsuarioViewModel,
    onSettingsClick: () -> Unit,
    onVolverClick: () -> Unit
) {
    val perfiles by
        viewModel.perfiles.collectAsState(
            initial = emptyList()
        )

    val perfilSeleccionadoId by
        viewModel.perfilSeleccionadoId.collectAsState(
            initial = null
        )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Perfiles")
                },
                navigationIcon = {
                    TextButton(
                        onClick = onVolverClick
                    ) {
                        Text("Volver")
                    }
                },
                colors =
                    TopAppBarDefaults
                        .topAppBarColors(
                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .primaryContainer
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
                Arrangement.spacedBy(16.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            Text(
                text = "Perfiles registrados",
                style =
                    MaterialTheme
                        .typography
                        .headlineMedium
            )

            Text(
                text =
                    "Cada perfil conserva su propio mejor puntaje.",
                style =
                    MaterialTheme
                        .typography
                        .bodyMedium
            )

            if (perfiles.isEmpty()) {
                Text(
                    text =
                        "Todavía no hay perfiles guardados.",
                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = perfiles,
                        key = { perfil ->
                            perfil.id
                        }
                    ) { perfil ->

                        val esActivo =
                            perfil.id ==
                                perfilSeleccionadoId

                        Card(
                            modifier =
                                Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier =
                                    Modifier.padding(16.dp),
                                verticalArrangement =
                                    Arrangement
                                        .spacedBy(8.dp)
                            ) {
                                Text(
                                    text = perfil.nombre,
                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium
                                )

                                Text(
                                    "Correo: ${perfil.correo}"
                                )

                                Text(
                                    "Dirección: ${perfil.direccion}"
                                )

                                Text(
                                    "🏆 Mejor puntaje: ${perfil.mejorPuntaje}"
                                )

                                if (esActivo) {
                                    Button(
                                        onClick = {},
                                        enabled = false,
                                        modifier =
                                            Modifier.fillMaxWidth()
                                    ) {
                                        Text("Perfil activo")
                                    }
                                } else {
                                    OutlinedButton(
                                        onClick = {
                                            viewModel
                                                .seleccionarPerfil(
                                                    perfil.id
                                                )
                                        },
                                        modifier =
                                            Modifier.fillMaxWidth()
                                    ) {
                                        Text("Usar este perfil")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Button(
                onClick = onSettingsClick,
                modifier =
                    Modifier.fillMaxWidth()
            ) {
                Text("Ir a Configuración")
            }
        }
    }
}
