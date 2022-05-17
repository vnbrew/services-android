package com.eru.service.android.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import timber.log.Timber

class MyBackgroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        Timber.i(TAG, "onCreate() executed in thread " + Thread.currentThread().name)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.i(TAG, "onStartCommand() executed in thread " + Thread.currentThread().name)
        // Perform Long Operations
        Thread.sleep(10000)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Timber.i(TAG, "onBind() executed in thread " + Thread.currentThread().name)
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // stopSelf()
        Timber.i(TAG, "onDestroy() executed in thread " + Thread.currentThread().name)
    }

    companion object {
        private val TAG = MyBackgroundService::class.java.simpleName
    }
}