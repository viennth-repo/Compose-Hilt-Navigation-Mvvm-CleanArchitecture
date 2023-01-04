package com.viennth.app.demo.data.datasource.local.mapper

import com.viennth.app.demo.data.datasource.local.entity.SampleEntity
import com.viennth.app.demo.domain.model.Sample

object EntityMapper {
    fun toSampleEntity(sample: Sample): SampleEntity {
        return SampleEntity(
            id = sample.id ?: "",
            name = sample.name
        )
    }
}