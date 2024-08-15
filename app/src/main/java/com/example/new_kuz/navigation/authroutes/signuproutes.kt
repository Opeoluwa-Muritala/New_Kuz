package com.example.new_kuz.navigation.authroutes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.new_kuz.domain.modules.authRoute.createPassword
import com.example.new_kuz.domain.modules.authRoute.personalInfo
import com.example.new_kuz.domain.modules.authRoute.phoneNumber
import com.example.new_kuz.domain.modules.authRoute.signUp
import com.example.new_kuz.domain.modules.authRoute.verifyEmail
import com.example.new_kuz.domain.modules.authRoute.verifyPhone
import com.example.new_kuz.presentation.viewmodels.SignUpViewModel
import com.example.new_kuz.navigation.Graph
import com.example.new_kuz.presentation.screens.auth.signup.SignUpNavigation
import com.example.new_kuz.presentation.screens.auth.signup.userdetails.CreatePassword
import com.example.new_kuz.presentation.screens.auth.signup.userdetails.EnterPhone
import com.example.new_kuz.presentation.screens.auth.signup.userdetails.PersonalInfo
import com.example.new_kuz.presentation.screens.auth.signup.userdetails.VerifyEmail
import com.example.new_kuz.presentation.screens.auth.signup.userdetails.VerifyNumber
import kotlinx.coroutines.flow.single

fun NavGraphBuilder.signUpRoutes(navController: NavHostController){


    navigation(startDestination = signUp.route, route = Graph.SIGN_UP){
        composable(signUp.route){entry ->
            val viewModel = entry.sharedViewModel<SignUpViewModel>(navController,)
            val state by viewModel.state.collectAsStateWithLifecycle()
            SignUpNavigation(
                navController = navController,
                state = state,
                onEvents = viewModel::onEvents,
                toastEvent = viewModel.snackBarEvenFlow
            )
        }
        composable(phoneNumber.route){entry->
            val viewModel = entry.sharedViewModel<SignUpViewModel>(navController,)
            val state by viewModel.state.collectAsStateWithLifecycle()
            EnterPhone(navController = navController, state = state, onEvent = viewModel::onEvents)
        }
        composable(createPassword.route){entry->
            val viewModel = entry.sharedViewModel<SignUpViewModel>(navController,)
            val state by viewModel.state.collectAsStateWithLifecycle()
            CreatePassword(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvents,
                snackBarEvent = viewModel.snackBarEvenFlow
            )
        }
        composable(personalInfo.route){entry->
            val viewModel = entry.sharedViewModel<SignUpViewModel>(navController,)
            val state by viewModel.state.collectAsStateWithLifecycle()
            PersonalInfo(navController = navController, state = state, onEvent = viewModel::onEvents)
        }
        composable(verifyEmail.route){entry->
            val viewModel = entry.sharedViewModel<SignUpViewModel>(navController,)
            val state by viewModel.state.collectAsStateWithLifecycle()
            VerifyEmail(navController = navController, state = state, onEvent = viewModel::onEvents)
        }
        composable(verifyPhone.route){entry->
            val viewModel = entry.sharedViewModel<SignUpViewModel>(navController,)
            val state by viewModel.state.collectAsStateWithLifecycle()
            VerifyNumber(navController = navController, onEvent = viewModel::onEvents, state = state)
        }
    }

}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}