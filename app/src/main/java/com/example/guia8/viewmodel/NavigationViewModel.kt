package com.example.guia8.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guia8.navigation.NavigationEvent
import com.example.guia8.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class NavigationViewModel : ViewModel() {

    // Flujo interno donde se generan los eventos de navegación
    private val _navigationEvents =
        MutableSharedFlow<NavigationEvent>()

    // Flujo público que podrá observar la interfaz
    val navigationEvents: SharedFlow<NavigationEvent> =
        _navigationEvents.asSharedFlow()


    // Navega hacia una pantalla determinada
    fun navigateTo(screen: Screen) {

        viewModelScope.launch {

            _navigationEvents.emit(
                NavigationEvent.NavigateTo(screen.route)
            )
        }
    }


    // Regresa a la pantalla anterior
    fun popBackStack() {

        viewModelScope.launch {

            _navigationEvents.emit(
                NavigationEvent.PopBackStack
            )
        }
    }
}