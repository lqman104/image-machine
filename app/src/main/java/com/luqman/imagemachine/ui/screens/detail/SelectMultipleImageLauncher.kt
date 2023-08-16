package com.luqman.imagemachine.ui.screens.detail

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

object SelectMultipleImageLauncher {

    @Composable
    fun multipleImagePickerLauncher(
        maxSize: Int,
        currentList: List<Uri>,
        callback: (List<Uri>) -> Unit
    ): ManagedActivityResultLauncher<PickVisualMediaRequest, out Any?>? {
        // don't allow selected more than maxsize
        val remain = maxSize - currentList.size

        return if (1 < remain) {
            // set to multiple select when the remaining still more then 1
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = remain),
                onResult = { uris ->
                    if (uris.isNotEmpty()) {
                        callback(
                            currentList.toMutableList().apply {
                                addAll(uris)
                            }
                        )
                    }
                }
            )
        } else if (0 < remain) {
            // set to single select when the remaining equal 1
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = { uri ->
                    if (uri != null) {
                        callback(
                            currentList.toMutableList().apply {
                                addAll(listOf(uri))
                            }
                        )
                    }
                }
            )
        } else {
            null
        }
    }
}