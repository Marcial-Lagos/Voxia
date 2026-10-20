package com.example.guia8.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.guia8.data.PreferenciasDataStore
import com.example.guia8.data.local.VoxiaDatabase
import com.example.guia8.model.JuegoUiState
import com.example.guia8.model.TarjetaJuego
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JuegoViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val preferenciasDataStore =
        PreferenciasDataStore(application)

    private val perfilDao =
        VoxiaDatabase.obtenerInstancia(application).perfilDao()

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

    init {
        observarPerfilActivo()
    }

    private fun observarPerfilActivo() {
        viewModelScope.launch {
            preferenciasDataStore.perfilSeleccionadoId.collectLatest { perfilId ->

                if (perfilId == null) {
                    _estado.update {
                        it.copy(
                            perfilActivoId = null,
                            perfilActivoNombre = null,
                            mejorPuntaje = 0,
                            cargandoPreferencias = false
                        )
                    }
                    return@collectLatest
                }

                perfilDao.obtenerPerfilPorId(perfilId).collect { perfil ->
                    _estado.update {
                        it.copy(
                            perfilActivoId = perfil?.id,
                            perfilActivoNombre = perfil?.nombre,
                            mejorPuntaje = perfil?.mejorPuntaje ?: 0,
                            cargandoPreferencias = false
                        )
                    }
                }
            }
        }
    }

    fun responder(respuestaUsuario: Boolean) {
        val estadoActual = _estado.value

        if (
            estadoActual.mostrarFeedback ||
            estadoActual.juegoTerminado
        ) {
            return
        }

        val tarjeta = estadoActual.tarjetaActual ?: return
        val acerto =
            respuestaUsuario == tarjeta.esCorrecta

        _estado.update {
            it.copy(
                puntaje = if (acerto) {
                    it.puntaje + 1
                } else {
                    it.puntaje
                },
                vidas = if (acerto) {
                    it.vidas
                } else {
                    (it.vidas - 1).coerceAtLeast(0)
                },
                mostrarFeedback = true,
                respuestaCorrecta = acerto
            )
        }
    }

    fun avanzarTarjeta() {
        val estadoActual = _estado.value

        if (
            !estadoActual.mostrarFeedback ||
            estadoActual.juegoTerminado
        ) {
            return
        }

        val sinVidas =
            estadoActual.vidas <= 0

        val ultimaTarjeta =
            estadoActual.indiceActual >= tarjetas.lastIndex

        if (sinVidas || ultimaTarjeta) {
            guardarRecordSiCorresponde(
                estadoActual.puntaje
            )

            _estado.update {
                it.copy(
                    mostrarFeedback = false,
                    respuestaCorrecta = null,
                    juegoTerminado = true,
                    mejorPuntaje = maxOf(
                        it.mejorPuntaje,
                        it.puntaje
                    )
                )
            }
            return
        }

        val siguienteIndice =
            estadoActual.indiceActual + 1

        _estado.update {
            it.copy(
                tarjetaActual = tarjetas[siguienteIndice],
                indiceActual = siguienteIndice,
                mostrarFeedback = false,
                respuestaCorrecta = null
            )
        }
    }

    private fun guardarRecordSiCorresponde(
        puntajeFinal: Int
    ) {
        val estadoActual = _estado.value
        val perfilId =
            estadoActual.perfilActivoId ?: return

        if (
            puntajeFinal <=
            estadoActual.mejorPuntaje
        ) {
            return
        }

        viewModelScope.launch {
            perfilDao.actualizarMejorPuntaje(
                perfilId = perfilId,
                puntaje = puntajeFinal
            )
        }
    }

    fun reiniciarJuego() {
        val estadoActual = _estado.value

        _estado.value = JuegoUiState(
            tarjetaActual = tarjetas.firstOrNull(),
            mejorPuntaje = estadoActual.mejorPuntaje,
            cargandoPreferencias =
                estadoActual.cargandoPreferencias,
            perfilActivoId =
                estadoActual.perfilActivoId,
            perfilActivoNombre =
                estadoActual.perfilActivoNombre
        )
    }
}
