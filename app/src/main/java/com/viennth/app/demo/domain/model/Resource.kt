package com.viennth.app.demo.domain.model

sealed class Resource<out T> {
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error(val resourceError: ResourceError) : Resource<Nothing>()
}

inline fun <T, R> Resource<T>.transformData(
    crossinline transform: (data: T?) -> R?
): Resource<R> = when (this) {
    is Resource.Success -> Resource.Success(
        transform(this.data)
    )
    is Resource.Error -> this
}
