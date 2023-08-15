package com.luqman.imagemachine.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luqman.imagemachine.R
import com.luqman.imagemachine.core.helper.DateHelper.toDate
import com.luqman.imagemachine.uikit.component.DatePickerComponent

@Composable
fun DetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    var isDateDialogShow by remember {
        mutableStateOf(false)
    }

    Surface(modifier = modifier) {
        Scaffold(
            topBar = {
                TopBar {
                    // on navigate back
                    navController.navigateUp()
                }
            },
            bottomBar = {
                Button(onClick = {}) {
                    Text(text = stringResource(id = R.string.button_save))
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                InputField(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    value = viewModel.machine.name,
                    label = stringResource(id = R.string.machine_name_input_label),
                    placeholder = stringResource(id = R.string.machine_name_input_placeholder),
                    onChange = {
                        viewModel.machine.name = it
                    }
                 )

                InputField(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    value = viewModel.machine.type,
                    label = stringResource(id = R.string.machine_type_input_label),
                    placeholder = stringResource(id = R.string.machine_type_input_placeholder),
                    onChange = {
                        viewModel.machine.type = it
                    }
                )

                InputField(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    value = viewModel.machine.code,
                    label = stringResource(id = R.string.machine_code_input_label),
                    placeholder = stringResource(id = R.string.machine_code_input_placeholder),
                    onChange = {
                        viewModel.machine.code = it
                    }
                )

                InputField(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    value = viewModel.machine.lastMaintain.toDate(),
                    label = stringResource(id = R.string.machine_last_maintain_input_label),
                    placeholder = stringResource(id = R.string.machine_last_maintain_input_placeholder),
                    onChange = {}
                )

                if (isDateDialogShow) {
                    DatePickerComponent(selectedDate = viewModel.machine.lastMaintain, onDismiss = { selectedDate ->
                        viewModel.machine.lastMaintain = (selectedDate ?: 0)
                        isDateDialogShow = false
                    })
                }
            }
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    label: String,
    onChange : (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onChange,
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        modifier = modifier,
        title = {
            Text(text = stringResource(id = R.string.detail_page_title))
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate back")
            }
        }
    )
}