package com.example.last.ui.dashboard

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class timepicker: DialogFragment(), TimePickerDialog.OnTimeSetListener { //x
    lateinit var alarmManager: AlarmManager
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        alarmManager = (context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager)!!



        val calendar = Calendar.getInstance()

        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var min = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(context, this, hour, min, false)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        cal.set(Calendar.MINUTE, minute)

        var intent = Intent(context, DashboardReceiver::class.java)
        @SuppressLint("UnspecifiedImmutableFlag")
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        ) //만들어져있으면 다시 업데이트


        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)


    }


}