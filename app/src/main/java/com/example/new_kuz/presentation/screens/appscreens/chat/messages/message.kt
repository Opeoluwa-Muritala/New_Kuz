package com.example.new_kuz.presentation.screens.appscreens.chat.messages

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.new_kuz.R
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.presentation.events.MessageScreenEvent
import com.example.new_kuz.presentation.screens.appscreens.components.chatBar
import com.example.new_kuz.presentation.screens.appscreens.components.chatSearch
import com.example.new_kuz.presentation.screens.appscreens.components.menu
import com.example.new_kuz.presentation.screens.appscreens.components.menuItems
import com.example.new_kuz.presentation.screens.appscreens.components.menuItems.archive
import com.example.new_kuz.presentation.screens.appscreens.components.menuItems.block
import com.example.new_kuz.presentation.screens.appscreens.components.menuItems.clear
import com.example.new_kuz.presentation.screens.appscreens.components.menuItems.contact
import com.example.new_kuz.presentation.screens.appscreens.components.menuItems.search
import com.example.new_kuz.presentation.screens.components.filledButton
import com.example.new_kuz.presentation.screens.components.outlinedButton
import com.example.new_kuz.presentation.states.MessageScreenState
import com.example.new_kuz.presentation.viewmodels.MessageScreenViewModel
import com.example.new_kuz.util.shimmerLoadingAnimation
import java.util.Locale


