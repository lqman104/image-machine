package com.luqman.imagemachine.core.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)