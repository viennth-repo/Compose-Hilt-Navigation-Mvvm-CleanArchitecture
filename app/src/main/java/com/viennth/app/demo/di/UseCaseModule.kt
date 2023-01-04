package com.viennth.app.demo.di

import com.viennth.app.demo.domain.usecase.SampleUseCase
import com.viennth.app.demo.domain.usecase.SampleUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class UseCaseModule {

    @Provides
    @Singleton
    fun provideSampleUseCase(
        sampleUseCaseImpl: SampleUseCaseImpl
    ): SampleUseCase = sampleUseCaseImpl
}