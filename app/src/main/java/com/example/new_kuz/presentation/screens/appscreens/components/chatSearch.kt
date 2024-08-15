package com.example.new_kuz.presentation.screens.appscreens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun chatSearch(
    text: String,
    valueChange: (String) -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        roundedSearchBar(
            text = text,
            onTextChange = valueChange,
        )
        Text(
            text = "Cancel",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp,
            modifier = Modifier.clickable { onCancelClick() }
        )
    }

}

@Composable
fun roundedSearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        textStyle = LocalTextStyle.current.copy(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        ),
         placeholder =  {
           Row(
               Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.Center,
               verticalAlignment = Alignment.CenterVertically
           ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
                Text("Search")
            }
        },
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(20.dp)
    )
}