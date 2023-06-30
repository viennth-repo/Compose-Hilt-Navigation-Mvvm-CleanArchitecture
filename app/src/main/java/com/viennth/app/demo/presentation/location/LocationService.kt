package com.viennth.app.demo.presentation.location

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.viennth.app.demo.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LocationService: Service() {
    //Coroutine
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    //Timer
    private var timerCounter = 0
    private lateinit var timer: Timer

    private lateinit var locationHelper: LocationHelper
    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent?): IBinder? = null

    private var action = ""

    override fun onCreate() {
        super.onCreate()
        initTimer()
        notificationManager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        createNotificationChanel()
        startForeground(
            NOTIFICATION_ID,
            createNotification("Starting ...").build()
        )
        locationHelper = LocationHelper(this) {
            val content = "Lat: ${it.latitude}, Long: ${it.longitude}\nTime: ${Util.formatSeconds(timerCounter)}"
            Timber.d("=>> notify: $content")
            if (action == ACTION_MOVE_TO_FOREGROUND) {
                notificationManager.notify(
                    NOTIFICATION_ID,
                    createNotification(content).build()
                )
            }
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (this::locationHelper.isInitialized) {
            locationHelper.requestLocationUpdates()
        }
        action = intent?.getStringExtra(EXTRA_ACTION) ?: ""
        when (action) {
            ACTION_MOVE_TO_FOREGROUND -> moveToForeground()
            ACTION_MOVE_TO_BACKGROUND -> moveToBackground()
            else -> stopService(this@LocationService)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_DETACH)
        } else {
            stopForeground(true)
        }
        if (this::locationHelper.isInitialized) {
            locationHelper.onDestroy()
        }
        if (this::timer.isInitialized) {
            timer.cancel()
        }
        job.cancel()

    }

    private fun initTimer() {
        timer = Timer().apply {
            val interval = TimeUnit.SECONDS.toMillis(SCHEDULE_INTERVAL)
            scheduleAtFixedRate(
                object : TimerTask() {
                    override fun run() {
                        timerCounter ++
                        Timber.d("=>> Duration: ${Util.formatSeconds(timerCounter)}")
                        sendBroadcast(
                            Intent().apply {
                                action = "LOCATION_UPDATE"
                                putExtra("LOCATION_UPDATE_TIME", Util.formatSeconds(timerCounter))
                            }
                        )
                    }
                },0,TimeUnit.SECONDS.toMillis(1)
            )
            scheduleAtFixedRate(
                object : TimerTask() {
                    override fun run() {
                        // Upload data to back-end, save to database, etc
                        scope.launch {
                            Timber.d(
                                "=>> Do something each ${interval}ms on worker thread ${Thread.currentThread().name}"
                            )
                        }
                    }
                }, interval, interval
            )
            val delay = TimeUnit.MINUTES.toMillis(2)
            schedule(object : TimerTask() {
                override fun run() {
                    Timber.d("=>> Do something after $delay")
                    stopService(this@LocationService)
                }
            }, delay)
        }
    }

    private fun createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_NONE
            )
            channel.lightColor = Color.BLUE
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
//            val notificationManager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(content: String = ""): NotificationCompat.Builder =
        NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Location Service")
            .setContentText(content)
            .setPriority(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    NotificationManager.IMPORTANCE_MAX
                } else {
                    Notification.PRIORITY_MAX
                }
            )
            .setDefaults(0)
            .setSound(null)
            .setAutoCancel(false)
            .setOnlyAlertOnce(true)
            .setCategory(Notification.CATEGORY_SERVICE)

    private fun moveToForeground() {
        Timber.d("=>> moveToForeground")
        startForeground(
            NOTIFICATION_ID,
            createNotification("Starting ...").build()
        )
    }

    private fun moveToBackground() {
        Timber.d("=>> moveToBackground")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_DETACH)
        } else {
            stopForeground(true)
        }

    }

    companion object {
        private const val ACTION_MOVE_TO_FOREGROUND = "ACTION_MOVE_TO_FOREGROUND"
        private const val ACTION_MOVE_TO_BACKGROUND = "ACTION_MOVE_TO_BACKGROUND"
        private const val EXTRA_ACTION = "EXTRA_ACTION"

        private const val NOTIFICATION_CHANNEL_ID = "demo-app-notification-channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Notification Location Service"
        private const val NOTIFICATION_ID = 1001

        private const val SCHEDULE_INTERVAL = 30L

        fun moveToBackground(context: Context) {
            if (Util.isMyServiceRunning(LocationService::class.java, context)) {
                startLocationService(context, ACTION_MOVE_TO_BACKGROUND)
            }
        }

        fun moveToForeground(context: Context) {
            if (Util.isMyServiceRunning(LocationService::class.java, context)) {
                startLocationService(context, ACTION_MOVE_TO_FOREGROUND)
            }
        }

        fun startService(context: Context) {
            if (!Util.isMyServiceRunning(LocationService::class.java, context)) {
                startLocationService(context, ACTION_MOVE_TO_BACKGROUND)
            }
        }

        fun stopService(context: Context) {
            if (Util.isMyServiceRunning(LocationService::class.java, context)) {
                context.applicationContext.stopService(
                    Intent(context.applicationContext, LocationService::class.java)
                )
            }
        }

        private fun startLocationService(context: Context, action: String? = null) {
            ContextCompat.startForegroundService(
                context.applicationContext,
                Intent(
                    context.applicationContext,
                    LocationService::class.java
                ).apply {
                    putExtra(EXTRA_ACTION, action)
                }
            )
        }
    }
}
