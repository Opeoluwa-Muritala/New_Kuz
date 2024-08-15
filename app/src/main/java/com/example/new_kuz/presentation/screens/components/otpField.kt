package com.example.new_kuz.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.samirahmed.otptextfield.theme.Error_Color
import com.samirahmed.otptextfield.theme.Focus_Text
import com.samirahmed.otptextfield.ui.OtpCellProperties
import com.samirahmed.otptextfield.ui.OtpCursor
import com.samirahmed.otptextfield.ui.OtpStatus
import com.samirahmed.otptextfield.ui.prepareCellWidthIfNoSpace


@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String = "0000",
    isHasError: Boolean = false,
    otpCellProperties: OtpCellProperties = OtpCellProperties(),
    onValueChange: (String) -> Unit = {},
    onOtpFinished: (OtpStatus) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val cellProperties = remember(otpCellProperties) {
        prepareCellWidthIfNoSpace(
            screenWidth = screenWidth,
            cellProperties = otpCellProperties
        )
    }

    CompositionLocalProvider(LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr) {
        BasicTextField(
            value = otpText,
            onValueChange = {
                if (it.isDigitsOnly() && it.length < cellProperties.otpLength + 1) {
                    onValueChange(it)
                    if (it.length == cellProperties.otpLength) {
                        onOtpFinished(OtpStatus.Filled(it))
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            cursorBrush = SolidColor(Color.Transparent),
            decorationBox = {
                OtpDecorationBox(
                    modifier = modifier,
                    cellProperties = cellProperties,
                    otpText = otpText,
                    isHasError = isHasError,
                )
            }
        )
    }

}

@Composable
fun OtpDecorationBox(
    modifier: Modifier = Modifier,
    cellProperties: OtpCellProperties,
    otpText: String,
    isHasError: Boolean,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(cellProperties.otpDistanceBetweenCells)
        ) {
            repeat(cellProperties.otpLength) { index ->
                OtpCell(
                    cellProperties = cellProperties,
                    index = index,
                    text = otpText,
                    isHasError = isHasError
                )
            }
        }
    }
}


@Composable
fun OtpCell(
    cellProperties: OtpCellProperties,
    index: Int,
    text: String,
    isHasError: Boolean
) {
    val colors = MaterialTheme.colorScheme.primary
    val isFocusedOtpCell = index <= text.length
    val cursorPosition = index == text.length
    val focusBorderColor =
        remember(isHasError) { if (isHasError) Error_Color else  colors}
    val unFocusBorderColor =
        remember(isHasError) { if (isHasError) Error_Color else colors }
    val textColor =
        remember(isHasError) { if (isHasError) Error_Color else colors }
    val char = when {
        index == text.length -> ""
        index > text.length -> cellProperties.hint
        else -> text[index].toString()
    }
    Box(
        modifier = Modifier
            .height(30.dp)
            .width(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier,
            text = char,
            style = cellProperties.otpTextStyle,
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        HorizontalDivider(
            color = when {
                isFocusedOtpCell -> focusBorderColor
                else -> unFocusBorderColor
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
        if (cursorPosition) {
            OtpCursor(
                cellProperties = cellProperties
            )
        }
    }
}
