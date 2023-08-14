package com.luqman.imagemachine.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier, content = {
        items(30) { index ->
            Item(
                modifier = Modifier.fillMaxWidth(),
                name = "${index + 1}",
                type = "This is machine type"
            )
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
    ListScreen()
}