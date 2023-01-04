package com.viennth.app.demo.domain.abstraction

import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.Sample
import kotlinx.coroutines.flow.Flow

interface ISampleRepository {
    suspend fun getSamples(): Flow<Resource<List<Sample>>>

    suspend fun insertSamples(samples: List<Sample>)
}