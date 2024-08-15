package com.example.new_kuz.presentation.screens.appscreens.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.new_kuz.R
import com.example.new_kuz.presentation.screens.auth.components.isActive

@Composable
fun connectItems(
    modifier: Modifier = Modifier,
    image: String?,
    gender: String,
    showActive: Boolean,
    active: Boolean,
    name: String,
    onChatClick : () -> Unit,
    onConnectClick: () -> Unit
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
                    if (showActive) {
                        isActive(active = active)
                    }//Show is Active
                }
                Row {
                    Icon(
                        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.gender),
                        contentDescription = "Gender"
                    )
                    Text(text = gender)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (!showActive) {
            TextButton(onClick = onConnectClick) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Connect",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(text = "Connect", color = MaterialTheme.colorScheme.primary)
            }
        } else {
            if (!active) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Request Sent",
                        tint = Green
                    )
                    Text(text = "Sent", color = Green)
                }
            }
        }
    }
}