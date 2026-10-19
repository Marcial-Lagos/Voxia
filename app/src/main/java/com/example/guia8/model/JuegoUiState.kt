package com.example.guia8.model

// Estado reactivo del juego Doğru mu?
data class JuegoUiState(
    val tarjetaActual: TarjetaJuego? = null,
    val indiceActual: Int = 0,
    val puntaje: Int = 0,
    val vidas: Int = 3,
    val mostrarFeedback: Boolean = false,
    val respuestaCorrecta: Boolean? = null,
    val juegoTerminado: Boolean = false,
    val mejorPuntaje: Int = 0,
    val cargandoPreferencias: Boolean = true
)
