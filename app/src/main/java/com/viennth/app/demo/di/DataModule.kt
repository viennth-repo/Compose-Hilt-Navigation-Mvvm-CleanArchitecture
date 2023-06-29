package com.viennth.app.demo.di

import android.app.Application
import android.content.Context
import com.viennth.app.demo.data.datasource.local.LocalDataSourceImpl
import com.viennth.app.demo.data.datasource.local.SampleDao
import com.viennth.app.demo.data.datasource.local.SampleRoomDb
import com.viennth.app.demo.data.repository.ILocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal class DataModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(
        sampleDao: SampleDao
    ): ILocalDataSource = LocalDataSourceImpl(sampleDao)

    @Singleton
    @Provides
    fun provideSampleDataBase(
        application: Application
    ) = SampleRoomDb.getDatabase(application)

    @Singleton
    @Provides
    fun provideSampleDao(
        sampleDataBase: SampleRoomDb
    ): SampleDao = sampleDataBase.sampleDao()
}