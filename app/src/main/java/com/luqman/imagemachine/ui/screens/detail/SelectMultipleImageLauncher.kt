package com.luqman.imagemachine.ui.screens.detail

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


object SelectMultipleImageLauncher {

    @Composable
    fun rememberMultipleImagePickerLauncher(
        maxSize: Int,
        currentList: List<String>,
        callback: (List<String>) -> Unit
    ): ManagedActivityResultLauncher<PickVisualMediaRequest, out Any?>? {
        // don't allow selected more than maxsize
        val remain = maxSize - currentList.size
        val context = LocalContext.current
        return if (1 < remain) {
            // set to multiple select when the remaining still more then 1
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = remain),
                onResult = { uris ->
                    if (uris.isNotEmpty()) {
                        val paths = uris.map {
                            it.getImageFilePath(context)
                        }
                        callback(
                            currentList.toMutableList().apply {
                                addAll(paths)
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
                                addAll(listOf(uri.getImageFilePath(context)))
                            }
                        )
                    }
                }
            )
        } else {
            null
        }
    }

    private fun Uri.getImageFilePath(context: Context): String {
        var cursor: Cursor? = null
        val path: String = try {
            cursor = context.contentResolver.query(
                this,
                arrayOf(MediaStore.Images.Media.DATA),
                null,
                null,
                null
            )
            cursor?.let {
                val column = it.getColumnIndex(MediaStore.Images.Media.DATA)
                val data = if(column > 0) column else 0
                it.moveToFirst()
                it.getString(data)
            }.orEmpty()
        } catch (e: Exception) {
            ""
        } finally {
            cursor?.close()
        }
        return path
    }
}