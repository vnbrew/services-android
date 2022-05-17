package com.eru.service.android.fish.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.eru.service.android.fish.R
import timber.log.Timber

class MyForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        Timber.i(TAG, "onCreate() executed in thread " + Thread.currentThread().name)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.i(TAG, "onStartCommand() executed in thread " + Thread.currentThread().name)
        if (intent != null) {
            when (intent.action) {
                ACTION_START_FOREGROUND_SERVICE -> startMyForegroundService()

                ACTION_STOP_FOREGROUND_SERVICE -> stopMyForegroundService()
            }
        }
        return START_STICKY
    }

    private fun startMyForegroundService() {
        Timber.i(
            TAG,
            "startMyForegroundService() executed in thread " + Thread.currentThread().name
        )
        val notificationIntent = Intent()
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "my_channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("my_channel_id", name, importance)
            val notificationManager =
                getSystemService<NotificationManager>(NotificationManager::class.java!!)
            notificationManager!!.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, "my_channel_id")
            .setContentTitle("Foreground Service Demo")
            .setContentText("Foreground Service Demo Content")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)

        val notification = builder.build()

        startForeground(10, notification)

        var counter = 0

        while (counter <= 10) {
            Timber.i(TAG, "counter $counter")
            Thread.sleep(1000)
            counter++
        }
    }

    private fun stopMyForegroundService() {
        Timber.i(TAG, "stopMyForegroundService() executed in thread " + Thread.currentThread().name)
        stopForeground(true)
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i(TAG, "onDestroy() executed in thread " + Thread.currentThread().name)
    }

    companion object {
        private val TAG = MyForegroundService::class.java.simpleName
        const val ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE"
        const val ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE"
    }
}