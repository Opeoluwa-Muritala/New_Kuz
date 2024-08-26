package com.example.new_kuz.navigation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.new_kuz.domain.modules.inAppNav
import com.example.new_kuz.presentation.viewmodels.DataViewmodel

@Composable
fun Home( navController: NavHostController = rememberNavController()) {
        val viewmodel: DataViewmodel = hiltViewModel()
        viewmodel.currentUser
        val screens = listOf(
                inAppNav.home.route,
                inAppNav.chat.route,
                inAppNav.settings.route
        )
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        if (screens.any { it == currentDestination?.route }) {
                MultiNavigationBar(navController = navController){
                        AppNav(navController)
                }
        } else {
                AppNav(navController)
        }
}