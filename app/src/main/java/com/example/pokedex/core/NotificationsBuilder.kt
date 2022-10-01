package com.example.pokedex.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.pokedex.ui.MainActivity
import com.example.pokedex.ui.NavigationActivity
import com.example.pokedex.ui.explore.ExplorerFragment
import com.example.pokedex.ui.home.HomeFragment

object NotificationsBuilder {
    private const val MY_CHANNEL_ID = "myChannel"

    fun createChannel(fragment: Fragment){

        val channel = NotificationChannel(
            MY_CHANNEL_ID,
            "generalChannel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "It is the general channel of this app"
        }

        val notificationManager: NotificationManager = fragment.activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    fun createSimpleNotifications(context: Context){

        val intent = Intent(context, NavigationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(context, MY_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_delete)
            .setContentTitle("POKEMON FOUND!")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Look at the pokemon that you was found in your way"))
            .setContentText("You have found a pokemon")
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)){
            notify(1, builder.build())
        }
    }
}