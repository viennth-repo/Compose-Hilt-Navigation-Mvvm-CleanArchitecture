package com.viennth.app.demo.presentation.ui.screen.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteScreen() {
    val refreshScope = rememberCoroutineScope()
    val threshold = with(LocalDensity.current) { 160.dp.toPx() }

    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(1) }
    var currentDistance by remember { mutableStateOf(0f) }

    val progress = currentDistance / threshold

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        itemCount += 5
        refreshing = false
    }

    fun onPull(pullDelta: Float): Float = when {
        refreshing -> 0f
        else -> {
            val newOffset = (currentDistance + pullDelta).coerceAtLeast(0f)
            val dragConsumed = newOffset - currentDistance
            currentDistance = newOffset
            dragConsumed
        }
    }

    suspend fun onRelease() {
        if (refreshing) return // Already refreshing - don't call refresh again.
        if (currentDistance > threshold) refresh()

        animate(initialValue = currentDistance, targetValue = 0f) { value, _ ->
            currentDistance = value
        }
    }

    Box(Modifier.pullRefresh(::onPull, { onRelease() })) {
        LazyColumn {
            if (!refreshing) {
                items(itemCount) {
                    ListItem { Text(text = "Item ${itemCount - it}") }
                }
            }
        }

        // Custom progress indicator
        AnimatedVisibility(visible = (refreshing || progress > 0)) {
            if (refreshing) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            } else {
                LinearProgressIndicator(progress, Modifier.fillMaxWidth())
            }
        }
    }
}
