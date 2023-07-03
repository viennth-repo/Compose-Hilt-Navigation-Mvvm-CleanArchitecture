package com.viennth.app.demo.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.viennth.app.demo.domain.model.Sample
import com.viennth.app.demo.presentation.base.BaseViewModelV2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SampleViewModelV2 @Inject constructor(): BaseViewModelV2() {

    private val _samples: MutableState<List<Sample>> = mutableStateOf(emptyList())
    val sample: State<List<Sample>> = _samples

    fun getSamples() {
        getSamplesApi1()
        getSamplesApi2()
        getSamplesApi3()
    }


    fun getSamplesApi1() {
        onMain {
            delay(5000)
            Timber.d("=>> getSamplesApi1")
        }
    }

    fun getSamplesApi2() {
        onMain {
            delay(2000)
            Timber.d("=>> getSamplesApi2")
        }
    }

    fun getSamplesApi3() {
        onMain {
            delay(1000)
            Timber.d("=>> getSamplesApi3")
        }
    }
}
