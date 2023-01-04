package com.viennth.app.demo.data.datasource.remote

import com.google.gson.annotations.SerializedName
import com.viennth.app.demo.domain.model.ErrorType
import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.ResourceError
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

abstract class BaseService {

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> ApiResponse<T>): Resource<T?> =
        try {
            val response = apiToBeCalled.invoke()
            if (response.data == null) {
                Resource.Error(
                    ResourceError(
                        type = ErrorType.API,
                        errorMessage = response.errorMessage ?: "Something went wrong "
                    )
                )
            } else {
                Resource.Success(response.data)
            }
        } catch (throwable: Throwable) {
            Timber.d("Error: ${throwable.message}")
            when (throwable) {
                is HttpException -> Resource.Error(
                    ResourceError(
                        type = ErrorType.GENERIC,
                        errorMessage = "Something went wrong"
                    )
                )
                is IOException -> Resource.Error(
                    ResourceError(
                        type = ErrorType.GENERIC,
                        errorMessage = "Please check your network connection"
                    )
                )
                else -> Resource.Error(
                    ResourceError(
                        type = ErrorType.GENERIC,
                        errorMessage = "Something went wrong"
                    )
                )
            }
        }
}


data class ApiResponse<T>(
    @SerializedName("data") val data: T? = null,
    @SerializedName("errorMessage") val errorMessage: String? = null
)
