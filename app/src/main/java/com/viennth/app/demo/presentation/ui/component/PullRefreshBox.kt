package com.viennth.app.demo.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshBox(
    state: PullRefreshState,
    refreshing: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .pullRefresh(state)
            .fillMaxSize()
    ) {
        if (!refreshing) {
            content()
        } else {
            CircularIndeterminateProgressBar(isDisplayed = true, 0.4f)
        }
        PullRefreshIndicator(false, state, Modifier.align(Alignment.TopCenter))
    }
}
