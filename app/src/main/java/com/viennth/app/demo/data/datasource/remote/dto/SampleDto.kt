package com.viennth.app.demo.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName
import com.viennth.app.demo.domain.model.Sample

data class SampleDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
)

fun SampleDto.toSample() = Sample(
    id = this.id,
    name = "Sample -> ${this.name}" // Do something
)
