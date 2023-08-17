package com.luqman.imagemachine.ui.screens.detail

import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.data.repository.model.Machine

data class DetailPageState(
    var saveResult: Resource<Any>? = null,
    var deleteResult: Resource<Any>? = null,
    var detailGetResult: Resource<Machine>? = null,
    var id: String? = null,
    var name: String = "",
    var type: String = "",
    var code: String = "",
    var lastMaintain: Long = 0,
    var pictures: List<String> = listOf(),
)
