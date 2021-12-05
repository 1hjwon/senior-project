package com.example.last.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.last.R
import com.example.last.ROOM.schedule.schedule
import com.example.last.databinding.FragmentHomeBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class HomeAdapter( context:Context, homeFragment: HomeFragment, homeBinding: FragmentHomeBinding, homeViewModel: HomeViewModel) : RecyclerView.Adapter<HomeAdapter.homeViewHolder>(){
    private var items = ArrayList<schedule>()

    private val calLayout = calLayout()
    private val context = context
    //private val rvBinding: DashboardrecyclerviewBinding? = null
    lateinit var monthTV: TextView //
    //private val context: Context// = context
    private val home:HomeFragment = homeFragment
    private val fragBind:FragmentHomeBinding = homeBinding
    private val viewModel = homeViewModel
    fun setItem(items: List<schedule>){
        this.items.clear()
        this.items.addAll(items)
        Log.i("itemHome; setItem", this.items.toString()) //값들어감
    }

    init {
        //Log.i("itemHome; init", this.items.toString()) //값안들어감

        //getMonth()
        //home = homeFragment
        //fragBind = homeBinding
        //this.context = context
        calLayout.initCal {
            home.refreshCalendar(it)
            //refreshCalendar(it)
        }

        //this.monthTV = findViewById<TextView>(R.id.current).text x
        //this.context = context x
    }

    fun getMonth(){ //x
        //this.monthTV = monthTV.findViewById(R.id.current)
    }

    //HomeAdapter.homeViewHolder
    //inner class homeViewHolder(itemView: View?, private val binding: ScheduleitemBinding): RecyclerView.ViewHolder(binding.root){
    inner class homeViewHolder(view: View): RecyclerView.ViewHolder(view){
        var items : ArrayList<schedule> = this@HomeAdapter.items
        //val context = context

        val expDate = view.findViewById<TextView>(R.id.expDate)
        val monthTV = view.findViewById<TextView>(R.id.current)//
        val checkStar = view.findViewById<ImageView>(R.id.checkStar)
        private val itemLayout2 = view.findViewById<LinearLayout>(R.id.scheduleItemLinearLayout)

        fun bind(position:Int){ //item: schedule

            //
            //val getDate = fragBind.recyclerviewDashboardLayout.current.text.toString()//.plus(expDate.text.toString())
            //val getD = expDate.text.toString()
            //Log.i("GetDate!! : ", getDate) //2021 11 -> 2021 11 xx
            //Log.i("GetPos!! : ", position.toString()) //2021 11

            //var i = 0

            var schedule:schedule
            //getSchedule(getDate) Log.i("schedule!!!!!!!!!!!!!!!1", schedule.toString())
/*
            this.items.iterator().forEach {
                    if (it.date == getDate){
                        schedule = it
                        Log.i("schedule!!!!!!!!!!!!!!!", schedule.toString())
                        return@forEach
                    }
            }
*/



            //
            itemLayout2.setOnClickListener {
                val getDate = fragBind.recyclerviewDashboardLayout.current.text.toString() + " " + expDate.text.toString()//.plus(expDate.text.toString())
                var schedule:schedule

                Log.i("GetDate!! : ", getDate) //2021 11 -> 2021 11 xx; 2021 11 15 나옴

                //val t1 = Toast.makeText(this@HomeAdapter.context, expDate.text.toString()+"?????"+fragBind.recyclerviewDashboardLayout.current.text.toString()+position.toString(), Toast.LENGTH_SHORT)
                //val t2 = Toast.makeText(this@HomeAdapter.context, "t2", Toast.LENGTH_SHORT)
                val dialog2 = customDialog(context, getDate, this@HomeAdapter.viewModel)
                val dialog1 = customDialog(context, getDate, this@HomeAdapter.viewModel)

                if (position >= calLayout.prevTailOffset &&
                    position < calLayout.prevTailOffset + calLayout.currentOffset){
                    if(checkStar.isVisible){
                        //인텐트 만들어서 여기는 수정삭제
                            // 값을 넘기기?

                        dialog1.showRUDDialog(items)


                    } else {
                        //여기는 삽입
                        //getDate,
                        dialog2.showInsertDialog() //리팩토링 빼고 완료

                    }

                }

                Log.i("items!! : ", items.toString()) //4개 다 나옴.... -> 3개


                ////// 해당 값끼리 비교를 하고 그 때만 로그가 찍히게 해야함
                ////// 인텐트로 해당 값들만 넘겨서 표시 해주고 rud를 그 쪽으로 위임시킴
                ////// c의 경우 버튼을 따로 생성


                /*
                if(items.iterator().next().date == getDate ) { //????
                    Log.i("nextDate? ", items.iterator().next().date)
                }*/



/*
                while(items.iterator().hasNext()){
                    Log.i("items.Next!! : ", items.iterator().next().toString())
                }
*//*
                items.iterator().forEach {

                    if (getDate == it.date){
                        schedule = it
                        Log.i("schedule!!! ", schedule.toString())
                        return@forEach
                    }

                }
*/
            }

            itemView.setOnClickListener {
                //itemClick(item)
            }

        }
        fun getSchedule(getDate:String ) { //, schedule: schedule
            //var schedule:schedule
/*
            while (this.items.iterator().hasNext()){
                this.items.iterator().forEach {
                    if (it.date == getDate){
                        this.schedule = it
                        Log.i("schedule!!!!!!!!!!!!!!!", this.schedule.toString())
                        return@forEach
                    }
                }
                Log.i("schedule??????????????", this.schedule.toString())

                break
            }
*/
            /*
            if(this.items.iterator().hasNext() ){
                this.items.iterator().forEach {
                    if(it.date == getDate) {
                        schedule = it
                        Log.i("schedule!!!!!!!!!!!!!!!", schedule.toString())
                        return@forEach

                    }
                }
                //schedule

            }
            */
            //return schedule
        }
        fun setContent(content: String){
            // contentExplicit 에 내용 삽입
            //클릭 안되어있으면 "선택해주세요" 혹은 내용비워두기 ->
            //없으면 "내용없음"
            //있으면 삽입
            //리턴값 -> String
            //string, textbox 용량과 크기 생각하기
           /* val contentTV =   .recyclerviewDashboardLayout.contentExplicit
            when(content){
                is Nothing -> contentTV.text = "내용이 없습니다"
                else -> contentTV.text = content
            }*/
        }
        fun setTime(){
            /*
            [설 명]
                1. System.currentTimeMillis() 사용해서 long 타입 날짜 데이터를 저장합니다
                2. SimpleDateFormat 을 사용해서 출력하고자하는 날짜 및 시간, 요일 형태를 지정할 수 있습니다
             */
            //현재 시간
            //해당 시간
            //형식 -> yyyy 년 MM 월 dd 일 -  HH시 mm분 ss초
            //


          /*  val timeTV = binding1.recyclerviewDashboardLayout.dateExplicit


            timeTV.text =*/
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: homeViewHolder, position: Int) {
        //holder.items = this.items
        holder.bind(position)//(this.items[items.size-1])
        holder.items = this.items
        Log.i("bindViewHolder", items.toString())
        //val schedHolder = holder
        //holder.bind(items[position])
        //schedHolder.bind(this.items[position])


        //expDate
        if ( position % 7 == 0) {
            //일요일 숫자 색 변경 -> pos % calLayout.DAYS_OF_WEEK -> 0%7 = 0
            holder.expDate.setTextColor(Color.parseColor("#c42121"))
        }else if (position % 7 == 6 ){
            //토요일 -> 6%7 = 6
            holder.expDate.setTextColor(Color.parseColor("#0d29a3"))

        }
        else {
            //나머지(평일)
            holder.expDate.setTextColor(Color.parseColor("#60616b"))
        }

        //해당 레이아웃 위치가 이전 달 마지막 날보다 작거나  이번 달 마지막 날보다 클 때
        if (position < calLayout.prevTailOffset ||
            position >= calLayout.prevTailOffset + calLayout.currentOffset) {
            holder.expDate.alpha = 0.3f //
        }
        else {
            holder.expDate.alpha = 1f //12% //여기다하면될듯0
        }


        //값 삽입

        //holder.binding1.scheduleItemLinearLayout.
        holder.expDate.text = calLayout.dateList[position].toString()




        if (position >= calLayout.prevTailOffset &&
            position < calLayout.prevTailOffset + calLayout.currentOffset) {

            val getDate = fragBind.recyclerviewDashboardLayout.current.text.toString() + " " + calLayout.dateList[position].toString()

            val localDate = LocalDate.now()
            val formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy MM dd"))
            val getToday = fragBind.recyclerviewDashboardLayout.current.text.toString() + " 0" + localDate.toString()

            Log.i("local?", localDate.toString())
            for (item in items){
                if(item.date == (getDate)){ //(item.date == (getDate))
                    Log.i("true", "1") //자체가안댐.. 댓음~
                    holder.checkStar.visibility = View.VISIBLE
                    holder.checkStar.setImageResource(R.drawable.ic_baseline_star_24_yellow)
                }
            }


            //if(calLayout.dateList[position].toString().)//10개


            if(getDate.count() == 9) { // 날짜형식 "2021 11 1" -> 9
                if(formatedDate == getToday) {
                    // getResources().getColor(R.color.purple_200)
                    Log.i("getToday!!! ", formatedDate)
                    holder.itemView.setBackgroundColor(context.resources.getColor(R.color.subColor))
                }
            } else {
                if(formatedDate == getDate) {
                    // getResources().getColor(R.color.purple_200)
                    Log.i("getToday!!! ", formatedDate)
                    holder.itemView.setBackgroundColor(context.resources.getColor(R.color.subColor))
                }
            }

            // } 아래에있음


/*
            if(item.date == (getDate)){ //(item.date == (getDate))
                Log.i("true", "1") //자체가안댐..
                holder.checkStar.visibility = View.VISIBLE
                holder.checkStar.setImageResource(R.drawable.ic_baseline_star_24_yellow)
            } else {
                Log.i("false", "2")

                holder.checkStar.visibility = View.INVISIBLE
                holder.checkStar.setImageResource(R.drawable.ic_baseline_star_24)

            }*/
/*
            for (item in items) {
                //Log.i("item?? ", item.toString())
                   Log.i("item.date : ", item.date)
                //Log.i("item.date.size : ", item.date.length.toString())
                Log.i("getDate : ", (getDate).toString())
                //Log.i("getDate size : ", (getDate).length.toString())

                //Log.i("문자열 비교 : ", (item.date == (getDate)).toString()) //트루도나옴

                //val temp = item.date

                if(item.date == (getDate)){ //(item.date == (getDate))
                    Log.i("true", "1") //자체가안댐..
                    holder.checkStar.visibility = View.VISIBLE
                    holder.checkStar.setImageResource(R.drawable.ic_baseline_star_24_yellow)
                } else {
                    Log.i("false", "2")

                    holder.checkStar.visibility = View.INVISIBLE
                    holder.checkStar.setImageResource(R.drawable.ic_baseline_star_24)

                }

            }*/
/*
            while (items.iterator().hasNext()){
                val item = items.iterator().next()

                if(item.date == getDate){
                    holder.checkStar.visibility = View.VISIBLE
                    holder.checkStar.setImageResource(R.drawable.ic_baseline_star_24_yellow)
                } else {
                    holder.checkStar.visibility = View.INVISIBLE
                    holder.checkStar.setImageResource(R.drawable.ic_baseline_star_24)

                }
            }
*/
            /*while (items.iterator().hasNext()){
                Log.i("루프", "1")
                //items.iterator().next().date ==
                items.iterator().next()
            }*/

        }

        //Log.i("바인드뷰홀더!! ", holder.expDate.text.toString())
        /// 여기서 날짜값들을 가지고 값이 있으면 별모양 바꿔주기?

        //calLayout.getMonth //-> frag에서 가져와야할듯
        //Log.i("바인드뷰홀더?? ", calLayout.getMonth.toString()) //2?????
        ///

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): homeViewHolder {
        /*
                val view = LayoutInflater.from(context).inflate(R.layout.memoitem, parent, false)
        val viewHolder = viewHolder1(view)
        //val bind = MemoitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.memoitem, parent, false)

        return viewHolder

         */
        //val view = LayoutInflater.from(context).inflate(R.layout.memoitem, parent, false)
        //val viewHolder = viewHolder1(view)
        val layoutInflater = LayoutInflater.from(context).inflate(R.layout.scheduleitem, parent, false)
        //val binding = ScheduleitemBinding.inflate(layoutInflater)
        val viewHolder = homeViewHolder(layoutInflater)
        //return homeViewHolder(parent, binding, this.context)
        return viewHolder
    }


    override fun getItemCount(): Int {
        //week 7 * row 6
        val num: Int = 7 * 6
        if(items.size < 42){
            return 42
        } else if( items.size == 42){
            return items.size
        } else {
            return 42
        }
        //return items.size
    }

    fun goNextMonth(){
        calLayout.goNext {
            refreshView(it)
        }
    }

    fun goPrevMonth(){
        calLayout.goPrev {
            refreshView(it)
        }
    }

    fun refreshView(calendar: Calendar){
        notifyDataSetChanged()
        refreshCalendar(calendar)


    }

    fun refreshCalendar(calendar: Calendar) {
        //val dateformat = SimpleDateFormat("yyyy MM", Locale.KOREAN)
        //monthTV.text = dateformat.format(calendar.time)
        //Log.i("tv", monthTV.toString())
        //rvBinding!!.dateExplicit.text = dateformat.format(calendar.time)
        home.refreshCalendar(calendar)
        //val dateformat = SimpleDateFormat("yyyy MM", Locale.KOREAN)
        //this._binding!!.recyclerviewDashboardLayout.dateExplicit.text = dateformat.format(calendar.time)
    }

/*
    public fun setOnClickListener(interfaceListener: interfaceListener){
        this.listener = interfaceListener
    }*/
}
/*
Android Studio Arctic Fox | 2020.3.1 Patch 1
Build #AI-203.7717.56.2031.7621141, built on August 8, 2021
Runtime version: 11.0.10+0-b96-7249189 amd64
VM: OpenJDK 64-Bit Server VM by Oracle Corporation
Windows 10 10.0
GC: G1 Young Generation, G1 Old Generation
Memory: 1280M
Cores: 12
Registry: external.system.auto.import.disabled=true
Non-Bundled Plugins: org.jetbrains.kotlin
 */