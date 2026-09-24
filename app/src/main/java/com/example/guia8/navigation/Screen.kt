
package com.example.guia8.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object Perfil : Screen("perfil")

    object Settings : Screen("settings")
}