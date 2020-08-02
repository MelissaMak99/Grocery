package com.example.navigation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.widget.Toast


class MyAlarmReceiver: BroadcastReceiver() {
    var i = 0
    override fun onReceive(context: Context, intent: Intent) {
        //lateinit var notificationManager: NotificationManager
        val x = intent.getStringExtra("name")
        lateinit var notificationChannel : NotificationChannel
        lateinit var builder : Notification.Builder
        val channelId = "com.example.navigation"
        val description = "My notification"


        val intent = Intent(context,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(context,i,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        //notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Configure the notification channel.

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = NotificationChannel(channelId, description,
                        NotificationManager.IMPORTANCE_HIGH)
                    notificationChannel.enableLights(true)
                    notificationChannel.lightColor = Color.GRAY
                    notificationChannel.enableVibration(true)
                    notificationManager.createNotificationChannel(notificationChannel)
                    builder = Notification.Builder(context, channelId)
                        .setContentTitle("Grocery")
                        .setContentText("$x is about to expire")
                        .setSmallIcon(R.drawable.ic_notification)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                }



        else{
                    builder = Notification.Builder(context)
                        .setContentTitle("Grocery")
                        .setContentText("Your product is about to expire")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(pendingIntent)
                }

                notificationManager.notify(i, builder.build())
                i += 1
            }

        }




