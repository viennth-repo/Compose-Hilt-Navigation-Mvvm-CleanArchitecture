package com.viennth.app.demo.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.viennth.app.demo.presentation.location.LocationService
import com.viennth.app.demo.presentation.ui.screen.maintab.MainTabScreen
import com.viennth.app.demo.presentation.ui.theme.ComposeAppTheme
import com.viennth.app.demo.presentation.viewmodel.SampleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<SampleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    setContent {
                        MainTabScreen()
                    }
                }
            }
        }
//        viewModel.getSamples()
    }

    override fun onStart() {
        super.onStart()
        LocationService.moveToBackground(this)
    }

    override fun onStop() {
        super.onStop()
        LocationService.moveToForeground(this)
    }
}
