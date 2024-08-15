package com.example.new_kuz.presentation.screens.appscreens.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.new_kuz.R
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.inAppNav
import com.example.new_kuz.domain.modules.inAppNav.message
import com.example.new_kuz.navigation.User
import com.example.new_kuz.presentation.events.ChatScreenEvent
import com.example.new_kuz.presentation.screens.appscreens.components.chatItem
import com.example.new_kuz.presentation.screens.appscreens.components.roundedSearchBar
import com.example.new_kuz.presentation.screens.auth.components.Logo
import com.example.new_kuz.presentation.states.ChatScreenState
import com.example.new_kuz.presentation.viewmodels.ChatScreenViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ChatNavigation(
    navController: NavController,
){
    val viewModel: ChatScreenViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value

    chatUi(
        state= state,
        onEvent = {chatEvent ->
            when (chatEvent){
                is ChatScreenEvent.onChatClick -> {
                    val user = chatEvent.user
                    navController.navigate(message.createMessage(user.uid))
                    viewModel.onEvent(chatEvent)
                }
                is ChatScreenEvent.onQueryChange ->{
                    viewModel.onEvent(chatEvent)
                }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun chatUi(
    state: ChatScreenState,
    onEvent:(ChatScreenEvent) -> Unit
) {
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()


    Scaffold(
        topBar = {
            ChatsScreenTopBar(
                search = state.query,
                onSearchChange = { onEvent(ChatScreenEvent.onQueryChange(it))},
                filter = {},
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {

            LazyColumn(
                state = listState
            ) {
                item {
                    if (state.chats.isEmpty()) {
                        Column(
                            Modifier.fillMaxSize().padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Logo(color = MaterialTheme.colorScheme.primary, fontSize = 50)
                            Text(text = "Thank you For Using Kuz. Please connect to more users.",
                                textAlign = TextAlign.Center)
                        }
                    }
                }
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
                        isActive = users.active,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatsScreenTopBar(
    search: String,
    onSearchChange: (String) -> Unit,
    filter: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            Text(text = "Chats", fontSize = 50.sp, fontWeight = FontWeight.Bold)
        },
      title = {
//            if (scrollBehavior.state.) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    roundedSearchBar(
                        text = search,
                        onTextChange = onSearchChange
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = filter) {
                        Icon(
                            painter = painterResource(id = R.drawable.filter_icon),
                            contentDescription = "Filter"
                        )
                    }
//                }
            }
//            else {
//                IconButton(onClick = filter) {
//                    Icon(
//                        imageVector = Icons.Filled.Search,
//                        contentDescription = "Filter"
//                    )
//                }
//            }
        })
}
