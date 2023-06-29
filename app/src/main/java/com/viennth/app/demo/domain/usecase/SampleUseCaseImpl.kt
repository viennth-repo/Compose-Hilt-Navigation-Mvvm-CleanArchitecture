package com.viennth.app.demo.domain.usecase

import com.viennth.app.demo.domain.abstraction.ISampleRepository
import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.Sample
import javax.inject.Inject

class SampleUseCaseImpl @Inject constructor(
    private val sampleRepository: ISampleRepository
): SampleUseCase {
    override suspend fun getSamples(): Resource<List<Sample>> {
        return sampleRepository.getSamples()
    }

    override suspend fun insertSamples(samples: List<Sample>) {
    }
}
