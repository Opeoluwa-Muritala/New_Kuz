package com.example.new_kuz.presentation.screens.appscreens.chat.messages

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.new_kuz.util.shimmerLoadingAnimation

@Composable
fun MessageBox(
    modifier: Modifier = Modifier,
    isSender: Boolean,
    message: String,
    time: String,
    images: List<String> = emptyList()
) {
    val color = if (isSender) MaterialTheme.colorScheme.onBackground.copy(8f)
    else MaterialTheme.colorScheme.primary.copy(9f)


   MessageCard(
       modifier = modifier,
       message = message,
       time = time,
       color = color,
       images = images
   )
}


@Composable
fun MessageCard(
    modifier: Modifier = Modifier,
    message: String,
    time: String,
    color: Color,
    images: List<String>
) {

    Column(
        modifier
            .wrapContentSize(unbounded = false)
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
            .padding(5.dp)
            .background(color, RoundedCornerShape(30))
    ){
        if (images.isNotEmpty()) {
            images.forEach {
                AsyncImage(
                    model = it,
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .clip(RoundedCornerShape(30))
                        .border(Dp.Hairline, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Fit
                )
            }
        }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(IntrinsicSize.Max),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(30)
    ) {
        Row {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onTertiary,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }


        Text(
            text = time,
            color = MaterialTheme.colorScheme.onTertiary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            style = LocalTextStyle.current.copy(
                fontSize = 10.sp
            )
        )
    }
    }
}

@Composable
fun MessageCard2(
    modifier: Modifier = Modifier,
) {


    Card(
        modifier
            .fillMaxWidth(0.7f)
            .shimmerLoadingAnimation()
            .padding(5.dp),
        shape = RoundedCornerShape(30)
    ) {
    }
}
