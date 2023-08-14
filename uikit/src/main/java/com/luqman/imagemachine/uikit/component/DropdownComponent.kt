package com.luqman.imagemachine.uikit.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownComponent(
    label: String,
    selectedValue: String,
    selection: List<String>,
    modifier: Modifier = Modifier,
    onSelected: (index: Int, text: String) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            label = {
                Text(text = label)
            },
            readOnly = true,
            value = selectedValue,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            onValueChange = {}
        )
        DropdownMenu(
            modifier = Modifier.exposedDropdownSize(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            selection.forEachIndexed { index, text ->
                DropdownMenuItem(
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    text = {
                        Text(text = text)
                    },
                    onClick = {
                        expanded = false
                        onSelected(index, text)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DropdownComponentPreview() {
    DropdownComponent(
        label = "",
        selectedValue = "",
        selection = listOf("One", "Two", "Three"),
        onSelected = { _, _ -> }
    )
}