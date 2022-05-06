package com.eru.service.android.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.eru.service.android.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class ForegroundService : Service() {

    companion object {
        const val LOG_FILTER = "ForegroundService: "
        private const val NOTIFICATION_ID = 999 //should
        private const val ACTION = "action"
        const val ACTION_START_FOREGROUND_SERVICE = "action_start_foreground_service"
        const val ACTION_STOP_FOREGROUND_SERVICE = "action_stop_foreground_service"

        fun createIntent(context: Context, action: String) =
            Intent(context, ForegroundService::class.java).apply {
                putExtra(ACTION, action)
            }
    }

    @Inject
    lateinit var serviceManager: INotificationManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Timber.i("$LOG_FILTER onCreate")
        serviceManager.createChannel(context = this)
    }

    override fun onDestroy() {
        Timber.i("$LOG_FILTER onDestroy")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        super.onStartCommand(intent, flags, startId)
        val action = intent?.getStringExtra(ACTION)
        Timber.i("$LOG_FILTER onStartCommand action: $action")
        when (action) {
            ACTION_START_FOREGROUND_SERVICE -> {
                startForegroundService()
            }

            ACTION_STOP_FOREGROUND_SERVICE -> {
                stopForegroundService()
            }
        }
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
//        stopForegroundService() // or AndroidManifest
    }

    private fun startForegroundService() {
        val message = applicationContext.getString(R.string.message_foreground_service)
        val notification = serviceManager.getNotification(applicationContext, message)
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun stopForegroundService() {
        stopForeground(true)
        stopSelf()
    }
}