@Composable
fun MessageNavigation(
    navController: NavController
){
    val viewModel: MessageScreenViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value
    messageUI(
        state = state,
        onEvent = {event ->
            when (event){
                MessageScreenEvent.onBackClick -> {
                    navController.navigateUp()
                }
                is MessageScreenEvent.onContactClick -> {
                    val user = event.user
                    navController.navigate(
                        Users(
                            name = user.name,
                            uid = user.uid,
                            imageUrl = user.imageUrl,
                            gender = user.gender,
                            active = user.active,
                            bio = user.bio,
                            dateOfBirth = user.dateOfBirth,
                            archivedUsers = user.archivedUsers,
                            connectedUsers = user.connectedUsers,
                            blockedUsers = user.blockedUsers,
                        )
                    )
                }
                else -> viewModel.onEvent(event)
            }

        }
    )
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun messageUI(
    state: MessageScreenState,
    onEvent: (MessageScreenEvent) -> Unit
) {
    val photopickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {
            onEvent(MessageScreenEvent.SelectPhoto(it))
        }
    )




    if (state.receiver != null) {
        sheets(
            task = state.bottomSheetTask,
            show = state.showBottomSheet,
            name = state.receiver.name,
            onConfirmClick = {
                onEvent(MessageScreenEvent.onBottomSheetStateChange)
                onEvent(MessageScreenEvent.onConfirmSheetEvent(state.bottomSheetTask))
                             },
            onCancelCLick = {
                onEvent(MessageScreenEvent.onBottomSheetStateChange)
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 50.dp),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            chatBar(
                text = state.message,
                onTextChange = {
                    onEvent(MessageScreenEvent.onMessageStateChange(it))
                },
                attachment = {
                    photopickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                send = {
                    onEvent(MessageScreenEvent.sendMessage)
                },
                images = state.sendPhoto
            )
        },
        topBar = {
            MessageTopBar(
                user = state.receiver,
                searchQuery = state.searchQuery,
                expanded = state.expandMenuBar,
                onSearchQueryChange = {onEvent(MessageScreenEvent.onSearchQueryChange(it))},
                showSearchBar = state.showSearchBar,
                onMenuClick = {onEvent(MessageScreenEvent.onMenuStateChange) },
                onBackClick = {onEvent(MessageScreenEvent.onBackClick)},
                showSearchChange = {onEvent(MessageScreenEvent.onShowSearchBarChange)},
                onMenuItemClick = { menuItems ->
                    when(menuItems){
                        archive -> {
                            onEvent(MessageScreenEvent.onBottomSheetStateChange)
                            onEvent(MessageScreenEvent.onTaskChange("Archive"))
                        }
                        block -> {
                            onEvent(MessageScreenEvent.onBottomSheetStateChange)
                            onEvent(MessageScreenEvent.onTaskChange("Block"))
                        }
                        clear -> {
                            onEvent(MessageScreenEvent.onBottomSheetStateChange)
                            onEvent(MessageScreenEvent.onTaskChange("Clear"))
                        }
                        contact -> {onEvent(MessageScreenEvent.onContactClick(state.receiver!!))}
                        search -> {onEvent(MessageScreenEvent.onShowSearchBarChange)}
                    }
                }
            )
        }
    ) {paddingValues->
        LazyColumn(Modifier.padding(paddingValues),
            reverseLayout = true) {
            items(6) {
                if (state.messages.isEmpty()) {
                    val alignment = if (it % 2 == 0) Alignment.CenterEnd else Alignment.CenterStart
                    val padding =
                        if ((it % 2 == 0)) PaddingValues(start = 50.dp) else PaddingValues(end = 50.dp)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        MessageCard2(
                            modifier = Modifier
                                .padding(padding)
                                .align(alignment),
                        )
                    }
                }
            }
            state.dates.forEach { section ->
                ;
                stickyHeader {
                    Text(
                        section,
                        Modifier.fillMaxWidth().background(Color.LightGray).padding(8.dp)
                    )
                }
                items(state.messages[section]!!) {
                    val alignment =
                        if (it.sentby == state.currentUser) Alignment.CenterEnd else Alignment.CenterStart
                    val padding =
                        if ((it.sentby == state.currentUser)) PaddingValues(start = 50.dp) else PaddingValues(
                            end = 50.dp
                        )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        MessageBox(
                            isSender = it.sentby == state.currentUser,
                            message = it.message,
                            time = it.timeline,
                            modifier = Modifier
                                .padding(padding)
                                .align(alignment),
                            images = it.images
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MessageTopBar(
    searchQuery: String,
    expanded: Boolean,
    user: Users?,
    onSearchQueryChange: (String) -> Unit,
    showSearchBar: Boolean,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit,
    showSearchChange: () -> Unit,
    onMenuItemClick: (menuItems) -> Unit
){


    TopAppBar(
        title = { Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (user != null){
                if (showSearchBar) {
                    chatSearch(
                        text = searchQuery,
                        valueChange = onSearchQueryChange,
                        onCancelClick = showSearchChange
                    )
                } else {
                    AsyncImage(
                        model = user.imageUrl,
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .border(Dp.Hairline, MaterialTheme.colorScheme.onBackground)
                            .clip(CircleShape)
                    )
                    Column {
                        Row {
                            Text(
                                text = user.name,
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
            } else{
                Box(modifier = Modifier.fillMaxWidth().shimmerLoadingAnimation())
            }
        } },
        actions = {

            IconButton(onClick = onMenuClick) {
                Icon(ImageVector.Companion.vectorResource(id = R.drawable.baseline_more_horiz_24),
                    contentDescription = "More",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
            menu(
                expanded = expanded,
                onDismissRequest = onMenuClick,
                onmenuItems = onMenuItemClick
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                    contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun sheets(
    task: String,
    show: Boolean,
    name: String,
    onConfirmClick: () -> Unit,
    onCancelCLick: () -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    if (show) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onCancelCLick,
            containerColor =MaterialTheme.colorScheme.background
        ) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .border(2.dp, Color.Black)
                )
                Text(
                    text = "Are you sure you want to ${task.lowercase()} your chat with $name?",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                filledButton(
                    onButtonClick = onConfirmClick,
                    text = "Yes, ${task.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }} Chat",
                    color = Color.White,
                    buttonColor = Color.Red
                )
                outlinedButton(
                    onButtonClick = onCancelCLick,
                    text = "No, Cancel",
                    color = MaterialTheme.colorScheme.primary,
                    buttonColor = Color.Black
                )
            }
        }
    }
}