package com.luqman.imagemachine.data.repository

import com.luqman.imagemachine.data.repository.model.Machine
import com.luqman.imagemachine.data.repository.model.SortMenuType
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getAll(
        sortBy: SortMenuType
    ): Flow<List<Machine>>
}