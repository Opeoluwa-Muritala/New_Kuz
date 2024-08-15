package com.example.new_kuz.presentation.screens.appscreens.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.new_kuz.R

@Composable
fun chatBar(
    text: String,
    onTextChange: (String) -> Unit,
    attachment: () -> Unit,
    send: () -> Unit,
    images: List<Uri?>?
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) {

        Column {
            Row(Modifier.verticalScroll(rememberScrollState())) {
                images?.forEach {
                    AsyncImage(
                        model = it,
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(5.dp)
                            .clip(RoundedCornerShape(10)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Row {
                IconButton(onClick = { attachment() }) {
                    Icon(
                        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.baseline_attach_file_24),
                        "Attachment",
                        tint = Color.Gray
                    )
                }
                OutlinedTextField(
                    value = text,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    placeholder = {
                        Text(text = "Message")
                    },
                    shape = RoundedCornerShape(50)
                )

                IconButton(onClick = { send() }) {
                    Icon(
                        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.baseline_send_24),
                        "Attachment",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}