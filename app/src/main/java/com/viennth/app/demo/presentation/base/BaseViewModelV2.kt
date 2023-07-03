package com.viennth.app.demo.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModelV2 : ViewModel() {

    private val ioContext = Dispatchers.IO
    private val mainContext = Dispatchers.Main

    private val _runningJobsState =  MutableStateFlow(0)
    val loadingState = _runningJobsState.map { it > 0 }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    @Synchronized
    private fun jobStarted() {
        _runningJobsState.getAndUpdate { count -> count + 1 }
    }

    @Synchronized
    private fun jobEnded() {
        _runningJobsState.getAndUpdate { count -> count - 1 }
    }

    private suspend inline fun backOnMain(crossinline block: () -> Unit) {
        withContext(Dispatchers.Main) { block() }
    }

    fun onIO(showLoading: Boolean = true, block: suspend (scope: CoroutineScope) -> Unit) {
        viewModelScope.launch(ioContext) {
            if (showLoading) {
                jobStarted()
            }
            block(this)
            if (showLoading) {
                jobEnded()
            }
        }

    }

    fun onMain(showLoading: Boolean = true, block: suspend (scope: CoroutineScope) -> Unit) {
        viewModelScope.launch(mainContext) {
            if (showLoading) {
                jobStarted()
            }
            block(this)
            if (showLoading) {
                jobEnded()
            }
        }
    }
}
