package com.luqman.imagemachine.data.repository

import com.luqman.imagemachine.data.repository.model.Machine
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getAll(
        sortBy: String
    ): Flow<List<Machine>>

    suspend fun get(
        id: String
    ): Machine

    suspend fun delete(
        id: String
    )

    suspend fun store(machine: Machine)
}