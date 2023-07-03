package com.viennth.app.demo.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.viennth.app.demo.domain.model.Sample
import com.viennth.app.demo.domain.usecase.SampleUseCase
import com.viennth.app.demo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SampleViewModel @Inject constructor(
    @Named("V1")
    private val sampleUseCase: SampleUseCase
): BaseViewModel() {
    private val _samples: MutableState<List<Sample>> = mutableStateOf(emptyList())
    val sample: State<List<Sample>> = _samples

    fun getSamples() {
        makeApiCall({
            sampleUseCase.getSamples()
        }) { samples ->
            _samples.value = samples
            onIO {
                sampleUseCase.insertSamples(samples)
            }
        }
//        onMain {
//            sampleUseCase.getSamples().collectResource { samples ->
//                _samples.value = samples
//                onIO {
//                    sampleUseCase.insertSamples(samples)
//                }
//            }
//        }
    }
}
