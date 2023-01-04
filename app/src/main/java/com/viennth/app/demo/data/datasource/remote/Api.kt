package com.viennth.app.demo.data.datasource.remote

import com.viennth.app.demo.data.datasource.remote.dto.SampleDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface Api {

    @GET("/samples")
    fun getSamplesAsync(): Deferred<ApiResponse<List<SampleDto>>>
}