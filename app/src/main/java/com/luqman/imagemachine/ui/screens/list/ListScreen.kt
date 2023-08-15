package com.luqman.imagemachine.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.luqman.imagemachine.R
import com.luqman.imagemachine.uikit.component.ErrorScreenComponent
import com.luqman.imagemachine.uikit.component.LoadingComponent

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel()
) {
    val state = viewModel.listState.collectAsState().value
    ListScreen(state, modifier) {
        viewModel.getList()
    }
}

@Composable
fun ListScreen(
    state: ListScreenState,
    modifier: Modifier = Modifier,
    onRetryAction : () -> Unit
) {
    LazyColumn(modifier = modifier, content = {
        when {
            state.loading -> {
                item {
                    LoadingComponent(
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }

            state.data != null -> {
                if (state.data.isEmpty()) {
                    item {
                        ErrorScreenComponent(
                            modifier = Modifier.fillParentMaxSize(),
                            title = stringResource(id = R.string.empty_title_message),
                            message = stringResource(id = R.string.empty_desc_message)
                        )
                    }
                } else {
                    items(state.data) { item ->
                        Item(
                            modifier = Modifier.fillMaxWidth(),
                            name = item.name,
                            type = item.type
                        )
                    }
                }
            }

            state.errorMessage != null -> {
                item {
                    ErrorScreenComponent(
                        modifier = Modifier.fillParentMaxSize(),
                        title = stringResource(id = R.string.error_title_message),
                        message = state.errorMessage.toString(),
                        showActionButton = true,
                        onActionButtonClicked = onRetryAction
                    )
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Item(
    modifier: Modifier = Modifier,
    name: String,
    type: String
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = {}) {
        Column(Modifier.padding(all = 8.dp)) {
            Text(name, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(type, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
fun ListPreview() {
    ListScreen(state = ListScreenState(loading = true)) {

    }
}