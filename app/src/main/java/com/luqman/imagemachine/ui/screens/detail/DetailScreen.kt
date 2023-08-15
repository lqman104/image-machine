package com.luqman.imagemachine.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.luqman.imagemachine.R
import com.luqman.imagemachine.core.helper.DateHelper.toDate
import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.uikit.component.DatePickerComponent
import com.luqman.imagemachine.uikit.component.LoadingComponent

@Composable
fun DetailScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var errorMessage: String? by remember {
        mutableStateOf(null)
    }

    when (state.state) {
        is Resource.Loading -> LoadingComponent(Modifier.fillMaxSize())
        is Resource.Success -> navController.navigateUp()
        is Resource.Error -> {
            errorMessage = state.state?.error.toString()
        }

        else -> {}
    }

    DetailScreen(
        state = state,
        modifier = modifier,
        errorMessage = errorMessage,
        onNavigateBack = {
            navController.navigateUp()
        },
        onSave = {
            viewModel.save()
        }
    )
}

@Composable
fun DetailScreen(
    state: DetailPageState,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit
) {
    var isDateDialogShow by remember {
        mutableStateOf(false)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    if (errorMessage.isNullOrEmpty().not()) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(
                message = errorMessage.orEmpty()
            )
        }
    }

    Surface(modifier = modifier) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                TopBar {
                    // on navigate back
                    onNavigateBack()
                }
            },
            bottomBar = {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    onClick = onSave
                ) {
                    Text(text = stringResource(id = R.string.button_save))
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                InputField(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    value = state.data.name,
                    label = stringResource(id = R.string.machine_name_input_label),
                    placeholder = stringResource(id = R.string.machine_name_input_placeholder),
                    onChange = {
                        state.data.name = it
                    }
                )

                InputField(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    value = state.data.type,
                    label = stringResource(id = R.string.machine_type_input_label),
                    placeholder = stringResource(id = R.string.machine_type_input_placeholder),
                    onChange = {
                        state.data.type = it
                    }
                )

                InputField(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    value = state.data.code,
                    label = stringResource(id = R.string.machine_code_input_label),
                    placeholder = stringResource(id = R.string.machine_code_input_placeholder),
                    onChange = {
                        state.data.code = it
                    }
                )

                InputField(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    value = state.data.lastMaintain.toDate(),
                    label = stringResource(id = R.string.machine_last_maintain_input_label),
                    placeholder = stringResource(id = R.string.machine_last_maintain_input_placeholder),
                    onChange = {}
                )

                if (isDateDialogShow) {
                    DatePickerComponent(
                        selectedDate = state.data.lastMaintain,
                        onDismiss = { selectedDate ->
                            state.data.lastMaintain = (selectedDate ?: 0)
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
    onChange: (String) -> Unit
) {
    var text by rememberSaveable {
        mutableStateOf(value)
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
            onChange(text)
        },
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

@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen(state = DetailPageState(), onNavigateBack = {}, onSave = {})
}