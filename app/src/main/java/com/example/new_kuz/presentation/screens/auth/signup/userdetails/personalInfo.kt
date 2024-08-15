package com.example.new_kuz.presentation.screens.auth.signup.userdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.domain.modules.label
import com.example.new_kuz.util.changeMillisToDateString
import com.example.new_kuz.presentation.events.SignUpEvents
import com.example.new_kuz.presentation.states.SignUpState
import com.example.new_kuz.presentation.viewmodels.SignUpViewModel
import com.example.new_kuz.presentation.screens.appscreens.components.AppDatePicker
import com.example.new_kuz.presentation.screens.components.appField
import com.example.new_kuz.presentation.screens.components.filledButton
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfo(
    navController: NavController,
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit
) {


        val datePickerState = rememberDatePickerState(
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= System.currentTimeMillis()
                }
            }
        )
        AppDatePicker(
            state = datePickerState,
            isOpen = state.isOpen,
            onDismissRequest = {onEvent(SignUpEvents.datePickerStateChange(state.isOpen))},
            onConfirmButtonClick = {
                onEvent(SignUpEvents.datePickerStateChange(state.isOpen))
                onEvent(SignUpEvents.onSelectedDateChange(datePickerState.selectedDateMillis.changeMillisToDateString()))
            }
        )



    Scaffold(
        topBar = {
            PersonalInfoTopBar(
                navController = navController,
                header = "Personal Info",
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

            appField(
                value = state.firstname,
                onValueChange = { onEvent(SignUpEvents.onFirstNameChange(it)) },
                label = "First Name",
                error = false,
                placeholderText = "Enter First Name"
            )
            appField(
                value = state.lastname,
                onValueChange = { onEvent(SignUpEvents.onLastNameChange(it)) },
                label = "Last Name",
                error = false,
                placeholderText = "Enter Last Name"
            )
            SelectDateOfBirth(
                selectedDateOfBirth = state.dateofbirth,
                onItemClick = {onEvent(SignUpEvents.datePickerStateChange(state.isOpen))}
            )
            Spacer(modifier = Modifier.height(20.dp))
            filledButton(
                onButtonClick = { navController.navigate(authRoute.createPassword.route) },
                text = "Continue",
                color = Color.White,
                buttonColor = MaterialTheme.colorScheme.primary,
                enabled = state.lastname != "" && state.firstname != "" && state.dateofbirth != ""
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonalInfoTopBar(
    modifier: Modifier = Modifier,
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

@Composable
private fun SelectDateOfBirth(
    modifier: Modifier = Modifier,
    selectedDateOfBirth: String,
    onItemClick: () -> Unit
) {
    Column {
        Text("Date Of Birth", color = MaterialTheme.colorScheme.onSecondary)
        appField(
            modifier = Modifier.padding(start = 5.dp),
            value = selectedDateOfBirth,
            onValueChange = { },
            placeholderText = "11/02/2999",
            trailingIcon = {
                IconButton(
                    onClick = onItemClick,
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Pick Date Of Birth",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        )
    }
}