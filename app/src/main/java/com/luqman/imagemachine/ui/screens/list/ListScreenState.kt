package com.luqman.imagemachine.ui.screens.list

import com.luqman.imagemachine.core.model.UiText
import com.luqman.imagemachine.data.repository.model.Machine

data class ListScreenState(
    val data: List<Machine>? = null,
    val loading: Boolean = false,
    val errorMessage: UiText? = null
)