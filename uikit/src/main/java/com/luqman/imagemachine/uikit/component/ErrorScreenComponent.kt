package com.luqman.imagemachine.uikit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luqman.imagemachine.uikit.theme.AppTheme


@Composable
fun ErrorScreenComponent(
    modifier: Modifier = Modifier,
    title: String,
    Image: @Composable (() -> Unit)? = null,
    message: String? = null,
    actionButtonText: String = "",
    showActionButton: Boolean = false,
    onActionButtonClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (Image != null) {
            Image()
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (message != null) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 4.dp)
            )
        }

        if (showActionButton) {
            Spacer(modifier = Modifier.padding(12.dp))
            Button(onClick = { onActionButtonClicked() }) {
                Text(text = actionButtonText)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun ErrorScreenComponentPreview() {
    AppTheme {
        ErrorScreenComponent(
            message = "Try again later",
            title = "No internet connection",
            showActionButton = true,
            actionButtonText = "Retry",
            modifier = Modifier.fillMaxSize()
        )
    }
}