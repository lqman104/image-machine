package com.luqman.imagemachine.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.data.repository.model.SortMenuType
import com.luqman.imagemachine.domain.usecase.GetAllMachineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getAllMachineUseCase: GetAllMachineUseCase
): ViewModel() {

    private var type: SortMenuType = SortMenuType.NAME
    private val _listState: MutableStateFlow<ListScreenState> = MutableStateFlow(ListScreenState(loading = true))
    val listState = _listState.asStateFlow()

    init {
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

    fun getMachineByType(type: SortMenuType) {
        this.type = type
        getList()
    }


}