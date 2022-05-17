package com.eru.service.android.cat

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

interface IGPSService {
    fun getName(): String
    fun getCurrentGPS(): Pair<Int, Int>
}

class GPSService : Service(), IGPSService {

    private val gpsManager: ICatManager = CatManager(GPSManager())

    private val gpsBinder = GPSBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return gpsBinder
    }

    inner class GPSBinder : Binder() {
        fun getGPSService(): IGPSService = this@GPSService
    }

    override fun getName(): String {
        return gpsManager.getName()
    }

    override fun getCurrentGPS(): Pair<Int, Int> {
        return gpsManager.getCatCurrentGPS()
    }
}