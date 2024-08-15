package com.example.new_kuz.presentation.screens.auth.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.new_kuz.presentation.screens.components.OtpTextField
import com.example.new_kuz.presentation.screens.components.filledButton
import com.samirahmed.otptextfield.ui.OtpCellProperties

@Composable
fun verifyScreen(
    text: String,
    valueChange: (String) -> Unit,
    header: String,
    buttonClick:() -> Unit,
    description: String,
    navController: NavController,
    @DrawableRes image: Int?
) {
    var otpError by remember {
        mutableStateOf<String?>(null)
    }
    otpError = when {
        text.isBlank() -> "Please enter otp"
        text.length < 4 -> "Please enter complete otp"
        else -> null
    }

    Scaffold(
        topBar = {
            VertificationTopBar(
                navController = navController,
                header = header,
                image = image,
                description = description
            )

        },
        containerColor =  MaterialTheme.colorScheme.background
    ) { paddingvalues ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(paddingvalues)
                .padding(horizontal = 10.dp)
        ) {

            otpField(
                otpTextFieldValue = text,
                otpTextFieldValueChange = valueChange,
                error = otpError == null
            )
            Spacer(modifier = Modifier.height(20.dp))
            filledButton(
                onButtonClick = buttonClick,
                text = "Continue",
                color = Color.White,
                buttonColor = MaterialTheme.colorScheme.primary,
                enabled = otpError == null
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VertificationTopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    header: String,
    image: Int?,
    description: String
) {
    Column {
        TopAppBar(
            title = {
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "back",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { navController.navigateUp() },
                )
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = header,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 30.sp
            )
            image?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = header,
                    modifier = Modifier
                        .size(100.dp)
                )
            }
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
private fun otpField(
    otpTextFieldValue: String,
    otpTextFieldValueChange: (String) -> Unit,
    error: Boolean
) {

    OtpTextField(
        modifier = Modifier.fillMaxWidth(),
        otpText = otpTextFieldValue,
        isHasError = error,
        otpCellProperties = OtpCellProperties(
            otpLength = 4,
            otpCellSize = 50.dp,
            otpDistanceBetweenCells = 10.dp,
            otpTextStyle = LocalTextStyle.current.copy(
                fontSize = 30.sp
            ),
            borderWidth = 1.dp,
            borderRound = 8.dp,
            cursorWidth = 2.dp,
            cursorColor = Color.Black,
            hint = "0"
        ),
        onValueChange = otpTextFieldValueChange,
        onOtpFinished = {

        }
    )
}