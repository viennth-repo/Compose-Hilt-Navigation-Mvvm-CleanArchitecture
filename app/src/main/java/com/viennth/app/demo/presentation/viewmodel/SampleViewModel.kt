package com.viennth.app.demo.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import com.viennth.app.demo.domain.model.Resource
import com.viennth.app.demo.domain.model.Sample
import com.viennth.app.demo.domain.usecase.SampleUseCase
import com.viennth.app.demo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val sampleUseCase: SampleUseCase
): BaseViewModel() {
    private val _samples: MutableState<List<Sample>> = mutableStateOf(emptyList())
    val sample: State<List<Sample>> = _samples

    fun getSamples() {
        onIO {
            sampleUseCase.getSamples()
                .collect { r ->
                    when (r) {
                        is Resource.Success -> {
                            backOnMain {
                                _samples.value = r.data ?: emptyList()
                            }
                        }
                        is Resource.Error -> Timber.d("${r.resourceError}")
                        is Resource.Loading -> Timber.d("Loading")
                    }
                }
        }
    }
}
