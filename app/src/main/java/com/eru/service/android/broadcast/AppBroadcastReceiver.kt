package com.eru.service.android.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.eru.service.android.services.IServiceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val BROADCAST_ACTION = "broadcast_action"
        const val ACTION_CLOSE = "action_close"
    }

    @Inject
    lateinit var serviceManager: IServiceManager

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.getStringExtra(BROADCAST_ACTION)) {
            ACTION_CLOSE -> {
                context?.let {
                    serviceManager.stopForegroundService(it)
                }
            }
        }
    }
}