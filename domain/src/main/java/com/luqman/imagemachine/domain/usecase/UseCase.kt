package com.luqman.imagemachine.domain.usecase

import com.luqman.imagemachine.core.model.UiText
import com.luqman.imagemachine.core.model.ValidationResult
import com.luqman.imagemachine.domain.R

class UseCase {

    operator fun invoke(input: String): ValidationResult {
        return when {
            input.isEmpty() -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.data_empty_error)
            )
            else -> {
                ValidationResult(
                    successful = true
                )
            }
        }
    }
}