package com.viennth.app.demo.presentation.ui.screen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

@Composable
fun DisposableEffectWithLifeCycle(
    onResume: () -> Unit,
    onPause: () -> Unit,
) {

    // Safely update the current lambdas when a new one is provided
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val currentOnResume by rememberUpdatedState(onResume)
    val currentOnPause by rememberUpdatedState(onPause)

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
                }
                Lifecycle.Event.ON_RESUME -> {
                    Timber.d("=>> ON_RESUME")
                    currentOnResume()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Timber.d("=>> ON_PAUSE")
                    currentOnPause()
                }
                Lifecycle.Event.ON_STOP -> {
                    Timber.d("=>> ON_STOP")
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
