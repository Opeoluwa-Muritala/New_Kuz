package com.example.new_kuz.presentation.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.new_kuz.R

@Composable
fun GoogleButton(
    onSignInClick: () -> Unit,
    text: String,
    enabled: Boolean
) {

    OutlinedButton(
        onClick = onSignInClick,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Sign in button",
                modifier = Modifier
                    .size(20.dp),
            )

            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Sign $text With Google",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }

}

@Composable
fun filledButton(
    onButtonClick: () -> Unit,
    text: String,
    buttonColor: Color,
    enabled: Boolean = true  ,
    color: Color = if(enabled )MaterialTheme.colorScheme.primary else Color.Black,

) {
    Button(
        onClick = { onButtonClick() },
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = enabled
    ) {
        Text(text = text, color = color)
    }
}

@Composable
fun outlinedButton(
    onButtonClick: () -> Unit,
    text: String,
    color: Color,
    buttonColor: Color
) {
    OutlinedButton(
        onClick = { onButtonClick() },
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(50.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, buttonColor)
    ) {
        Text(text = text, color = color)
    }
}