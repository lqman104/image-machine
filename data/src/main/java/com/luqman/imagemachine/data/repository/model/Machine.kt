package com.luqman.imagemachine.data.repository.model

import java.util.UUID

data class Machine(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var type: String,
    var code: String,
    var lastMaintain: Long,
    var pictures: List<String> = listOf(),
)
