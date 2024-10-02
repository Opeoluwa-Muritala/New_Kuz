package com.example.new_kuz.presentation.screens.appscreens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.new_kuz.R
import com.example.new_kuz.presentation.screens.auth.components.isConnected

@Composable
fun ConnectItems(
    modifier: Modifier = Modifier,
    image: String?,
    gender: String,
    showActive: Boolean,
    showConnect: Boolean = false,
    requestState: Boolean = true,
    connected: Boolean,
    name: String,
    onChatClick : () -> Unit,
    onConnectClick: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
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
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(Dp.Hairline, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Column {
                Row {
                    Text(
                        text = name,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                        modifier = Modifier.width(150.dp),
                        maxLines = 1
                    )
                    if (showConnect) {
                        isConnected(connected = false)
                    }
                    if (!showConnect){
                        isConnected(connected = true)
                    }
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
        if (showConnect) {
            if (!requestState) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Request Sent",
                        tint = Green
                    )
                    Text(text = "Sent", color = Green)
                }
            } else {
                Connect(
                    onConnectClick = {})
                }
            // Request State is true when a request is sent for connection.
        } // Show Connect is to decide whether to show the end part of the row
    }

    HorizontalDivider(
        thickness = Dp.Hairline,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
}

@Composable
private fun Connect(
    onConnectClick: () -> Unit){

        TextButton(onClick = { onConnectClick() }) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Connect",
                tint = MaterialTheme.colorScheme.primary
            )
            Text(text = "Connect", color = MaterialTheme.colorScheme.primary)

    }
}

@Preview(showBackground = true)
@Composable
fun ConnectMembersPreview(){
    ConnectItems(
        image = "",
        gender = "Male",
        showActive = false,
        connected = false,
        name = "Muritala Opeoluwa",
        onChatClick = {  }) {

    }
}