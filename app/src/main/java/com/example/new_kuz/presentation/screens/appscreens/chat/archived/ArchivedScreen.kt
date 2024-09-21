package com.example.new_kuz.presentation.screens.appscreens.chat.archived

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.inAppNav.message
import com.example.new_kuz.presentation.events.ChatScreenEvent
import com.example.new_kuz.presentation.screens.appscreens.components.chatItem
import com.example.new_kuz.presentation.screens.auth.components.Logo
import com.example.new_kuz.presentation.states.ChatScreenState
import com.example.new_kuz.presentation.viewmodels.ArchivedViewModel
import com.example.new_kuz.presentation.viewmodels.ChatScreenViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ArchiveNavigation(
    navController: NavController,
){
    val viewModel: ArchivedViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value

    ArchiveScreen(
        state= state,
        onEvent = {chatEvent ->
            when (chatEvent){
                is ChatScreenEvent.onChatClick -> {
                    val user = chatEvent.user
                    navController.navigate(message.createMessage(user.uid))
                    viewModel.onEvent(chatEvent)
                }
                else -> {}
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ArchiveScreen(
    state: ChatScreenState,
    onEvent:(ChatScreenEvent) -> Unit
) {


    Scaffold(
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {

            LazyColumn() {
                items(state.chats) { users ->
                    val lastMessages = if (state.messages.isEmpty()) Messages(
                        timeline = "",
                        message = "",
                        sentby = "",
                        sentto = "",
                        images = emptyList()
                    ) else state.messages.filter {
                        it.sentby == users.uid || it.sentto == users.uid
                    }.lastOrNull()

                    chatItem(
                        image = users.imageUrl ?: "",
                        isActive = !users.connectedUsers.contains(state.currentUser.uid) &&
                                !state.connected.contains(users.uid),
                        time = lastMessages?.timeline ?: "",
                        chats = null,
                        lastMessage = lastMessages?.message ?: lastMessages?.images?.firstOrNull() ?: "",
                        name = users.name,
                        onChatClick = { onEvent(ChatScreenEvent.onChatClick(users)) }
                    )
                }
            }
        }
    }
}