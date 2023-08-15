package com.luqman.imagemachine.data.repository

import com.luqman.imagemachine.data.repository.model.Machine

class DataRepository: DataSource {

    override suspend fun fetch(): List<Machine> {
        return try {
            // TODO: do something
            listOf()
        } catch (e: Exception) {
            listOf()
        }
    }

}