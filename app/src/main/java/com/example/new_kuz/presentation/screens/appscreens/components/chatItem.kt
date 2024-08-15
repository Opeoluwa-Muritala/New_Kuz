package com.example.new_kuz.presentation.screens.appscreens.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.new_kuz.presentation.screens.auth.components.isActive

@Composable
fun chatItem(
    modifier: Modifier = Modifier,
    image: String,
    isActive: Boolean,
    time: String,
    chats: Int?,
    lastMessage: String?,
    name: String,
    onChatClick : () -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(2.dp)
            .clickable {
                onChatClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AsyncImage(
                model = image,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Column {
                Row {
                    Text(text = name, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(5.dp))
                    isActive(active = isActive)
                }
                lastMessage?.let { Text(text = it, fontWeight = FontWeight.Light,) }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(5.dp)
        ) {
            Text(text = time)
            Spacer(modifier = Modifier.height(10.dp))
            chats?.let {
                Box(
                Modifier
                    .clip(CircleShape)
                    .size(20.dp)
                    .background(MaterialTheme.colorScheme.primary)) {
                Text(text = it.toString(), color = Color.White, modifier = Modifier.align(
                    Alignment.Center))
                }
            }
        }

    }
}