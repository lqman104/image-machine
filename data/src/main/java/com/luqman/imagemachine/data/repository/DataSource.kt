package com.luqman.imagemachine.data.repository

import com.luqman.imagemachine.data.repository.model.Response

interface DataSource {
    suspend fun fetch(): List<Response>
}