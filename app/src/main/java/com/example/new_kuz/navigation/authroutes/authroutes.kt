package com.example.new_kuz.navigation.authroutes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.domain.modules.authRoute.*
import com.example.new_kuz.navigation.Graph
import com.example.new_kuz.presentation.screens.auth.signin.forgotpassword.forgotPasword
import com.example.new_kuz.presentation.screens.auth.signin.forgotpassword.newPassword
import com.example.new_kuz.presentation.screens.auth.signin.forgotpassword.verifyPin
import com.example.new_kuz.presentation.screens.auth.signin.signInNavigation


fun NavGraphBuilder.authRoutes(navController: NavHostController){
    navigation(startDestination = signIn.route, route = Graph.AUTHENTCATION) {
        composable(signIn.route) {
            signInNavigation(navController = navController)
        }
        composable(newPassword.route) {
            newPassword(navController = navController)
        }
        composable(forgotPassword.route) {
            forgotPasword(navController = navController)
        }
        composable(verifyPin.route){
            verifyPin(navController = navController)
        }
        signUpRoutes(navController)
    }
}



