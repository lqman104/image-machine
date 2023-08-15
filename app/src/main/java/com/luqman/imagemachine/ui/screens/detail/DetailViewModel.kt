package com.luqman.imagemachine.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.luqman.imagemachine.data.repository.model.Machine
import dagger.hilt.android.lifecycle.HiltViewModel

class DetailViewModel: ViewModel() {
    val machine: Machine = Machine(
        name = "",
        type = "",
        code = "",
        lastMaintain = 0,
        pictures = listOf()
    )
}