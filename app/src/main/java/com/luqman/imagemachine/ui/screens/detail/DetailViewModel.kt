package com.luqman.imagemachine.ui.screens.detail

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.imagemachine.data.repository.model.Picture
import com.luqman.imagemachine.domain.usecase.StoreMachineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: StoreMachineUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<DetailPageState> = MutableStateFlow(DetailPageState())
    val state = _state.asStateFlow()

    fun updateSelectedPictures(
        list: List<Uri>
    ) {
        _state.value = _state.value.copy(
            data = _state.value.data.copy(
                pictures = list.map { Picture(path = it.path.toString()) }.toMutableList()
            )
        )
    }

    fun save() {
        useCase(_state.value.data).onEach { response ->
            _state.value = _state.value.copy(
                saveResult = response
            )
        }.launchIn(viewModelScope)
    }

}