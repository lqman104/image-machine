package com.luqman.imagemachine.data.repository

import com.luqman.imagemachine.data.repository.model.Response

class DataRepository: DataSource {

    override suspend fun fetch(): List<Response> {
        return try {
            // TODO: do something
            listOf()
        } catch (e: Exception) {
            listOf()
        }
    }

}