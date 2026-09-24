package com.example.guia8.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guia8.ui.screens.HomeScreen
import com.example.guia8.ui.screens.PerfilScreen
import com.example.guia8.ui.screens.SettingsScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {

            HomeScreen(
                onComenzarClick = {
                    navController.navigate(Screen.Perfil.route)
                }
            )
        }

        composable(Screen.Perfil.route) {

            PerfilScreen(

                onSettingsClick = {
                    navController.navigate(
                        Screen.Settings.route
                    )
                },

                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}