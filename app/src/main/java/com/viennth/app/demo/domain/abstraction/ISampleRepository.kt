package com.viennth.app.demo.domain.abstraction

import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.Sample

interface ISampleRepository {
    suspend fun getSamples(): Resource<List<Sample>>

    suspend fun insertSamples(samples: List<Sample>)
}
