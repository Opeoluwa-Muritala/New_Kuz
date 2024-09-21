package com.example.new_kuz.presentation.screens.appscreens.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.new_kuz.R
import com.example.new_kuz.domain.modules.inAppNav
import com.example.new_kuz.util.changeMillisToDateString
import com.example.new_kuz.presentation.events.SettingsEvent
import com.example.new_kuz.presentation.states.SettingsState
import com.example.new_kuz.presentation.viewmodels.SettingsViewModel
import com.example.new_kuz.presentation.screens.appscreens.components.AppDatePicker
import com.example.new_kuz.presentation.screens.components.LabelledTextField
import com.example.new_kuz.presentation.screens.components.appField
import com.example.new_kuz.util.RequestState
import java.time.Instant


@Composable
fun SettingsNavigation(navController: NavController){
    val viewModel: SettingsViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value
    val data = viewModel.userdata.collectAsState(initial = RequestState.Idle)

    val photopickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            viewModel.onEvents(SettingsEvent.onSelectImage(it))
        }
    )


    settingsUi(
        state = state,
        onEvent = { settingsEvent ->
            when (settingsEvent) {
                SettingsEvent.onAboutClick -> {
                    navController.navigate(inAppNav.about.route)
                }
                is SettingsEvent.onChangeProfilePicture -> {
                    photopickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                    viewModel.onEvents(settingsEvent)
                }
                else -> viewModel.onEvents(settingsEvent)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun settingsUi(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit
) {

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = Instant.now().toEpochMilli()
        )


        AppDatePicker(
            state = datePickerState,
            isOpen = state.isopen,
            onDismissRequest = {onEvent(SettingsEvent.onIsOpenChange)},
            onConfirmButtonClick = {
                onEvent(SettingsEvent.onSelectDate(datePickerState.selectedDateMillis.changeMillisToDateString()))
                onEvent(SettingsEvent.onIsOpenChange)
            }
        )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Settings") })
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 20.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        ProfileImage(
                            image = state.image,
                            changeProfilePicture = { onEvent(SettingsEvent.onChangeProfilePicture) }
                        )
                        LabelledTextField(
                            fieldText = state.name,
                            onFieldTextChange = { onEvent(SettingsEvent.onNameChange(it)) }
                        )
                        SelectGender(
                            selectedGender = state.selectedGender,
                            genders = state.genders,
                            isExpanded = state.isexpanded,
                            onExpandedChange = { onEvent(SettingsEvent.onIsExpandedChange) },
                            onItemClick = { onEvent(SettingsEvent.onSelectGender(it)) ; onEvent(
                                SettingsEvent.onIsExpandedChange)}
                        )
                        SelectDateOfBirth(
                            selectedDateOfBirth = state.date_of_birth,
                            onItemClick = {onEvent(SettingsEvent.onIsOpenChange)}
                        )
                        LabelledTextField(
                            title = "Bio",
                            fieldText = state.bio,
                            onFieldTextChange = { onEvent(SettingsEvent.onBioChange(it)) }
                        )

                        ElevatedButton(onClick = { onEvent(SettingsEvent.saveChanges) }) {
                            Text(text = "Save Changes")
                        }
                    }

                }
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
            item {
                AboutKuzButton(onClickCard = {onEvent(SettingsEvent.onAboutClick)})
            }
        }
    }
}

@Composable
private fun AboutKuzButton(
    modifier: Modifier = Modifier,
    onClickCard: () -> Unit
) { 
    Card(
        Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clickable {
                onClickCard()
            },
        shape = RoundedCornerShape(30)
    ) {
        Row(
            modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "About Kuz", color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = "About")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Stable
private fun SelectGender(
    selectedGender: String = "Select A Gender",
    genders: List<String>,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onItemClick: (String) -> Unit
) {

    Column {

        Text("Gender")
        ExposedDropdownMenuBox(
            modifier = Modifier.padding(start = 5.dp),
            expanded = isExpanded,
            onExpandedChange = onExpandedChange,
        ) {
            appField(
                value = selectedGender,
                onValueChange = { },
                error = false,
                placeholderText = "Select Gender",
                readOnly = true,
                modifier = Modifier.menuAnchor(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { onExpandedChange(true) },
                modifier = Modifier
            ) {
                genders.forEachIndexed { index, gender ->
                    DropdownMenuItem(
                        text = { Text(text = gender, color = MaterialTheme.colorScheme.onSecondary) },
                        onClick = { onItemClick(gender) }
                    )
                }
            }
        }
    }
}


@Composable
private fun SelectDateOfBirth(
    modifier: Modifier = Modifier,
    selectedDateOfBirth: String,
    onItemClick: () -> Unit
) {
    Column {
        Text("Date Of Birth")
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
                        contentDescription = "Pick Date Of Birth"
                    )
                }
            }
        )
    }
}


@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    image: Uri?,
    changeProfilePicture: () -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(20.dp),
    ) {
        Row {
            AsyncImage(
                model = image,
                modifier = Modifier
                    .size(60.dp)
                    .border(Dp.Hairline, MaterialTheme.colorScheme.onBackground, CircleShape)
                    .clip(CircleShape),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop
            )
            TextButton(onClick = changeProfilePicture) {
                Text(
                    "Change Profile Picture",
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Gray,
            modifier = Modifier
                .padding(vertical = 9.dp)
                .fillMaxWidth()
        )
    }
}