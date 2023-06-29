package com.viennth.app.demo.data.datasource.remote.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class ApiKeyQueryInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val originalUrl = request.url
        val urlBuilder = originalUrl.newBuilder()
        urlBuilder.addQueryParameter("api-key", "API_KEY")

        val builder = request.newBuilder().url(urlBuilder.build())
        return chain.proceed(builder.build())
    }
}