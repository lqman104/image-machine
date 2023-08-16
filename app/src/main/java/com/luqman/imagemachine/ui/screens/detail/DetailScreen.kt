package com.luqman.imagemachine.ui.screens.detail

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.luqman.imagemachine.R
import com.luqman.imagemachine.core.helper.DateHelper.dateToLong
import com.luqman.imagemachine.core.helper.DateHelper.toDate
import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.ui.screens.detail.DetailScreen.GRID_COUNT
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: DetailPageState,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var isDateDialogShow by remember {
        mutableStateOf(false)
    }

    var itemsUri: List<Uri> by rememberSaveable {
        mutableStateOf(state.data.pictures.map { it.uri })
    }
    val multiSelectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10),
        onResult = { uris ->
            itemsUri = uris
        }
    )

    if (errorMessage.isNullOrEmpty().not()) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(
                message = errorMessage.orEmpty()
            )
        }
    }
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopBar(scrollBehavior = scrollBehavior) {
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
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LazyVerticalGrid(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                columns = GridCells.Fixed(GRID_COUNT),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                this.formSection(
                    state = state,
                    isDateDialogShow = isDateDialogShow,
                    onDialogDismiss = {
                        isDateDialogShow = false
                    }
                )
                this.imageSection(
                    pictures = itemsUri,
                    onClickImageAdd = {
                        multiSelectImageLauncher.launch(
                            PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
        }
    }
}

private fun LazyGridScope.formSection(
    modifier: Modifier = Modifier,
    state: DetailPageState,
    isDateDialogShow: Boolean,
    onDialogDismiss: () -> Unit
) {
    item(span = { GridItemSpan(GRID_COUNT) }) {
        Column(modifier) {
            InputField(

                value = state.data.name,
                label = stringResource(id = R.string.machine_name_input_label),
                placeholder = stringResource(id = R.string.machine_name_input_placeholder),
                onChange = {
                    state.data.name = it
                }
            )

            InputField(

                value = state.data.type,
                label = stringResource(id = R.string.machine_type_input_label),
                placeholder = stringResource(id = R.string.machine_type_input_placeholder),
                onChange = {
                    state.data.type = it
                }
            )

            InputField(

                value = state.data.code,
                label = stringResource(id = R.string.machine_code_input_label),
                placeholder = stringResource(id = R.string.machine_code_input_placeholder),
                onChange = {
                    state.data.code = it
                }
            )

            InputField(
                modifier = Modifier.clickable {
                    onDialogDismiss()
                },
                readOnly = true,
                enable = false,
                value = state.data.lastMaintain.toDate(),
                label = stringResource(id = R.string.machine_last_maintain_input_label),
                placeholder = stringResource(id = R.string.machine_last_maintain_input_placeholder),
                onChange = {
                    state.data.lastMaintain = it.dateToLong()
                }
            )

            if (isDateDialogShow) {
                DatePickerComponent(
                    selectedDate = state.data.lastMaintain,
                    onDismiss = { selectedDate ->
                        state.data.lastMaintain = (selectedDate ?: 0)
                        onDialogDismiss()
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

fun LazyGridScope.imageSection(
    modifier: Modifier = Modifier,
    pictures: List<Uri>,
    onClickImageAdd: () -> Unit
) {
    item(span = { GridItemSpan(GRID_COUNT) }) {
        Column(modifier = modifier) {
            Text(
                text = stringResource(id = R.string.image_subtitle_text),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (pictures.size < 10) {
        item {
            ButtonAddImage(
                onClick = onClickImageAdd
            )
        }
    }

    items(pictures) { picture ->
        Box(modifier = Modifier.aspectRatio(1f)) {
            AsyncImage(
                model = picture,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(corner = CornerSize(16.dp))),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    enable: Boolean = true,
    value: String,
    placeholder: String,
    label: String,
    onChange: (String) -> Unit
) {
    var text by rememberSaveable {
        mutableStateOf(value)
    }

    OutlinedTextField(
        value = text,
        modifier = modifier.fillMaxWidth(),
        enabled = enable,
        readOnly = readOnly,
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

@Composable
fun ButtonAddImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val color = MaterialTheme.colorScheme.secondary
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .drawBehind {
                drawRoundRect(
                    color = color,
                    style = stroke,
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            }
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add image")
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen(state = DetailPageState(), onNavigateBack = {}, onSave = {})
}

@Preview
@Composable
fun ButtonAddImagePreview() {
    ButtonAddImage {

    }
}

object DetailScreen {
    const val GRID_COUNT = 3
}