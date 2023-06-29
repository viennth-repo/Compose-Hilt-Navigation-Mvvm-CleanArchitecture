package com.viennth.app.demo.presentation.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.viennth.app.demo.presentation.ui.component.CircularIndeterminateProgressBar
import com.viennth.app.demo.presentation.ui.component.PullRefreshBox
import com.viennth.app.demo.presentation.viewmodel.SampleViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun HomeScreen() {
    val viewModel = hiltViewModel<SampleViewModel>()
    LaunchedEffect(true) {
        viewModel.getSamples()
    }
    val loading by viewModel.loading.observeAsState(initial = false)
    val samples by viewModel.sample
    val state = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            viewModel.getSamples()
        }
    )
    PullRefreshBox(
        state = state,
        refreshing = loading
    ) {
        if (samples.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No data")
            }
        } else {
            LazyColumn(Modifier.fillMaxSize()) {
                items(samples) { item ->
                    ListItem { Text(text = item.name ?: "") }
                }
            }

        }
    }
}
