package com.luqman.imagemachine.ui.screens.detail

import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.data.repository.model.Machine
import com.luqman.imagemachine.data.repository.model.Picture

data class DetailPageState(
    var saveResult: Resource<Any>? = null,
    var detailGetResult: Resource<Machine>? = null,
    var name: String = "",
    var type: String = "",
    var code: String = "",
    var lastMaintain: Long = 0,
    var pictures: MutableList<Picture> = mutableListOf(),
)
