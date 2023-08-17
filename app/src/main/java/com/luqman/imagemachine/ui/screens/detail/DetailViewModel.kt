package com.luqman.imagemachine.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.data.repository.model.Machine
import com.luqman.imagemachine.domain.usecase.DeleteMachineUseCase
import com.luqman.imagemachine.domain.usecase.GetMachineByCodeUseCase
import com.luqman.imagemachine.domain.usecase.GetMachineUseCase
import com.luqman.imagemachine.domain.usecase.StoreMachineUseCase
import com.luqman.imagemachine.ui.navigation.Graph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val storeUseCase: StoreMachineUseCase,
    private val getUseCaseById: GetMachineUseCase,
    private val getUseCaseByCode: GetMachineByCodeUseCase,
    private val deleteUseCase: DeleteMachineUseCase,
    saveState: SavedStateHandle
) : ViewModel() {

    private var id: String? = saveState.get<String>(Graph.Detail.ID_KEY)
    private var code: String? = saveState.get<String>(Graph.Detail.CODE_KEY)
    private val _state: MutableStateFlow<DetailPageState> = MutableStateFlow(DetailPageState())
    val state = _state.asStateFlow()

    init {
        val id = id
        val code = code

        if (!id.isNullOrEmpty()) {
            getDetailById(id)
        } else if (!code.isNullOrEmpty()) {
            getDetailByCode(code)
        }
    }

    private fun getDetailById(id: String) {
        viewModelScope.launch {
            getUseCaseById(id).collect { response ->
                handleDetailResponse(response)
            }
        }
    }

    private fun getDetailByCode(code: String) {
        viewModelScope.launch {
            getUseCaseByCode(code).collect { response ->
                handleDetailResponse(response)
            }
        }
    }

    private fun handleDetailResponse(response: Resource<Machine>) {
        _state.value = _state.value.copy(
            detailGetResult = response
        )

        if (response is Resource.Success) {
            _state.value = _state.value.copy(
                id = response.data?.id.orEmpty(),
                name = response.data?.name.orEmpty(),
                type = response.data?.type.orEmpty(),
                code = response.data?.code.orEmpty(),
                lastMaintain = response.data?.lastMaintain ?: 0,
                pictures = response.data?.pictures.orEmpty().toMutableList(),
            )
        }
    }

    fun delete() {
        viewModelScope.launch {
            deleteUseCase(_state.value.id.orEmpty()).collect { response ->
                _state.value = _state.value.copy(
                    deleteResult = response
                )
            }
        }
    }

    fun updateName(value: String) {
        _state.value = _state.value.copy(
            name = value
        )
    }

    fun updateType(value: String) {
        _state.value = _state.value.copy(
            type = value
        )
    }

    fun updateCode(value: String) {
        _state.value = _state.value.copy(
            code = value
        )
    }

    fun updateLastMaintain(value: Long) {
        _state.value = _state.value.copy(
            lastMaintain = value
        )
    }

    fun updateSelectedPictures(
        list: List<String>
    ) {
        _state.value = _state.value.copy(
            pictures = list
        )
    }

    fun save() {
        val id = _state.value.id ?: UUID.randomUUID().toString()
        val machine = with(_state.value) {
            Machine(
                id = id,
                name = name,
                code = code,
                type = type,
                lastMaintain = lastMaintain,
                pictures = pictures
            )
        }
        storeUseCase(machine).onEach { response ->
            _state.value = _state.value.copy(
                saveResult = response
            )
        }.launchIn(viewModelScope)
    }

}