package com.luqman.imagemachine.domain.usecase

import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.core.model.UiText
import com.luqman.imagemachine.data.repository.DataSource
import com.luqman.imagemachine.data.repository.model.Machine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetMachineByCodeUseCase @Inject constructor(
    private val repository: DataSource
) {
    operator fun invoke(code: String): Flow<Resource<Machine>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.getByCode(code)
            emit(Resource.Success(data))
        } catch (error: Exception) {
            Timber.e(error)
            emit(
                Resource.Error(
                    UiText.DynamicText(message = error.message.orEmpty())
                )
            )
        }
    }
}