package com.viennth.app.demo.presentation.ui.screen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

@Composable
fun DisposableEffectWithLifeCycle(
    onStart: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onPause: (() -> Unit)? = null,
    onStop: (() -> Unit)? = null,
) {

    // Safely update the current lambdas when a new one is provided
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnResume by rememberUpdatedState(onResume)
    val currentOnPause by rememberUpdatedState(onPause)
    val currentOnStop by rememberUpdatedState(onStop)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for lifecycle events
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    Timber.d("=>> ON_CREATE")
                }
                Lifecycle.Event.ON_START -> {
                    Timber.d("=>> ON_START")
                    currentOnStart?.invoke()
                }
                Lifecycle.Event.ON_RESUME -> {
                    Timber.d("=>> ON_RESUME")
                    currentOnResume?.invoke()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Timber.d("=>> ON_PAUSE")
                    currentOnPause?.invoke()
                }
                Lifecycle.Event.ON_STOP -> {
                    Timber.d("=>> ON_STOP")
                    currentOnStop?.invoke()
                }
                Lifecycle.Event.ON_DESTROY -> {
                    Timber.d("=>> ON_DESTROY")
                }
                else -> {}
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
