package com.example.new_kuz.presentation.screens.appscreens.components

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun menu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onmenuItems: (menuItems) -> Unit
) {
    val dropItems = listOf<menuItems>(
        menuItems.search,
        menuItems.contact,
        menuItems.clear,
        menuItems.block,
        menuItems.archive
    )
    DropdownMenu(expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background
        )
    ) {

        dropItems.forEach {menuItem ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = menuItem.text,
                        color = menuItem.color ?: MaterialTheme.colorScheme.onSecondary
                    )
                },
                onClick = {
                    onmenuItems(menuItem)
                },
            )
        }


    }
}

sealed class menuItems(val text: String, val color: Color? = null){
    object search: menuItems("Search Chat", )
    object contact: menuItems("View Contact", )
    object clear: menuItems("Clear Chat",Color.Red)
    object block: menuItems("Block Member", Color.Red)
    object archive: menuItems("Archive Chat",Color.Red)
}