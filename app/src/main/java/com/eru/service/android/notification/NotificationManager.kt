package com.eru.service.android.services

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.eru.service.android.R
import com.eru.service.android.broadcast.AppBroadcastReceiver
import com.eru.service.android.ui.main.MainActivity
import javax.inject.Inject
import timber.log.Timber

interface INotificationManager {
    fun createChannel(context: Context)
    fun getNotification(context: Context, message: String): Notification
}

class NotificationManager @Inject constructor(
) : INotificationManager {

    companion object {
        const val LOG_FILTER = "NotificationManager: "
    }

    /**
     * for API 26+ create notification channels
     */
    override fun createChannel(context: Context) {
        Timber.i("$LOG_FILTER createChannel")
        val channelId = context.packageName + context.getString(R.string.channel_id)
        val channelName = context.packageName + context.getString(R.string.channel_name)
        val channelDescription = context.getString(R.string.channel_description)
        val notificationChannelCompat =
            NotificationChannelCompat.Builder(channelId, NotificationManagerCompat.IMPORTANCE_LOW)
                .setDescription(channelDescription)
                .setName(channelName)
                .setLightsEnabled(true)
                .setShowBadge(true)
                .build()
        NotificationManagerCompat.from(context).createNotificationChannel(notificationChannelCompat)
    }

    // build a persistent notification and return it.
    @RequiresApi(Build.VERSION_CODES.M)
    override fun getNotification(context: Context, message: String): Notification {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val broadcastIntent = Intent(context, AppBroadcastReceiver::class.java).apply {
            putExtra("action_msg", "close")
            putExtra(AppBroadcastReceiver.BROADCAST_ACTION, AppBroadcastReceiver.ACTION_CLOSE)
        }
        val broadcastPendingIntent =
            PendingIntent.getBroadcast(context, 0, broadcastIntent, 0)

        val channelId = context.packageName + context.getString(R.string.channel_id)
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true) //persistent notification!
            .setChannelId(channelId)
            .setContentTitle("Foreground Service") //Title message top row.
            .setContentText(message) //message when looking at the notification, second row
            .setContentIntent(activityPendingIntent)
            .addAction(R.drawable.ic_close, "Close", broadcastPendingIntent)
            .build() //finally build and return a Notification.
    }
}