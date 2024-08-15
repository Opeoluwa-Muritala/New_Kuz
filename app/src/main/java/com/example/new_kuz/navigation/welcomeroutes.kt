package com.example.new_kuz.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.new_kuz.domain.modules.welcomeRoute
import com.example.new_kuz.domain.modules.welcomeRoute.*
import com.example.new_kuz.presentation.screens.auth.welcome.SplashUi
import com.example.new_kuz.presentation.screens.auth.welcome.WelcomeNavigation


fun NavGraphBuilder.welcomeGraph(navController: NavHostController) {
    navigation(startDestination = welcomeRoute.splash.route, route = Graph.MAIN) {
        composable(splash.route) {
            SplashUi(navController = navController)
        }
        composable(welcome.route) {
            WelcomeNavigation(navController = navController)
        }
    }
}