package com.appsculture.climato.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v4.content.ContextCompat
import com.appsculture.climato.R
import com.appsculture.climato.model.Forecast
import com.appsculture.climato.module.detail.DetailActivity
import javax.inject.Inject

class NotificationProvider @Inject constructor(private val context: Context) {

    companion object {
        const val notificationId = 1200
        const val channelId = "climato_weather_channel"
    }

    fun show(forecast: Forecast) {

        val notificationTitle = forecast.name
        val notificationText = forecast.weather?.main
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = descriptionText
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setAutoCancel(true)
            .setChannelId(channelId)

        val detailIntentForToday = Intent(context, DetailActivity::class.java)

        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addNextIntentWithParentStack(detailIntentForToday)
        val resultPendingIntent = taskStackBuilder
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        notificationBuilder.setContentIntent(resultPendingIntent)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}