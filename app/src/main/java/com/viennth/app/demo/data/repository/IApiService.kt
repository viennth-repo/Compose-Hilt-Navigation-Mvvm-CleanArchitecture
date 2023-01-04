package com.viennth.app.demo.data.repository

import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.Sample


interface IApiService {

    suspend fun getSamples(): Resource<List<Sample>>
}