package com.viennth.app.demo.domain.usecase

import com.viennth.app.demo.domain.abstraction.ISampleRepository
import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.Sample
import com.viennth.app.demo.domain.model.transformData
import timber.log.Timber
import javax.inject.Inject

class SampleUseCaseV2Impl @Inject constructor(
    private val sampleRepository: ISampleRepository
): SampleUseCase {
    override suspend fun getSamples(): Resource<List<Sample>> {
        return sampleRepository.getSamples().transformData { list ->
            list?.filter { it.id == "1002" }
        }
    }

    override suspend fun insertSamples(samples: List<Sample>) {
        if (samples.isNotEmpty()) {
            sampleRepository.insertSamples(samples)
        }
    }
}
