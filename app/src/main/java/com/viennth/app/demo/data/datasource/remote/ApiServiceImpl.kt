package com.viennth.app.demo.data.datasource.remote

import com.viennth.app.demo.data.datasource.remote.dto.toSample
import com.viennth.app.demo.data.repository.IApiService
import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.transformData
import com.viennth.app.demo.domain.model.Sample
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val api: Api
): BaseService(), IApiService {
    override suspend fun getSamples(): Resource<List<Sample>> = safeApiCall {
        api.getSamplesAsync().await()
    }.transformData {
        it?.map { dto ->
            dto.toSample()
        }
    }



}