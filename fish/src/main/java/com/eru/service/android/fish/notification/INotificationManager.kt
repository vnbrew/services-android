package com.eru.service.android.fish.notification

import android.app.Notification
import android.content.Context

interface INotificationManager {
    fun createChannel(context: Context)
    fun getNotification(context: Context, message: String): Notification
}