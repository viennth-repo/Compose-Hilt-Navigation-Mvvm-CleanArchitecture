package com.viennth.app.demo.presentation.base

import androidx.lifecycle.*
import com.viennth.app.demo.domain.model.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class BaseViewModel: ViewModel() {

    open fun <T> LiveData<T>.post(data: T) = (this as MutableLiveData<T>).postValue(data)

    open fun <T> LiveData<T>.set(data: T) {
        (this as MutableLiveData<T>).value = data
    }

    // coroutines
    private val ioContext = Dispatchers.IO // background context
    private val mainContext = Dispatchers.Main // ui context

    override fun onCleared() {
        super.onCleared()
    }

    private val runningJobs = MutableLiveData<Int>().also { it.value = 0 }

    private val _loadingJobs: LiveData<Boolean> = Transformations.map(runningJobs) { it > 0 }

    val loading = _loadingJobs

    @Synchronized
    private fun jobStarted() {
        val running = runningJobs.value ?: 0
        runningJobs.value = (running + 1)
    }

    @Synchronized
    private fun jobEnded() {
        var running = runningJobs.value ?: 0
        if (running > 0) {
            running--
        }
        runningJobs.value = running
    }

    protected fun onIO(block: suspend (scope: CoroutineScope) -> Unit) {
        viewModelScope.launch(ioContext) {
            backOnMain {
                jobStarted()
            }
            block(this)
            backOnMain {
                jobEnded()
            }
        }
    }

    protected fun onMain(
        showLoading: Boolean = true,
        block: suspend (scope: CoroutineScope) -> Unit
    ) {
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

    protected suspend inline fun backOnMain(crossinline block: () -> Unit) {
        withContext(Dispatchers.Main) { block() }
    }

    open suspend fun <T> Resource<T>.collectResource(onSuccess: (data: T) -> Unit) =
        (this@collectResource).execute().collectResource(onSuccess)

    open fun <T> Resource<T>.execute(): Flow<Resource<T>> = flow {
        emit(this@execute)
    }.flowOn(Dispatchers.IO)

    open suspend fun <T> Flow<Resource<T>>.collectResource(onSuccess: (data: T) -> Unit) {
        (this@collectResource).collect { r ->
            when (r) {
                is Resource.Success -> {
                    if (r.data != null) {
                        onSuccess(r.data)
                    } else {
                        Timber.d("Data is null")
                    }
                }
                is Resource.Error -> Timber.d("${r.resourceError}")
            }
        }
    }
}
