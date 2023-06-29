package com.viennth.app.demo.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.viennth.app.demo.BuildConfig
import com.viennth.app.demo.data.datasource.remote.Api
import com.viennth.app.demo.data.datasource.remote.ApiServiceImpl
import com.viennth.app.demo.data.repository.IApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {
    @Provides
    @Singleton
    @ApiUrl
    internal fun provideApiUrl() = API_URL

    @Provides
    @Singleton
    internal fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
            }
            connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofitBuilder(
        @ApiUrl apiUrl: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)

    @Provides
    @Singleton
    internal fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder
    ): Retrofit = retrofitBuilder.build()

    @Provides
    @Singleton
    internal fun provideApi(
        retrofit: Retrofit
    ) = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    internal fun provideApiService(
        api: Api
    ): IApiService = ApiServiceImpl(api)

    companion object {
        private const val API_URL = "http://10.0.2.2:3001"
        private const val CONNECTION_TIMEOUT = 30L
    }
}