package com.example.last.ui.dashboard

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.last.R
import com.example.last.ROOM.memo.memo
import com.example.last.databinding.MemocmdialogBinding
import com.example.last.databinding.MemordlayoutBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class customDialog(context: Context, viewModel: DashboardViewModel) { //date 없어도될듯? , date:String
    private val imDialog = Dialog(context)
    private val drDialog = Dialog(context)

    private var calendar = Calendar.getInstance()

    private val context = context
    //private val date = date
    private val viewModel = viewModel

    lateinit var binding: MemordlayoutBinding
    lateinit var _binding: MemocmdialogBinding


    @RequiresApi(Build.VERSION_CODES.O)
    fun showDRialog(item:memo){
        binding = MemordlayoutBinding.inflate(drDialog.layoutInflater)
        drDialog.setContentView(binding.root)
        drDialog.setCancelable(true)

        drDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        binding.rmemoTitle.text = item.Title
        binding.rmemoDate.text = item.Date
        binding.rmemoTime.text = item.Time

        binding.rmemoContent.text = item.Content

        binding.rmemoDelete.setOnClickListener {
            viewModel.delete(item)

            Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_SHORT).show()
            drDialog.dismiss()
        }

        binding.updateImage.setOnClickListener {
            //showIMDialog()
            _binding = MemocmdialogBinding.inflate(imDialog.layoutInflater)
            imDialog.setContentView(_binding.root)
            imDialog.setCancelable(true)
            _binding.imemoConfirm.text = "수정"
            imDialog.show()

            val title = _binding.imemoTitle
            val content = _binding.imemoContent
            val date = LocalDate.now()//dateFormat.format(calendar.time)
            val time = getTime()

            _binding.imemoConfirm.setOnClickListener {
                viewModel.update(memo(0, title.text.toString(), content.text.toString(), date.toString(), time.toString()))

                Toast.makeText(context, "수정되었습니다", Toast.LENGTH_SHORT).show()

                binding.rmemoTitle.text = title.text.toString()
                binding.rmemoContent.text = content.text.toString()

                imDialog.dismiss()
            }

            _binding.imemoCancel.setOnClickListener {
                imDialog.dismiss()
            }
        }

        binding.notificationImage.setOnClickListener {
            //타임피커로 시간받아서 그 때 띄워주기 - 기본 알람과 같을 수 있음

            //val timepicker = timepicker()
            //timepicker.show()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val min = calendar.get(Calendar.MINUTE)

            val listener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                //val cal = Calendar.getInstance() // 새로 만들기
                var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
                var intent = Intent(context, DashboardReceiver::class.java)
                intent.putExtra("id", item.memoID)
                intent.putExtra("title", item.Title)
                intent.putExtra("content", item.Content)
                @SuppressLint("UnspecifiedImmutableFlag")
                val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0) //만들어져있으면 다시 업데이트


                val clone = calendar.clone() as Calendar



                clone.set(Calendar.HOUR_OF_DAY, hourOfDay)
                clone.set(Calendar.MINUTE, minute)
                clone.set(Calendar.SECOND, 0)
                clone.set(Calendar.MILLISECOND, 0)

                if (clone.compareTo(calendar) <= 0) {
                    clone.add(Calendar.DATE, 1);
                }

                //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis)

                alarmManager?.set(AlarmManager.RTC_WAKEUP, clone.timeInMillis, pendingIntent)



                //alarmManager?.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis , pendingIntent)
                //showNoti(item)


            }

            val timePicker = TimePickerDialog(context,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                listener,
                hour, min, true)


            /*timePicker.setButton(
                TimePickerDialog.BUTTON_POSITIVE,
                "확인",
                DialogInterface.OnClickListener { dialog, which ->
                    //Toast.makeText(context, "positive", Toast.LENGTH_SHORT).show() //x
                    //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis)

                    //alarmManager?.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)


                }
            )*/


            timePicker.setButton(
                TimePickerDialog.BUTTON_NEGATIVE,
                "취소",
                DialogInterface.OnClickListener { dialog, which ->
                    //Toast.makeText(context, "negative", Toast.LENGTH_SHORT).show() //o
                    dialog.dismiss()

                }

            )
            timePicker.setTitle("시간 설정")
            timePicker.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            timePicker.show()



            //showNoti(item)

        }


        binding.rmemoConfirm.setOnClickListener {
            drDialog.dismiss()


        }



        drDialog.show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNoti(item: memo){
        val builder = NotificationCompat.Builder(context, "default")

        builder.setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
        builder.setContentTitle(item.Title)
        builder.setContentText(item.Content)
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

        manager.notify(item.memoID, builder.build())


    }

    fun removeNoti(item: memo){
        //NotificationCompat.from(this).cancel(item.title)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showIMDialog(){
        _binding = MemocmdialogBinding.inflate(imDialog.layoutInflater)
        imDialog.setContentView(_binding.root)
        imDialog.setCancelable(true)


        /*
            @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") val memoID:Int, 아이템 아이디
            @ColumnInfo(name= "title") val Title:String?, 아이템 제목
            @ColumnInfo(name= "content") val Content:String?, 아이템 내용
            @ColumnInfo(name= "date") val Date:String,
            @ColumnInfo(name = "time") val Time:String,
         */
        val title = _binding.imemoTitle
        val content = _binding.imemoContent
        val date = LocalDate.now()//dateFormat.format(calendar.time)
        val time = getTime()
        //val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)

            /*
                    val dateformat = SimpleDateFormat("yyyy MM", Locale.KOREAN)

        this._binding!!.recyclerviewDashboardLayout.current.text = dateformat.format(calendar.time)
             */
            //fragBind.recyclerviewDashboardLayout.current.text.toString() + " " + expDate.text.toString()
        _binding.imemoConfirm.setOnClickListener {
            viewModel.insert(memo(0, title.text.toString(), content.text.toString(), date.toString(), time.toString()))

            Toast.makeText(context, "입력되었습니다", Toast.LENGTH_SHORT).show()

            imDialog.dismiss()

        }

        _binding.imemoCancel.setOnClickListener { imDialog.dismiss() }




        imDialog.show()

    }
    fun getTime():String{
        val now = System.currentTimeMillis()
        val timeformat = SimpleDateFormat("HH:mm:ss", Locale("ko", "KR"))
        val time = timeformat.format(now)
        return time
    }

}