package com.example.new_kuz.presentation.screens.auth.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.domain.modules.label
import com.example.new_kuz.presentation.screens.components.filledButton
import com.example.new_kuz.presentation.screens.auth.components.headerDetailText
import com.example.new_kuz.presentation.screens.components.outlinedButton
import com.example.new_kuz.R
import com.example.new_kuz.navigation.Graph


@Composable
fun WelcomeNavigation(navController: NavController){
    WelcomeUi(
        signUpClick = { navController.navigate(Graph.SIGN_UP) },
        signInClick = { navController.navigate(authRoute.signIn.route) }
    )
}
@Composable
private fun WelcomeUi(
    signUpClick: () -> Unit,
    signInClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(.8f)
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            ) {

                Column(
                    Modifier.fillMaxSize().padding(top = 150.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.welcometwo),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .sizeIn(minWidth = 200.dp, minHeight = 200.dp, maxWidth = 500.dp, 500.dp)
                            .graphicsLayer(
                                rotationZ = -8F
                            )
                            .clip(RoundedCornerShape(20.dp))
                            .border(4.dp, Color.White, RoundedCornerShape(20.dp))

                    )
                }
                Column(
                    Modifier.fillMaxSize().padding(10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.welcomeone),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .sizeIn(
                                minWidth = 200.dp,
                                minHeight = 200.dp,
                                maxWidth = 500.dp,
                                maxHeight = 500.dp
                            )
                            .graphicsLayer(rotationZ = 8f)
                            .clip(RoundedCornerShape(20.dp))
                            .border(4.dp, Color.White, RoundedCornerShape(20.dp))
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.welcomeicon),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(300.dp)
                        .sizeIn(
                            minWidth = 200.dp,
                            minHeight = 200.dp,
                            maxWidth = 600.dp,
                            600.dp
                        )
                        .padding(top = 90.dp, end = 100.dp, bottom = 80.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            headerDetailText(
                text = "Welcome to Kuz",
                details = "Lorem Ipsum dolor sit amet consectur. " +
                        "Platae nisi volupat donec cras " +
                        "bibendum tellus commodo. " +
                        "Id vulutpate tortor faucibus " +
                        "lectus in facilisis vitae ",
                headerColor = Color.White,
                size = 40
            )
            Spacer(modifier = Modifier.height(40.dp))
            Column {
                filledButton(
                    onButtonClick = signInClick,
                    text = label.signin,
                    color = MaterialTheme.colorScheme.primary,
                    buttonColor = MaterialTheme.colorScheme.background
                )
                Spacer(modifier = Modifier.height(20.dp))
                outlinedButton(
                    onButtonClick = signUpClick,
                    text = label.signup,
                    color = MaterialTheme.colorScheme.background,
                    buttonColor = Color.White
                )

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
class ImageSizeAndPadding {
    val small: Dp = 300.dp
    val large: Dp = 500.dp
    val topPadding: List<Dp> = listOf(50.dp, 80.dp)
    val middlePadding: List<Dp> = listOf(50.dp, 80.dp)
    val endPadding: List<Dp> = listOf(50.dp, 80.dp)
}