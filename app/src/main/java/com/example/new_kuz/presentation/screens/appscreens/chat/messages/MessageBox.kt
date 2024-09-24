package com.example.new_kuz.presentation.screens.appscreens.chat.messages

import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.asFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.new_kuz.util.shimmerLoadingAnimation
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

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
    images: List<String>,
) {

    Column(
        modifier
            .wrapContentSize(unbounded = false)
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
            .padding(5.dp)
            .background(color, RoundedCornerShape(10))
    ){
        if (images.isNotEmpty()) {
            images.forEach {
                Log.d("Image",it)
                ZoomableImage(model = it)
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
        shape = RoundedCornerShape(31)
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
    var visible by remember{ mutableStateOf(false) }
    val colorAlpha = animateFloatAsState(
        targetValue = if (visible) 0.8f else 0.3f,
        label = "Background Float," ,
        animationSpec = spring(
            stiffness = Spring.StiffnessMediumLow,
        )
    )
    val offsetAnim = animateIntOffsetAsState(
        targetValue =if (visible) IntOffset(x = 0, y = 0) else IntOffset(x = 0, y = 10),
        label = "Background Offset",
        animationSpec = spring(
            stiffness = Spring.StiffnessMediumLow,
        )
    )
    val shimmerColors = listOf(
        Color.White,
        Color.Gray
//        Color.White.copy(alpha = colorAlpha.asFloatState().floatValue),
//        Color.White.copy(alpha = colorAlpha.asFloatState().floatValue),
//        Color.White.copy(alpha = colorAlpha.asFloatState().floatValue),
//        Color.White.copy(alpha = colorAlpha.asFloatState().floatValue),
//        Color.White.copy(alpha = colorAlpha.asFloatState().floatValue),
        )


    LaunchedEffect(key1 = true) {
        while (true) {
            visible = true
            delay(1000)
            visible = false
            delay(1000)
        }
    }

    Box(
        modifier
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(30))
//            .shimmerLoadingAnimation()
//            .graphicsLayer {
//                this.alpha = colorAlpha.asFloatState().floatValue
//                this.ambientShadowColor = Color.Black.copy(colorAlpha.asFloatState().floatValue)
//            }
            .background(
                Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset(x = 100f, y = 0.0f),
                    end = Offset(x = 400f, y = 270f),
                )
            )
            .height(40.dp)

            .padding(5.dp),
    ){
        Box(modifier = Modifier
            .background(Color.Black, shape = RectangleShape)
            .fillMaxHeight()
            .offset { offsetAnim.value })
    }
}

@Composable
fun animateColorsSequence(number: Int): List<Color> {
    var color by remember { mutableStateOf(listOf<Color>()) }
    when(number){
        1 -> {
            color = listOf(Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.3f),)
        }
        2 -> {
            color = listOf(Color.White.copy(alpha = 0.3f),
                Color.White.copy(alpha = 0.5f),
                Color.White.copy(alpha = 1.0f),
                Color.White.copy(alpha = 1.0f),
                Color.White.copy(alpha = 0.3f),)
        }
    }
    return color
}

@Composable
fun ZoomableImage(model: Any, contentDescription: String? = null) {
    val angle by remember { mutableStateOf(0f) }
    var zoom by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.value
    val screenHeight = configuration.screenHeightDp.dp.value

    AsyncImage(
        model,
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .graphicsLayer(
                scaleX = zoom,
                scaleY = zoom,
                rotationZ = angle
            )
            .pointerInput(Unit) {
                detectTapGestures {click ->

                }
                detectTransformGestures(
                    onGesture = { _, pan, gestureZoom, _ ->
                        zoom = (zoom * gestureZoom).coerceIn(1F..4F)
                        if (zoom > 1) {
                            val x = (pan.x * zoom)
                            val y = (pan.y * zoom)
                            val angleRad = angle * PI / 180.0

                            offsetX =
                                (offsetX + (x * cos(angleRad) - y * sin(angleRad)).toFloat()).coerceIn(
                                    -(screenWidth * zoom)..(screenWidth * zoom)
                                )
                            offsetY =
                                (offsetY + (x * sin(angleRad) + y * cos(angleRad)).toFloat()).coerceIn(
                                    -(screenHeight * zoom)..(screenHeight * zoom)
                                )
                        } else {
                            offsetX = 0F
                            offsetY = 0F
                        }
                    }
                )
            }
            .size(120.dp)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .clip(RoundedCornerShape(10))
            .border(Dp.Hairline, MaterialTheme.colorScheme.primary, RoundedCornerShape(10))
            .fillMaxSize()
    )
}