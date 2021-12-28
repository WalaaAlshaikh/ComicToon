package com.example.comictoon.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.comictoon.R
import com.google.android.material.internal.ContextUtils.getActivity

class Notification(val context: Context) {

    val channelId:String="Channel"
    var notificationId=1

    fun createNotificationChannel(name:String,descriptionText:String,id:Int) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.bangers)
            .setContentTitle("Comic Toon")
            .setContentText("Yay you liked this comic :)")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Yay you liked this comic :)"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        /**
          Create the NotificationChannel, but only on API 26+ because the NotificationChannel class is new and not in the support library
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name ="Notification"
            val descriptionText ="channel des"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, builder.build())

            }
        }
    }
}