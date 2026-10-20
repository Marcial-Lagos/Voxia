package com.example.guia8.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guia8.ui.screens.HomeScreen
import com.example.guia8.ui.screens.JuegoScreen
import com.example.guia8.ui.screens.PerfilScreen
import com.example.guia8.ui.screens.SettingsScreen
import com.example.guia8.viewmodel.JuegoViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val juegoViewModel: JuegoViewModel = viewModel()
    val estadoJuego by juegoViewModel.estado.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(
                onComenzarClick = {
                    navController.navigate(Screen.Perfil.route)
                },
                onJugarClick = {
                    juegoViewModel.reiniciarJuego()
                    navController.navigate(Screen.Juego.route)
                },
                mejorPuntaje = estadoJuego.mejorPuntaje,
                cargandoRecord = estadoJuego.cargandoPreferencias
            )
        }

        composable(Screen.Juego.route) {
            JuegoScreen(
                viewModel = juegoViewModel,
                onVolverInicio = {
                    navController.popBackStack(
                        route = Screen.Home.route,
                        inclusive = false
                    )
                }
            )
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onVolverClick = {
                    navController.popBackStack(
                        route = Screen.Home.route,
                        inclusive = false
                    )
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onVolverClick = {
                    navController.popBackStack()
                },
                onInicioClick = {
                    navController.popBackStack(
                        route = Screen.Home.route,
                        inclusive = false
                    )
                }
            )
        }
    }
}
