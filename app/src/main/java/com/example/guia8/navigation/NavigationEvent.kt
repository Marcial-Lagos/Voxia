package com.example.guia8.navigation

sealed class NavigationEvent {

    data class NavigateTo(
        val route: String
    ) : NavigationEvent()

    object PopBackStack : NavigationEvent()
}