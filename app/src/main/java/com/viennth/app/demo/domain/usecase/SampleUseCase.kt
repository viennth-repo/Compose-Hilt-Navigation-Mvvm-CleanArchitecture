package com.viennth.app.demo.domain.usecase

import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.Sample

interface SampleUseCase {

    suspend fun getSamples(): Resource<List<Sample>>

    suspend fun insertSamples(samples: List<Sample>)
}
