package com.viennth.app.demo.presentation.ui.screen.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.viennth.app.demo.presentation.ui.screen.maintab.MainBottomBar

@Composable
fun MainScreen(
    navController: NavHostController,
    content: @Composable () -> Unit = {}
) {
    Scaffold(bottomBar = { MainBottomBar(navController = navController) }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}
