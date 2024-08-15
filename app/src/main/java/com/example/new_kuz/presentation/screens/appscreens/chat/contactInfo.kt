package com.example.new_kuz.presentation.screens.appscreens.chat

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.new_kuz.R
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.domain.modules.label
import com.example.new_kuz.presentation.events.ContactCardEvent
import com.example.new_kuz.presentation.states.ContactCardState
import com.example.new_kuz.presentation.viewmodels.SignInViewModel
import com.example.new_kuz.navigation.User

@Composable
fun ContactNavigation(navController: NavController, user: Users){
    //Create ViewModel And Add Functionality
    contactCard(
        state = ContactCardState(),
        user = user,
        onEvent = { contactCardEvent ->
            when(contactCardEvent) {
                ContactCardEvent.onBackClick -> {
                    navController.navigateUp()
                }
                else -> contactCardEvent
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun contactCard(
    user: Users? = null,
    state: ContactCardState,
    onEvent: (ContactCardEvent) -> Unit
) {
    val viewModel: SignInViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            AppBar(onBackClick = { onEvent(ContactCardEvent.onBackClick) })
        },
        containerColor = MaterialTheme.colorScheme.background,
        ) {
        LazyColumn {
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Column(
                    Modifier
                        .padding(it)
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    UserDetails(
                        image = user?.imageUrl ?: "",
                        name = user?.name ?: "User",
                        lastSeen = state.lastSeen
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Gender(gender = user?.gender ?: "e.g Male")
                        Spacer(modifier = Modifier.height(2.dp))
                        Biography(bio = user?.bio ?: "")
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        ProfileButtonCards(
                            onCardClick = {onEvent(ContactCardEvent.blockContact)}
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        ProfileButtonCards(
                            text = "Archive Chat",
                            onCardClick = {onEvent(ContactCardEvent.archiveContact)}
                        )
                    }

                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    onBackClick: () -> Unit
){
    TopAppBar(
        title = {
            Text(text = "Member Profile", color = MaterialTheme.colorScheme.onSecondary)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}


@Composable
private fun UserDetails(
    image: String,
    name: String ,
    lastSeen: String = "last seen 3 minutes ago",
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,) {
        AsyncImage(
            model = image,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Text(
            text = lastSeen,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )
        Card(
            Modifier
                .width(IntrinsicSize.Max)
                .padding(10.dp)
                .height(90.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(20.dp)
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.Green)
                        .size(90.dp, 40.dp)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Connected",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Text(
                    text = "Connected on 5th Dec, 2023",
                    color = Color.Black
                )
            }

        }
    }

}


@Composable
private fun Gender(
    gender: String = "Male"
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(text = "Gender", fontSize = 10.sp, fontWeight = FontWeight.Normal)
            Text(text = gender, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun Biography(
    bio: String = label.details
){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(text = "Bio", fontSize = 10.sp, fontWeight = FontWeight.Normal)
            Text(
                text = bio,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
private fun ProfileButtonCards (
    text: String = "Block Contact",
    onCardClick: () -> Unit
){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .height(40.dp)
            .clickable {
                onCardClick()
            }
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
    }
}