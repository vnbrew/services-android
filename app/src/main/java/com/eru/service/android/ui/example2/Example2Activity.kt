package com.eru.service.android.ui.example2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import androidx.appcompat.app.AppCompatActivity
import com.eru.service.android.databinding.ActivityExample2Binding
import com.eru.service.android.services.MyBackgroundService
import com.eru.service.android.services.MyForegroundService
import com.eru.service.android.services.MyIntentService

class Example2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityExample2Binding

    private val myHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExample2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bStartBgService.setOnClickListener {
            startBackgroundService()
        }

        binding.bStopBgService.setOnClickListener {
            stopBackgroundService()
        }

        binding.bStartFgService.setOnClickListener {
            startForegroundService()
        }

        binding.bStopFgService.setOnClickListener {
            stopForegroundService()
        }

        binding.bStartIntentService.setOnClickListener {
            startIntentService()
        }
    }

    private fun startBackgroundService() {
        val intent = Intent(this, MyBackgroundService::class.java)
        startService(intent)
    }

    private fun stopBackgroundService() {
        val intent = Intent(this, MyBackgroundService::class.java)
        stopService(intent)
    }

    fun startIntentService() {
        val myResultReceiver = MyIntentResultReceiver(null)
        val intent = Intent(this, MyIntentService::class.java)
        intent.putExtra("intent_result_receiver", myResultReceiver)
        startService(intent)
    }

    private fun startForegroundService() {
        val intent = Intent(this, MyForegroundService::class.java)
        intent.action = MyForegroundService.ACTION_START_FOREGROUND_SERVICE
        startService(intent)
    }

    private fun stopForegroundService() {
        val intent = Intent(this, MyForegroundService::class.java)
        intent.action = MyForegroundService.ACTION_STOP_FOREGROUND_SERVICE
        startService(intent)
    }

    inner class MyIntentResultReceiver(handler: Handler?) : ResultReceiver(handler) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            if (resultCode == 15 && resultData != null) {
                val result = resultData.getString("rr_result")
                myHandler.post {
                    binding.tvIntentServiceResult.text = result
                }
            }
            super.onReceiveResult(resultCode, resultData)
        }
    }

    private val myBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val result = intent?.getStringExtra("br_result")
            myHandler.post {
                binding.tvIntentServiceResult.text = result
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Registering BroadcastReceiver
        val myIntentFilter = IntentFilter()
        myIntentFilter.addAction("action.service.broadcast.receive")
        registerReceiver(myBroadcastReceiver, myIntentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(myBroadcastReceiver)
    }
}