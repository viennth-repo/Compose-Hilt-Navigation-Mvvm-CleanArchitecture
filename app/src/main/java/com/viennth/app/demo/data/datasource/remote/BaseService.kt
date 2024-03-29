package com.viennth.app.demo.data.datasource.remote

import com.google.gson.annotations.SerializedName
import com.viennth.app.demo.domain.model.ErrorType
import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.ResourceError
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException

abstract class BaseService {

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> ApiResponse<T>): Resource<T?> =
        try {
            val response = apiToBeCalled.invoke()
            if (response.data == null) {
                Resource.Error(
                    ResourceError(
                        type = ErrorType.API,
                        errorCode = response.errorCode,
                        errorMessage = response.errorMessage ?: "Something went wrong "
                    )
                )
            } else {
                Resource.Success(response.data)
            }
        } catch (throwable: Throwable) {
            Timber.d("Error: $throwable")
            when (throwable) {
                is HttpException -> Resource.Error(
                    ResourceError(
                        type = ErrorType.GENERIC,
                        errorCode = throwable.code(),
                        errorMessage = "Something went wrong"
                    )
                )
                is ConnectException -> Resource.Error(
                    ResourceError(
                        type = ErrorType.GENERIC,
                        errorCode = 404,
                        errorMessage = "Please check your network connection"
                    )
                )
                else -> Resource.Error(
                    ResourceError(
                        type = ErrorType.GENERIC,
                        errorCode = -1,
                        errorMessage = "Something went wrong"
                    )
                )
            }
        }
}


data class ApiResponse<T>(
    @SerializedName("data") val data: T? = null,
    @SerializedName("errorMessage") val errorMessage: String? = null,
    @SerializedName("errorCode") val errorCode: Int? = null
)
