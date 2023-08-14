package com.luqman.imagemachine.uikit.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.luqman.imagemachine.uikit.R


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DatePickerComponent(
    selectedDate: Long?,
    onDismiss: (selectedDate: Long?) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate ?: System.currentTimeMillis()
    )
    val confirmEnable by remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    DatePickerDialog(
        onDismissRequest = { onDismiss(datePickerState.selectedDateMillis) },
        confirmButton = {
            TextButton(
                enabled = confirmEnable,
                onClick = { onDismiss(datePickerState.selectedDateMillis) }
            ) {
                Text(stringResource(id = R.string.ok_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss(datePickerState.selectedDateMillis) }
            ) {
                Text(stringResource(id = R.string.cancel_button))
            }
        }
    ) {
        DatePicker(
            showModeToggle = false,
            state = datePickerState
        )
    }
}