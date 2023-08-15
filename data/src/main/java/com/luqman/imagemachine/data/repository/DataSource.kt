package com.luqman.imagemachine.data.repository

import com.luqman.imagemachine.data.repository.model.Machine
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getAll(
        sortBy: String
    ): Flow<List<Machine>>
}