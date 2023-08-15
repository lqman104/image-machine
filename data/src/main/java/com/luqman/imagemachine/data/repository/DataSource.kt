package com.luqman.imagemachine.data.repository

import com.luqman.imagemachine.data.repository.model.Machine

interface DataSource {
    suspend fun fetch(): List<Machine>
}