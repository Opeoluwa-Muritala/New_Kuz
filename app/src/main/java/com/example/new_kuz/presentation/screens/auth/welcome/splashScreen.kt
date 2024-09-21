package com.example.new_kuz.presentation.screens.auth.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.new_kuz.domain.modules.welcomeRoute
import com.example.new_kuz.presentation.screens.auth.components.Logo
import com.example.new_kuz.KUZTheme
import com.example.new_kuz.navigation.Graph
import com.example.new_kuz.presentation.viewmodels.DataViewmodel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashUi(navController: NavController) {

    KUZTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo(color = MaterialTheme.colorScheme.background, fontSize = 120)
        }
        LaunchedEffect(key1 = Unit) {
            delay(3000)
            navController.popBackStack()
            if (FirebaseAuth.getInstance().currentUser == null){
                navController.navigate(welcomeRoute.welcome.route)
            } else navController.navigate(Graph.HOME)
        }
    }
}

