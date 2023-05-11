package com.viennth.app.demo.domain.usecase

import com.viennth.app.demo.domain.abstraction.ISampleRepository
import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.Sample
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class SampleUseCaseV2Impl @Inject constructor(
    private val sampleRepository: ISampleRepository
): SampleUseCase {
    override suspend fun getSamples(): Flow<Resource<List<Sample>>> {
        Timber.d("=>> SampleUseCaseV2Impl getSamples")
        return sampleRepository.getSamples()
    }
}
