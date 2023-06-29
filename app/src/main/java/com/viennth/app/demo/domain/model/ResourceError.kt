package com.viennth.app.demo.domain.model

data class ResourceError(
    val type: ErrorType? = null,
    val errorCode: Int? = null,
    val errorMessage: String? = null,
)

enum class ErrorType {
    GENERIC,
    API
}
