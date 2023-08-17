package com.luqman.imagemachine.domain.usecase

import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.core.model.UiText
import com.luqman.imagemachine.core.model.toUiText
import com.luqman.imagemachine.data.repository.DataSource
import com.luqman.imagemachine.data.repository.model.Machine
import com.luqman.imagemachine.domain.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreMachineUseCase @Inject constructor(
    private val repository: DataSource
) {

    operator fun invoke(machine: Machine): Flow<Resource<Any>> = flow {
        try {
            when {
                machine.name.isEmpty() -> emit(Resource.Error(UiText.StringResource(R.string.name_empty_error)))
                machine.type.isEmpty() -> emit(Resource.Error(UiText.StringResource(R.string.type_empty_error)))
                machine.code.isEmpty() -> emit(Resource.Error(UiText.StringResource(R.string.code_empty_error)))
                machine.lastMaintain == 0L -> emit(Resource.Error(UiText.StringResource(R.string.last_maintain_empty_error)))
                else -> {
                    emit(Resource.Loading())
                    repository.store(machine)
                    emit(Resource.Success(null))
                }
            }
        } catch (error: Exception) {
            emit(Resource.Error(error.toUiText()))
        }
    }
}