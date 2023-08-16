package com.luqman.imagemachine.ui.screens.detail

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.Color
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
import com.luqman.imagemachine.core.helper.DateHelper.toDate
import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.data.repository.model.Machine
import com.luqman.imagemachine.ui.screens.detail.DetailScreen.GRID_COUNT
import com.luqman.imagemachine.ui.screens.detail.SelectMultipleImageLauncher.multipleImagePickerLauncher
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

    DetailScreen(
        state = state,
        modifier = modifier,
        errorMessage = errorMessage,
        selectPicturesListener = { list ->
            viewModel.updateSelectedPictures(list)
        },
        onNavigateBack = {
            navController.navigateUp()
        },
        onSave = {
            viewModel.save()
        }
    )

    when (state.saveResult) {
        is Resource.Loading -> LoadingComponent(Modifier.fillMaxSize())
        is Resource.Success -> navController.navigateUp()
        is Resource.Error -> {
            errorMessage = state.saveResult?.error.toString()
        }

        else -> {}
    }
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
    selectPicturesListener: (List<Uri>) -> Unit,
    errorMessage: String? = null,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var itemsUri: List<Uri> by rememberSaveable {
        mutableStateOf(state.data.pictures.map { it.uri })
    }

    val multiSelectImageLauncher = multipleImagePickerLauncher(
        maxSize = 10,
        currentList = itemsUri,
        callback = { result ->
            itemsUri = result
            selectPicturesListener(itemsUri)
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
                    machine = state.data
                )
                this.imageSection(
                    pictures = itemsUri,
                    onClickImageAdd = {
                        multiSelectImageLauncher?.launch(
                            PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    onDeleteImage = { picture ->
                        itemsUri = itemsUri.toMutableList().apply {
                            remove(picture)
                        }
                        selectPicturesListener(itemsUri)
                    }
                )
            }
        }
    }
}

private fun LazyGridScope.formSection(
    modifier: Modifier = Modifier,
    machine: Machine
) {
    item(span = { GridItemSpan(GRID_COUNT) }) {
        Column(modifier) {
            InputField(
                modifier = Modifier.padding(bottom = 12.dp),
                value = machine.name,
                label = stringResource(id = R.string.machine_name_input_label),
                placeholder = stringResource(id = R.string.machine_name_input_placeholder),
                onChange = {
                    machine.name = it
                }
            )

            InputField(
                modifier = Modifier.padding(bottom = 12.dp),
                value = machine.type,
                label = stringResource(id = R.string.machine_type_input_label),
                placeholder = stringResource(id = R.string.machine_type_input_placeholder),
                onChange = {
                    machine.type = it
                }
            )

            InputField(
                modifier = Modifier.padding(bottom = 12.dp),
                value = machine.code,
                label = stringResource(id = R.string.machine_code_input_label),
                placeholder = stringResource(id = R.string.machine_code_input_placeholder),
                onChange = {
                    machine.code = it
                }
            )

            DateInputField(
                value = machine.lastMaintain,
                onChange = { date ->
                    machine.lastMaintain = date
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

fun LazyGridScope.imageSection(
    modifier: Modifier = Modifier,
    pictures: List<Uri>,
    onDeleteImage: (Uri) -> Unit,
    onClickImageAdd: () -> Unit
) {
    val picturesSize = pictures.size
    item(span = { GridItemSpan(GRID_COUNT) }) {
        Column(modifier = modifier) {
            Text(
                text = stringResource(id = R.string.image_subtitle_text, picturesSize),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    item {
        ButtonAddImage(
            enable = picturesSize < 10,
            onClick = onClickImageAdd
        )
    }

    items(pictures) { picture ->
        ImageThumbnail(
            picture = picture,
            onDeleteImage = onDeleteImage
        )
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
        value = text,
        modifier = modifier.fillMaxWidth(),
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
fun DateInputField(
    modifier: Modifier = Modifier,
    value: Long? = null,
    onChange: (Long) -> Unit
) {
    var isDateDialogShow by remember {
        mutableStateOf(false)
    }

    var dateValue: Long? by rememberSaveable {
        // make sure the form is null when first launch
        val longDate = if (value != null && value == 0L) null else value
        mutableStateOf(longDate)
    }

    OutlinedTextField(
        value = dateValue.toDate(),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isDateDialogShow = true
            },
        enabled = false,
        readOnly = true,
        onValueChange = {},
        label = {
            Text(text = stringResource(id = R.string.machine_last_maintain_input_label))
        },
        placeholder = {
            Text(text = stringResource(id = R.string.machine_last_maintain_input_placeholder))
        },
    )

    if (isDateDialogShow) {
        DatePickerComponent(
            selectedDate = dateValue,
            onDismiss = { selectedDate ->
                isDateDialogShow = false

                if (selectedDate != null) {
                    dateValue = selectedDate
                    onChange(selectedDate)
                }
            }
        )
    }
}

@Composable
fun ImageThumbnail(
    modifier: Modifier = Modifier,
    picture: Uri,
    onDeleteImage: (Uri) -> Unit
) {
    Box(modifier = modifier.aspectRatio(1f)) {
        AsyncImage(
            model = picture,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(corner = CornerSize(16.dp))),
            contentScale = ContentScale.Crop,
        )
        IconButton(
            modifier = Modifier
                .border(
                    shape = CircleShape,
                    width = 1.dp,
                    color = Color.Transparent
                )
                .padding(8.dp)
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .size(20.dp)
                .align(Alignment.TopEnd),
            onClick = { onDeleteImage(picture) }
        ) {
            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = Icons.Default.Close,
                contentDescription = "delete the image",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun ButtonAddImage(
    modifier: Modifier = Modifier,
    enable: Boolean,
    onClick: () -> Unit
) {
    val color = MaterialTheme.colorScheme.secondary
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    Box(
        modifier = modifier
            .background(if (!enable) Color.LightGray else Color.Transparent)
            .aspectRatio(1f)
            .drawBehind {
                drawRoundRect(
                    color = color,
                    style = stroke,
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            }
            .clickable(enabled = enable) {
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
    DetailScreen(
        state = DetailPageState(),
        onNavigateBack = {},
        onSave = {},
        selectPicturesListener = {})
}

@Preview
@Composable
fun ButtonAddImagePreview() {
    ButtonAddImage(
        enable = true
    ) {

    }
}

@Preview
@Composable
fun ImageThumbnailPreview() {
    ImageThumbnail(picture = Uri.EMPTY, onDeleteImage = {})
}

object DetailScreen {
    const val GRID_COUNT = 3
}