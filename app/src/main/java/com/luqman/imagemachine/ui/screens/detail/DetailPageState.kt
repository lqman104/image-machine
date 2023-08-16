package com.luqman.imagemachine.ui.screens.detail

import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.data.repository.model.Machine

data class DetailPageState(
    var saveResult: Resource<Any>? = null,
    val data: Machine = Machine(
        name = "",
        type = "",
        code = "",
        lastMaintain = 0,
        pictures = mutableListOf()
    )
)
