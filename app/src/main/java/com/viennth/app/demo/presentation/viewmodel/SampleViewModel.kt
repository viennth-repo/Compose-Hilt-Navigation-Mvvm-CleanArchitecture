package com.viennth.app.demo.presentation.viewmodel

import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.usecase.SampleUseCase
import com.viennth.app.demo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val sampleUseCase: SampleUseCase
): BaseViewModel() {
    fun getSamples() {

        onIO {
            sampleUseCase.getSamples()
                .collect { r ->
                    when (r) {
                        is Resource.Success -> Timber.d("${r.data}")
                        is Resource.Error -> Timber.d("${r.resourceError}")
                        is Resource.Loading -> Timber.d("Loading")
                    }
                }
        }
    }
}