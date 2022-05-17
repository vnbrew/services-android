package com.eru.service.android.cat

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

interface IServiceConnectionManager {
    fun setServiceConnectionListener(listener: ServiceConnectionListener)
    fun getServiceConnection(): ServiceConnection
}

interface ServiceConnectionListener {
    fun onServiceConnected(service: IGPSService)
    fun onServiceDisconnected()
}

class ServiceConnectionManager : IServiceConnectionManager {

    private var serviceConnectionListener: ServiceConnectionListener? = null
    override fun setServiceConnectionListener(listener: ServiceConnectionListener) {
        serviceConnectionListener = listener
    }

    override fun getServiceConnection() = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName, service: IBinder) {
            val mService = (service as GPSService.GPSBinder).getGPSService()
            serviceConnectionListener?.onServiceConnected(service = mService)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            serviceConnectionListener?.onServiceDisconnected()
        }
    }
}