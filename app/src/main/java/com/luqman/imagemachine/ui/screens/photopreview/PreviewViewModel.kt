package com.luqman.imagemachine.ui.screens.photopreview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.ui.navigation.Graph.Detail.PICTURES_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    saveState: SavedStateHandle
) : ViewModel() {

    private var pictures: String = saveState.get<String>(PICTURES_KEY).orEmpty()
    private val _state: MutableStateFlow<PreviewScreenState> =
        MutableStateFlow(PreviewScreenState())
    val state = _state.asStateFlow()

    init {
        getPictures()
    }

    private fun getPictures() {
        viewModelScope.launch {
            _state.value = _state.value.copy(result = Resource.Success(pictures.split(", ")))
        }
    }
}