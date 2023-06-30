package com.viennth.app.demo.presentation.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.viennth.app.demo.presentation.location.LocationService
import com.viennth.app.demo.presentation.ui.screen.components.DisposableEffectWithLifeCycle
import com.viennth.app.demo.presentation.ui.screen.components.SystemBroadcastReceiver
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val accessBackgroundPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
    ) { isGranted ->
        if (isGranted) {
            LocationService.startService(context = context)
        }
    }
    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) { isGranted ->
        if (isGranted) {
            if (accessBackgroundPermissionState.status.isGranted) {
                LocationService.startService(context = context)
            } else {
                accessBackgroundPermissionState.launchPermissionRequest()
            }
        }
    }
    DisposableEffectWithLifeCycle(
        onResume = {
            Timber.d("=>> current ON_RESUME")
        },
        onPause = {
            Timber.d("=>> current ON_PAUSE")
        }
    )
    var content by remember { mutableStateOf("") }

    SystemBroadcastReceiver("LOCATION_UPDATE") {
        val data = it?.getStringExtra("LOCATION_UPDATE_TIME")
        content = data ?: ""
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column {
            Text(text = content)
            Button(
                onClick = {
                    if (locationPermissionState.status.isGranted){
                        if (accessBackgroundPermissionState.status.isGranted) {
                            LocationService.startService(context = context)
                        } else {
                            accessBackgroundPermissionState.launchPermissionRequest()
                        }
                    } else {
                        locationPermissionState.launchPermissionRequest()
                    }
                }
            ) {
                Text(text = "Start Location Service")
            }
            Button(
                onClick = {
                    LocationService.stopService(context = context)
                }
            ) {
                Text(text = "Stop Location Service")
            }
        }
    }
}
