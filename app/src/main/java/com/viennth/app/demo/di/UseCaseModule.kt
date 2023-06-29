package com.viennth.app.demo.di

import com.viennth.app.demo.domain.usecase.SampleUseCase
import com.viennth.app.demo.domain.usecase.SampleUseCaseImpl
import com.viennth.app.demo.domain.usecase.SampleUseCaseV2Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class UseCaseModule {

    @Provides
    @Singleton
    @Named("V1")
    fun provideSampleUseCase(
        sampleUseCaseImpl: SampleUseCaseImpl
    ): SampleUseCase = sampleUseCaseImpl

    @Provides
    @Singleton
    @Named("V2")
    fun provideSampleUseCaseV2(
        sampleUseCaseImpl: SampleUseCaseV2Impl
    ): SampleUseCase = sampleUseCaseImpl
}
