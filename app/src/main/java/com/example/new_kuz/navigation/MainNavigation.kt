package com.example.new_kuz.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.new_kuz.KUZTheme
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.domain.modules.welcomeRoute
import com.example.new_kuz.navigation.authroutes.authRoutes
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigation() {
    val navController = rememberNavController()


    KUZTheme {
        NavHost(navController = navController,
            startDestination = if(FirebaseAuth.getInstance().currentUser == null )Graph.MAIN else Graph.HOME
        ){
            composable(Graph.HOME){
                Home()
            }
            welcomeGraph(navController)
            authRoutes(navController)
        }
    }

}


object Graph {
    const val AUTHENTCATION = "auth_graph"
    const val SIGN_UP = "sign_up_route"
    const val HOME = "home_graph"
    const val MAIN = "welcome_graph"
}