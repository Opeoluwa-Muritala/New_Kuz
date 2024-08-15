package com.example.new_kuz.presentation.screens.auth.signup.userdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.domain.modules.label
import com.example.new_kuz.presentation.events.SignUpEvents
import com.example.new_kuz.presentation.states.SignUpState
import com.example.new_kuz.util.SnackBarEvent
import com.example.new_kuz.presentation.screens.auth.components.phoneField
import com.example.new_kuz.presentation.screens.components.appField
import com.example.new_kuz.presentation.screens.components.filledButton
import kotlinx.coroutines.flow.SharedFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterPhone(
    navController: NavController,
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            EnterNumberTopBar(
                navController = navController,
                header = "Enter Your Phone Number",
                description = label.details
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 10.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = state.isexpanded,
                onExpandedChange = { onEvent(SignUpEvents.onExpandedChange(it)) },
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Text(text = "Select Country", color = MaterialTheme.colorScheme.onSecondary)
                    appField(
                        value = state.country,
                        onValueChange = { },
                        error = false,
                        placeholderText = "country",
                        readOnly = true,
                        modifier = Modifier.menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isexpanded)
                        },
                        leadingIcon = {
                            Text(
                                text = state.selectedCountry.countryPhoneCode,
                                fontStyle = LocalTextStyle.current.fontStyle,
                                modifier = Modifier.padding(start = 6.dp,top = 15.dp),
                            )
                        },
                    )
                }
                ExposedDropdownMenu(
                    expanded = state.isexpanded,
                    onDismissRequest = { SignUpEvents.onExpandedChange(isexpanded = state.isexpanded) },
                    modifier = Modifier
                ) {
                    state.countries.forEachIndexed { index, countryData ->
                        DropdownMenuItem(
                            text = { Text(text = countryData.cNames, color = MaterialTheme.colorScheme.onSecondary) },
                            onClick = {
                                onEvent(SignUpEvents.onSelectedCountry(countryData))
                                onEvent(SignUpEvents.onExpandedChange(isexpanded = state.isexpanded))
                            }
                        )
                    }
                }
            }

            Column {
                Text(text = "Phone Number", color = MaterialTheme.colorScheme.onSecondary)
                phoneField(
                    text = state.phonenumber,
                    textChange = { onEvent(SignUpEvents.onPhoneChange(it)) },
                    selectedCountry = state.selectedCountry
                )

            }
            Spacer(modifier = Modifier.height(20.dp))
            filledButton(
                onButtonClick = {
                    onEvent(SignUpEvents.onEnterPhone)
                    navController.navigate(authRoute.personalInfo.route)
                },
                text = "Continue",
                color = Color.White,
                buttonColor = MaterialTheme.colorScheme.primary,
                enabled = state.phonenumber.isNotBlank()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnterNumberTopBar(
    navController: NavController,
    header: String,
    description: String
) {
    Column(
        Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
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