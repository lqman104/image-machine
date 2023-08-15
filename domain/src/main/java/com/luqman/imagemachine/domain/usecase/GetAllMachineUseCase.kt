package com.luqman.imagemachine.domain.usecase

import com.luqman.imagemachine.core.model.Resource
import com.luqman.imagemachine.core.model.UiText
import com.luqman.imagemachine.data.repository.DataSource
import com.luqman.imagemachine.data.repository.model.Machine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetAllMachineUseCase @Inject constructor(
    private val repository: DataSource
) {

    suspend operator fun invoke(sortMenuType: String): Flow<Resource<List<Machine>>> {
        return repository.getAll(sortMenuType).map { data ->
            Resource.Success(data)
        }.onStart {
            Resource.Loading<List<Machine>>()
        }.catch { error ->
            Resource.Error<List<Machine>>(
                UiText.DynamicText(message = error.message.orEmpty())
            )
        }
    }
}