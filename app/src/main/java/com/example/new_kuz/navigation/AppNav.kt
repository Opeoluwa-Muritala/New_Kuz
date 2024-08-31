package com.example.new_kuz.navigation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.new_kuz.R
import com.example.new_kuz.domain.modules.Contact
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.domain.modules.inAppNav
import com.example.new_kuz.domain.modules.inAppNav.about
import com.example.new_kuz.domain.modules.inAppNav.chat
import com.example.new_kuz.domain.modules.inAppNav.home
import com.example.new_kuz.domain.modules.inAppNav.message
import com.example.new_kuz.domain.modules.inAppNav.settings
import com.example.new_kuz.presentation.screens.appscreens.chat.ChatNavigation
import com.example.new_kuz.presentation.screens.appscreens.chat.ContactNavigation
import com.example.new_kuz.presentation.screens.appscreens.chat.archived.ArchiveNavigation
import com.example.new_kuz.presentation.screens.appscreens.chat.messages.MessageNavigation
import com.example.new_kuz.presentation.screens.appscreens.home.HomeNavigation
import com.example.new_kuz.presentation.screens.appscreens.settings.AboutUiNavigation
import com.example.new_kuz.presentation.screens.appscreens.settings.SettingsNavigation
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun AppNav(navController: NavHostController){

    NavHost(navController = navController, startDestination = chat.route) {
        composable(chat.route) {
            ChatNavigation(
                navController = navController,
            )
        }
        composable(
            route = message.route,
            arguments = listOf(
                navArgument("receiver"){
                    type = NavType.StringType
                }
            )
        ){
            MessageNavigation(
                navController = navController
            )
        }
        composable(settings.route) {
            SettingsNavigation(navController = navController)
        }
        composable<Users>{
            val arguements = it.toRoute<Users>()
            ContactNavigation(navController, user = arguements)
        }
        composable(about.route) {
            AboutUiNavigation(navController)
        }
        composable(home.route) {
            HomeNavigation(navController)
        }
        composable(inAppNav.archive.route) {
            ArchiveNavigation(navController)
        }
    }
}


//@OptIn(ExperimentalMaterial3AdaptiveApi::class)
//@Composable
//fun MessageNav(navController: NavHostController) {
//    val navigator = rememberListDetailPaneScaffoldNavigator<User>()
//    val setUser = User(
//        name = "Test Scaffold",
//        image = R.drawable.download
//    )
//
//
//    ListDetailPaneScaffold(
//        directive = navigator.scaffoldDirective,
//        value = navigator.scaffoldValue,
//        listPane = {
//            AnimatedPane {
//
//                ChatNavigation(
//                    navController = navController,
//                    onChatClick = {
//                        navigator.navigateTo(
//                            pane = ListDetailPaneScaffoldRole.Detail,
//                            content = setUser
//                        )
//                    }
//                )
//            }
//
//        },
//        detailPane = {
//            val user = navigator.currentDestination?.content ?: User()
//
//            AnimatedPane {
//
//                MessageNavigation(
//                    user = user,
//                    onContactClick = {
//                        navigator.navigateTo(
//                            pane = ListDetailPaneScaffoldRole.Extra,
//                            content = setUser
//                        )
//                    },
//                    onBackClick = { navigator.navigateBack() }
//                )
//            }
//        },
//        extraPane = {
//            val user = navigator.currentDestination?.content ?: User()
//            AnimatedPane {
//
//                ContactNavigation(
//                    user = user,
//                    navController = navController,
//                    onBackClick = {
//                        navigator.navigateBack()
//                    }
//                )
//            }
//        }
//    )
//}
//
@Serializable
data class User(
    val uid: String = "",
    val name: String = "",
    val imageUrl: String? = null,
    val connectedUsers: List<String> = emptyList(),
    val archivedUsers: List<String> = emptyList(),
    val blockedUsers: List<String> = emptyList(),
    val active: Boolean = true,
    val dateOfBirth: String = "",
    val bio: String? = null,
    val gender: String = ""
)