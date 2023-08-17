package com.luqman.imagemachine.ui.screens.photopreview

import com.luqman.imagemachine.core.model.Resource

data class PreviewScreenState(
    var result: Resource<List<String>>? = null,
)
