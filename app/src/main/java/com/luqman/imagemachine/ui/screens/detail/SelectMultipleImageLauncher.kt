package com.luqman.imagemachine.ui.screens.detail

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID


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
                        val paths = uris.mapNotNull {
                            it.saveContentToFile(context)
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
                                uri.saveContentToFile(context)?.let {
                                    addAll(listOf(it))
                                }
                            }
                        )
                    }
                }
            )
        } else {
            null
        }
    }

    private fun Uri.saveContentToFile(context: Context): String? {
        val contentResolver: ContentResolver = context.contentResolver

        try {
            val inputStream = contentResolver.openInputStream(this)

            val appFilesDir = context.filesDir
            val targetFile = File(appFilesDir, "${UUID.randomUUID()}.jpg")
            val outputStream = FileOutputStream(targetFile)

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream?.read(buffer).also { bytesRead = it!! } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.close()
            inputStream?.close()

            return targetFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}