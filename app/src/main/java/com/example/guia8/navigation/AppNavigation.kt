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
import com.example.guia8.ui.screens.RegistroScreen
import com.example.guia8.ui.screens.ResumenScreen
import com.example.guia8.ui.screens.SettingsScreen
import com.example.guia8.viewmodel.JuegoViewModel
import com.example.guia8.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val juegoViewModel: JuegoViewModel = viewModel()
    val estadoJuego by juegoViewModel.estado.collectAsState()

    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(
                onJugarClick = {
                    juegoViewModel.reiniciarJuego()
                    navController.navigate(Screen.Juego.route)
                },
                onRegistroClick = {
                    navController.navigate(Screen.Registro.route)
                },
                onPerfilClick = {
                    navController.navigate(Screen.Perfil.route)
                },
                mejorPuntaje = estadoJuego.mejorPuntaje,
                cargandoRecord = estadoJuego.cargandoPreferencias
            )
        }

        composable(Screen.Registro.route) {
            RegistroScreen(
                viewModel = usuarioViewModel,
                onRegistroExitoso = {
                    navController.navigate(Screen.Resumen.route)
                },
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Resumen.route) {
            ResumenScreen(
                viewModel = usuarioViewModel,
                onInicioClick = {
                    navController.popBackStack(
                        route = Screen.Home.route,
                        inclusive = false
                    )
                },
                onPerfilClick = {
                    navController.navigate(Screen.Perfil.route)
                }
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
