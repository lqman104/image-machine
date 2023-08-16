package com.luqman.imagemachine.data.repository.model

import android.net.Uri
import java.io.File

data class Picture(
    val id: Int = 0,
    var path: String = "",
) {
    val uri: Uri
        get() = Uri.fromFile(File(path))
}