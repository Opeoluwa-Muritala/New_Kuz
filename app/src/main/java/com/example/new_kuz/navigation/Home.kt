package com.example.new_kuz.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.new_kuz.domain.modules.inAppNav
import com.example.new_kuz.presentation.viewmodels.DataViewmodel

@Composable
fun Home( navController: NavHostController = rememberNavController()) {
        val viewmodel: DataViewmodel = hiltViewModel()
        val loadingState = viewmodel.loadingState.collectAsStateWithLifecycle().value
        LaunchedEffect(key1 = true) {
                when(loadingState){
                        true -> {
                                Log.d("Loading", "Loading...")
                        }
                        false -> {
                                Log.d("Loading", "Loading Finished...")
                        }
                }
        }
        val screens = listOf(
                inAppNav.home,
                inAppNav.chat,
                inAppNav.settings)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        
        var select by rememberSaveable{ mutableIntStateOf(1) }

        if (screens.any { it.route == currentDestination?.route }) {
                MultiNavigationBar(
                        navController = navController,
                        selected = select,
                        select = { select = it }) {
                        AppNav(navController)
                }
        } else {
                AppNav(navController = navController)
        }
}