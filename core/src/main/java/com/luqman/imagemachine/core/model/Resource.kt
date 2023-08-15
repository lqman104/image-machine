package com.luqman.imagemachine.core.model

sealed class Resource<T>(
    open val data: T? = null,
    open val error: UiText? = null
) {
    data class Success<T>(
        override val data: T?,
    ) : Resource<T>(data = data)

    data class Error<T>(
        override val error: UiText?
    ) : Resource<T>(error = error)

    class Loading<T> : Resource<T>()
}
