package com.viennth.app.demo.domain.usecase

import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.Sample
import kotlinx.coroutines.flow.Flow

interface SampleUseCase {

    suspend fun getSamples(): Flow<Resource<List<Sample>>>
}