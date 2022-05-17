package com.eru.service.android.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eru.service.android.R
import com.eru.service.android.cat.GPSService
import com.eru.service.android.cat.IGPSService
import com.eru.service.android.cat.IServiceConnectionManager
import com.eru.service.android.cat.ServiceConnectionListener
import com.eru.service.android.cat.ServiceConnectionManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IMainAction {

    companion object {
        const val LOG_FILTER = "MainActivity: "
    }

    private lateinit var gpsService: IGPSService
    private var isBound: Boolean = false

    private val serviceConnectionManager: IServiceConnectionManager = ServiceConnectionManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("$LOG_FILTER onCreate")
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        setServiceConnectionListener()
    }

    private fun setServiceConnectionListener() {
        serviceConnectionManager.setServiceConnectionListener(object : ServiceConnectionListener {
            override fun onServiceConnected(service: IGPSService) {
                gpsService = service
                isBound = true
            }

            override fun onServiceDisconnected() {
                isBound = false
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Timber.i("GPSService onStart bindService")
        Intent(this, GPSService::class.java).also {
            bindService(
                it,
                serviceConnectionManager.getServiceConnection(),
                Context.BIND_AUTO_CREATE
            )
        }
    }

    override fun onStop() {
        super.onStop()
        Timber.i("GPSService onStop")
        if (isBound) {
            Timber.i("GPSService onDestroy unbindService")
            unbindService(serviceConnectionManager.getServiceConnection())
            isBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        if (isBound) {
//            Timber.i("GPSService onDestroy unbindService")
//            unbindService(serviceConnectionManager.getServiceConnection())
//            isBound = false
//        }
    }

    override fun showCurrentGPS() {
        val catName = gpsService.getName()
        val catPos = gpsService.getCurrentGPS()
        Timber.i("GPSService $catName at (${catPos.first},${catPos.second})")
    }
}

interface IMainAction {
    fun showCurrentGPS()
}