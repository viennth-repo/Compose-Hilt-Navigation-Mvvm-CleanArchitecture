package com.viennth.app.demo.data.datasource.local

import com.viennth.app.demo.data.datasource.local.mapper.EntityMapper
import com.viennth.app.demo.data.repository.ILocalDataSource
import com.viennth.app.demo.domain.model.Sample
import timber.log.Timber
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val sampleDao: SampleDao
) : ILocalDataSource {
    override suspend fun insertSamples(samples: List<Sample>) {
        sampleDao.insertSample(samples.map {
            EntityMapper.toSampleEntity(it)
        })
    }
}