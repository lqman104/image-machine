package com.luqman.imagemachine.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.luqman.imagemachine.data.repository.model.Machine

class DetailViewModel: ViewModel() {
    val machine: Machine = Machine(
        name = "",
        type = "",
        code = "",
        lastMaintain = 0,
        pictures = mutableListOf()
    )
}