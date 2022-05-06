package com.eru.service.android.services

import android.content.Context
import androidx.core.content.ContextCompat
import javax.inject.Inject

interface IServiceManager {
    fun startForegroundService(context: Context)
    fun stopForegroundService(context: Context)
}

class ServiceManager @Inject constructor(
) : IServiceManager {

    companion object {
        const val LOG_FILTER = "ServiceManager: "
    }

    override fun startForegroundService(context: Context) {
        val foregroundServiceIntent = ForegroundService.createIntent(
            context,
            ForegroundService.ACTION_START_FOREGROUND_SERVICE
        )
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }

    override fun stopForegroundService(context: Context) {
        val foregroundServiceIntent = ForegroundService.createIntent(
            context,
            ForegroundService.ACTION_STOP_FOREGROUND_SERVICE
        )
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }
}