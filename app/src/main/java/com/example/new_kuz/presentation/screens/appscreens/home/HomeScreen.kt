package com.example.new_kuz.presentation.screens.appscreens.home

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.new_kuz.R
import com.example.new_kuz.domain.modules.inAppNav
import com.example.new_kuz.presentation.events.HomeScreenEvent
import com.example.new_kuz.presentation.screens.appscreens.components.connectItems
import com.example.new_kuz.presentation.screens.appscreens.components.roundedSearchBar
import com.example.new_kuz.presentation.states.HomeScreenState
import com.example.new_kuz.presentation.viewmodels.HomeScreenViewModel
import com.example.new_kuz.util.RequestState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeNavigation(navController: NavController) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value



    HomeUi(
        state = state,
        requestState = viewModel.requestState,
        onEvent = {homeScreenEvent ->
            when (homeScreenEvent) {
                is HomeScreenEvent.onChatClick -> {
                    navController.navigate(homeScreenEvent.contact)
                }
                else -> viewModel.onEvents(homeScreenEvent)
            }
        },
        profileClick = {
            navController.navigate(inAppNav.settings.route)
        }
    )
}

@Composable
private fun HomeUi(
    state: HomeScreenState,
    requestState: SharedFlow<RequestState<String>>,
    onEvent: (HomeScreenEvent) -> Unit,
    profileClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        requestState.collectLatest { event ->
            when (event) {
                RequestState.Idle -> {}
                RequestState.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                }

                is RequestState.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                is RequestState.Success -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                search = state.query,
                available = state.available,
                onSearchChange = { onEvent(HomeScreenEvent.onQueryChange(it)) },
                filter = {},
                onAvailableChange = { onEvent(HomeScreenEvent.onAvailableChange(it)) },
                imageUrl = state.image,
                text = if (state.available) "Available" else "Not Available",
                name = state.userName,
                ProfileClick = {
                    profileClick()
                }
            )
        }
    ) {
        Column(
            Modifier.padding(it)
        ) {
            LazyColumn {
                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            text = "Members",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                        )
                        Box(modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                            .clip(CircleShape)
                            .size(40.dp)
                        )
                        {
                            Text(
                                text = "${state.contacts.size}",
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                                )
                        }
                    }



                }

                items(state.contacts) { user ->

                    Column {
                        connectItems(
                            image = user.imageUrl,
                            showActive =
                            if (
                                user.connectedUsers.contains(state.users.uid) &&
                                state.connected.contains(user.uid)
                                ) true else false,
                            gender = user.gender,
                            connected = if (
                                !user.connectedUsers.contains(state.users.uid) &&
                                state.connected.contains(user.uid)
                            ) true else false,
                            name = user.name,
                            onChatClick = { onEvent(HomeScreenEvent.onChatClick(user)) },
                            onConnectClick = {
                                onEvent(HomeScreenEvent.onConnectClick(user))
                            }
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    imageUrl: Bitmap?,
    search: String,
    onSearchChange: (String) -> Unit,
    filter: () -> Unit,
    name: String,
    available: Boolean,
    onAvailableChange: (Boolean) -> Unit,
    ProfileClick: () -> Unit,
    text: String
){
    LargeTopAppBar(
        title = {
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
            }
        },
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clickable {
                        ProfileClick()
                    }
            ) {
                Box {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(Dp.Hairline, MaterialTheme.colorScheme.primary, CircleShape)
                            .align(Alignment.Center)
                    )
                    Box(modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.Green)
                        .align(Alignment.TopEnd)
                    )
                }

                Text(text = name)
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {

                Text(text = text)
                Switch(checked = available, onCheckedChange = onAvailableChange)
            }
        }
    )
}