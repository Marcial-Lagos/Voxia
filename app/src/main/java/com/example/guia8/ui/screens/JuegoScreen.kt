package com.example.guia8.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guia8.ui.utils.vibrarAcierto
import com.example.guia8.viewmodel.JuegoViewModel
import kotlinx.coroutines.delay

@Composable
fun JuegoScreen(
    viewModel: JuegoViewModel,
    onVolverInicio: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(
        estado.mostrarFeedback,
        estado.respuestaCorrecta,
        estado.indiceActual
    ) {
        if (estado.mostrarFeedback) {
            if (estado.respuestaCorrecta == true) {
                vibrarAcierto(context)
            }

            delay(900)
            viewModel.avanzarTarjeta()
        }
    }

    if (estado.juegoTerminado) {
        JuegoTerminado(
            puntaje = estado.puntaje,
            totalTarjetas = viewModel.totalTarjetas,
            onReiniciar = viewModel::reiniciarJuego,
            onVolverInicio = onVolverInicio
        )
        return
    }

    val tarjeta = estado.tarjetaActual ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Doğru mu?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "¿Es verdad?",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Pregunta ${estado.indiceActual + 1} de ${viewModel.totalTarjetas}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = tarjeta.palabraTurca,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "=",
                    fontSize = 28.sp
                )

                Text(
                    text = tarjeta.traduccionMostrada,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (estado.mostrarFeedback) {
            Text(
                text = if (estado.respuestaCorrecta == true) {
                    "¡Correcto! +1 punto"
                } else {
                    "Incorrecto. Pierdes una vida"
                },
                style = MaterialTheme.typography.titleMedium,
                color = if (estado.respuestaCorrecta == true) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                },
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = "¿La traducción es correcta?",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.responder(false) },
                modifier = Modifier.weight(1f),
                enabled = !estado.mostrarFeedback
            ) {
                Text("Falso")
            }

            Button(
                onClick = { viewModel.responder(true) },
                modifier = Modifier.weight(1f),
                enabled = !estado.mostrarFeedback
            ) {
                Text("Verdadero")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Vidas: ${"❤️".repeat(estado.vidas)}"
            )

            Text(
                text = "Puntaje: ${estado.puntaje}"
            )
        }
    }
}

@Composable
private fun JuegoTerminado(
    puntaje: Int,
    totalTarjetas: Int,
    onReiniciar: () -> Unit,
    onVolverInicio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Juego terminado",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Puntaje: $puntaje / $totalTarjetas",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onReiniciar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Jugar nuevamente")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onVolverInicio,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
