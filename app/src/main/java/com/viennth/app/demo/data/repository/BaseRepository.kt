package com.viennth.app.demo.data.repository

import com.viennth.app.demo.domain.model.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

abstract class BaseRepository {

    fun <T> Resource<T>.asFlow(): Flow<Resource<T>> = flow {
        emit(this@asFlow)
    }

    fun <T> makeApiCall(apiCall: suspend () -> Resource<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading)
        emit(apiCall())
    }
}
