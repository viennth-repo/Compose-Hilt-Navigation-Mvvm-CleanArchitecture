package com.viennth.app.demo.di

import com.viennth.app.demo.data.datasource.local.LocalDataSourceImpl
import com.viennth.app.demo.data.repository.IApiService
import com.viennth.app.demo.data.repository.ILocalDataSource
import com.viennth.app.demo.data.repository.SampleRepository
import com.viennth.app.demo.domain.abstraction.ISampleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {

    @Provides
    fun provideSampleRepository(
        repo: SampleRepository
    ): ISampleRepository = repo
}