package com.viennth.app.demo.presentation.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class LocationHelper(
    private val context: Context,
    private val callback: ((LocationData) -> Unit)? = null
) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context.applicationContext)

    private val locationRequest =
        LocationRequest
            .Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(INTERVAL))
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(TimeUnit.SECONDS.toMillis(FAST_INTERVAL))
            .setMaxUpdateDelayMillis(TimeUnit.SECONDS.toMillis(MAX_WAIT_TIME))
            .build()

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                val location = locationList.last()
                val dateFormat: DateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault())
                val c = Calendar.getInstance()
                val date = c.time
                val formattedDate = dateFormat.format(date)
//                Timber.d(
//                    "=>> Lat: ${location.latitude}, Long: ${location.longitude}, time: $formattedDate"
//                )
                callback?.invoke(
                    LocationData(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        time = formattedDate
                    )
                )
            }
        }
    }

    private fun checkLocationPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    fun requestLocationUpdates() {
        if (checkLocationPermission()) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    fun onDestroy() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val INTERVAL = 10L
        private const val FAST_INTERVAL = 10L
        private const val MAX_WAIT_TIME = 1L
    }
}

data class LocationData(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val time: String? = null
)
