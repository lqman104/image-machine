package com.luqman.imagemachine.ui.screens.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.data.repository.model.SortMenuType
import com.luqman.imagemachine.domain.usecase.GetAllMachineUseCase
import com.luqman.imagemachine.ui.navigation.Graph.Main.TYPE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getAllMachineUseCase: GetAllMachineUseCase,
    saveState: SavedStateHandle
): ViewModel() {

    private var type: String = saveState.get<String>(TYPE_KEY) ?: SortMenuType.NAME.toString()
    private val _listState: MutableStateFlow<ListScreenState> = MutableStateFlow(ListScreenState(loading = true))
    val listState = _listState.asStateFlow()

    fun setType(type: SortMenuType) {
        this.type = type.toString()
        getList()
    }

    fun getList() {
        viewModelScope.launch {
            getAllMachineUseCase.invoke(type).collect { response ->
                _listState.value = when(response) {
                    is Resource.Success -> ListScreenState(data = response.data)
                    is Resource.Loading -> ListScreenState(loading = true)
                    is Resource.Error -> ListScreenState(errorMessage = response.error)
                }
            }
        }
    }
}