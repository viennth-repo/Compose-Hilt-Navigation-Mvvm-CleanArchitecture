package com.viennth.app.demo.data.repository

import com.viennth.app.demo.domain.abstraction.ISampleRepository
import com.viennth.app.demo.domain.model.Sample
import javax.inject.Inject

class SampleRepository @Inject constructor(
    private val apiService: IApiService,
    private val localDataSource: ILocalDataSource
) : BaseRepository(), ISampleRepository {
    override suspend fun getSamples() = apiService.getSamples()

    override suspend fun insertSamples(samples: List<Sample>) =
        localDataSource.insertSamples(samples)
}
