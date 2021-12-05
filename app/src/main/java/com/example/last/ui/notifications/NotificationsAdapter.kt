package com.example.last.ui.notifications

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.last.R
import com.example.last.ROOM.schedule.schedule

class NotificationsAdapter(context: Context, fragment: NotificationsFragment): RecyclerView.Adapter<NotificationsAdapter.PagerViewHolder>() {
    private val context = context
     val schedule = ArrayList<schedule>()
    private val fragment = fragment

    fun setItem(items: List<schedule>){
        this.schedule.clear()
        this.schedule.addAll(items)
        Log.i("notiItem; setItem", this.schedule.toString()) //값들어감
    }
    fun getItem():ArrayList<schedule>{

        Log.i("notiItem; getItem", this.schedule.toString()) //값들어감
        return schedule
    }


    fun setRate(): ArrayList<Float> {
        val temp = ArrayList<Float>()
        for (item in schedule) {
            temp.add(item.rating)

            if(schedule.isEmpty()){
                break
            }
        }

        return temp

    }

    inner class PagerViewHolder(view: View): RecyclerView.ViewHolder(view){
        val item = this@NotificationsAdapter.schedule

        val image = view.findViewById<ImageView>(R.id.VPIV1)
        private val layout = view.findViewById<LinearLayout>(R.id.viewPagerId)
        fun bind(position: Int){
            image.imageTintList = ColorStateList.valueOf(Color.parseColor("#FFB300"))


            layout.setOnClickListener {
                when(position) {
                    0 -> {
                        //웹뷰 띄우기 - 구글
                        getSearch(0)
                    }
                    1 -> {
                        //네이버 띄우기
                        getSearch(1)
                    }
                    else -> {
                        //x
                    }
                }
            }




        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationsAdapter.PagerViewHolder {
        //        val layoutInflater = LayoutInflater.from(context).inflate(R.layout.scheduleitem, parent, false)
        //        //val binding = ScheduleitemBinding.inflate(layoutInflater)
        //        val viewHolder = homeViewHolder(layoutInflater)
        //val layoutInflater = LayoutInflater.from(context).inflate(R.layout.viewpageritem1, parent, false)
        val layoutInflater = LayoutInflater.from(context).inflate(R.layout.viewpageritem1, parent, false)

        val viewHolder = PagerViewHolder(layoutInflater)
        return viewHolder
    }

    override fun onBindViewHolder(holder: NotificationsAdapter.PagerViewHolder, position: Int) {
        holder.bind(position%2)
        when(position%2) {
            0 -> holder.image.setImageResource(R.drawable.ic_baseline_manage_search_24)
            else -> holder.image.setImageResource(R.drawable.ic_baseline_map_24)
            //else -> holder.image.setImageResource(R.drawable.ic_baseline_access_alarm_24)
        }


    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }


    fun longonclick(){
        Toast.makeText(context, "롱클릭", Toast.LENGTH_SHORT).show()
    }

    fun onclick(){
        Toast.makeText(context, "클릭", Toast.LENGTH_SHORT).show()

    }

    fun getSearch(flag:Int){
        when(flag){
            0 -> fragment.google()
            1 -> fragment.naver()

            else -> fragment.alarm()
        }
    }




}