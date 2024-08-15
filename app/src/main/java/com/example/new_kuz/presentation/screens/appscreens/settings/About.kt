package com.example.new_kuz.presentation.screens.appscreens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.new_kuz.domain.modules.label
import com.example.new_kuz.presentation.screens.auth.components.Logo

@Composable
fun AboutUiNavigation(navController: NavController){
    AboutUi(
        version = "1.0",
        onBackPressed = { navController.navigateUp() },
        onHelpClick = { /*TODO*/ },
        onTermsClick = {}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutUi(
    version: String,
    onBackPressed: () -> Unit,
    onHelpClick: () -> Unit,
    onTermsClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "About KUZ") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "back",
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { onBackPressed() },
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Logo(color = MaterialTheme.colorScheme.primary, fontSize = 90)
                    Text(text = "Version $version")
                }
            }
            item {
                Body()
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
            item {
                Column {
                    HelpAndTermsButtons(text = "Terms Of Use", onTextClick = onTermsClick)
                    HelpAndTermsButtons(text = "Help Centre", onTextClick = onHelpClick)
                }
            }
        }
    }
}



@Composable
private fun Body(
    modifier: Modifier = Modifier,
    about: String = label.details
) {
    
    Card(
        Modifier
            .fillMaxWidth()
            .heightIn(120.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.background.copy(8f)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Text(
            text = about + about + about,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        )
    }
}

@Composable
private fun HelpAndTermsButtons(
    text: String,
    onTextClick: () -> Unit
){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.background.copy(8f)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            4.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(onClick = onTextClick) {
                Text(text = text, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}