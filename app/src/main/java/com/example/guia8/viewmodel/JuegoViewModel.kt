package com.example.guia8.viewmodel

import androidx.lifecycle.ViewModel
import com.example.guia8.model.JuegoUiState
import com.example.guia8.model.TarjetaJuego
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class JuegoViewModel : ViewModel() {

    // Tarjetas iniciales del MVP. Más adelante se pueden mover a una fuente de datos separada.
    private val tarjetas = listOf(
        TarjetaJuego("Merhaba", "Hola", true),
        TarjetaJuego("Teşekkürler", "Adiós", false),
        TarjetaJuego("Lütfen", "Por favor", true),
        TarjetaJuego("Evet", "No", false),
        TarjetaJuego("Hayır", "No", true),
        TarjetaJuego("Su", "Agua", true),
        TarjetaJuego("Ekmek", "Queso", false)
    )

    private val _estado = MutableStateFlow(
        JuegoUiState(
            tarjetaActual = tarjetas.firstOrNull()
        )
    )

    val estado: StateFlow<JuegoUiState> = _estado

    val totalTarjetas: Int
        get() = tarjetas.size

    // Evalúa la opción elegida por el usuario: true = Verdadero, false = Falso.
    fun responder(respuestaUsuario: Boolean) {
        val estadoActual = _estado.value

        // Evita responder dos veces mientras se muestra el feedback.
        if (estadoActual.mostrarFeedback || estadoActual.juegoTerminado) {
            return
        }

        val tarjeta = estadoActual.tarjetaActual ?: return
        val acerto = respuestaUsuario == tarjeta.esCorrecta

        _estado.update {
            it.copy(
                puntaje = if (acerto) it.puntaje + 1 else it.puntaje,
                vidas = if (acerto) it.vidas else (it.vidas - 1).coerceAtLeast(0),
                mostrarFeedback = true,
                respuestaCorrecta = acerto
            )
        }
    }

    // Se llama después de mostrar brevemente el mensaje Correcto/Incorrecto.
    fun avanzarTarjeta() {
        val estadoActual = _estado.value

        if (!estadoActual.mostrarFeedback || estadoActual.juegoTerminado) {
            return
        }

        val sinVidas = estadoActual.vidas <= 0
        val ultimaTarjeta = estadoActual.indiceActual >= tarjetas.lastIndex

        if (sinVidas || ultimaTarjeta) {
            _estado.update {
                it.copy(
                    mostrarFeedback = false,
                    respuestaCorrecta = null,
                    juegoTerminado = true
                )
            }
            return
        }

        val siguienteIndice = estadoActual.indiceActual + 1

        _estado.update {
            it.copy(
                tarjetaActual = tarjetas[siguienteIndice],
                indiceActual = siguienteIndice,
                mostrarFeedback = false,
                respuestaCorrecta = null
            )
        }
    }

    fun reiniciarJuego() {
        _estado.value = JuegoUiState(
            tarjetaActual = tarjetas.firstOrNull()
        )
    }
}
