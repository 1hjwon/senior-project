package com.example.last.ui.dashboard

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.last.R

class DashboardReceiver(): BroadcastReceiver() {
    //private val context = context

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val builder = NotificationCompat.Builder(context!!, "default")

        val id = intent!!.getIntExtra("name", 1)
        val title = intent!!.getStringExtra("title")
        val content = intent!!.getStringExtra("content")


        builder.setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setAutoCancel(true)
        builder.setTicker("123")



        val rington = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM)
        builder.setSound(rington)

        val vibrate = LongArray(4)
        vibrate.set(0, 0)
        vibrate.set(1, 75)
        vibrate.set(2, 150)
        vibrate.set(3, 225)
        Log.i("longarray", vibrate.toString())
        builder.setVibrate(vibrate)

        var manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager




        manager.createNotificationChannel(
            NotificationChannel("default", "채널1", NotificationManager.IMPORTANCE_DEFAULT)
        )

        manager.notify(id, builder.build())

    }
}