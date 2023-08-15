package com.luqman.imagemachine.domain.usecase

import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.core.model.toUiText
import com.luqman.imagemachine.data.repository.DataSource
import com.luqman.imagemachine.data.repository.model.Machine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreMachineUseCase @Inject constructor(
    private val repository: DataSource
) {

    operator fun invoke(machine: Machine): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            repository.store(machine)
            emit(Resource.Success(null))
        } catch (error: Exception) {
            emit(Resource.Error(error.toUiText()))
        }
    }
}