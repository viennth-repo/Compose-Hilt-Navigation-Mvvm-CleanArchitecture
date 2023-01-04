package com.viennth.app.demo.data.repository

import com.viennth.app.demo.domain.model.Sample

interface ILocalDataSource {
    suspend fun insertSamples(samples: List<Sample>)
}