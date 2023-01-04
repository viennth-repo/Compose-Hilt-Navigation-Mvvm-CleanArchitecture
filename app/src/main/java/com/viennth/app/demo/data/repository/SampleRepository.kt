package com.viennth.app.demo.data.repository

import com.viennth.app.demo.domain.abstraction.ISampleRepository
import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.Sample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SampleRepository @Inject constructor(
    private val apiService: IApiService,
    private val localDataSource: ILocalDataSource
) : BaseRepository(), ISampleRepository {
    override suspend fun getSamples(): Flow<Resource<List<Sample>>> = flow {
        makeApiCall { apiService.getSamples() }
            .collect { r ->
                if (r is Resource.Success && !r.data.isNullOrEmpty()) {
                    insertSamples(r.data)
                }
                emit(r)
            }
    }

    override suspend fun insertSamples(samples: List<Sample>) =
        localDataSource.insertSamples(samples)
}