package com.example.new_kuz.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.new_kuz.KUZTheme
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.domain.modules.welcomeRoute
import com.example.new_kuz.navigation.authroutes.authRoutes
import com.example.new_kuz.presentation.screens.auth.welcome.SplashUi
import com.google.firebase.auth.FirebaseAuth
import com.plcoding.globalsnackbarscompose.ObserveAsEvents
import com.plcoding.globalsnackbarscompose.SnackbarController
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Long
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    KUZTheme {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {innerPadding ->
            NavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                        startDestination =  Graph.MAIN
            ) {
                composable(Graph.HOME) {
                    Home()
                }

                welcomeGraph(navController)
                authRoutes(navController)
            }
        }
    }


}


object Graph {
    const val AUTHENTCATION = "auth_graph"
    const val SIGN_UP = "sign_up_route"
    const val HOME = "home_graph"
    const val MAIN = "welcome_graph"
    const val SPLASH = "splash"
}