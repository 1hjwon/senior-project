package com.example.last.ui.notifications

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.last.ROOM.appDatabase
import com.example.last.ROOM.schedule.schedule
import com.example.last.ROOM.schedule.scheduleDao
import com.example.last.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() , intent{
    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dao: scheduleDao = appDatabase.getDatabase(requireContext()).scheduleDao()
        val repos = NotificationsRepository(dao)
        val factory = NotificationsViewModelFactory(repos)

        notificationsViewModel =
            ViewModelProvider(this, factory).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = NotificationsAdapter(requireContext(), this)

        _binding!!.viewPager1.adapter =  adapter //어댑터 연결 - 컨텍스트, 스케줄개수(-1), 바인딩,
        //_binding!!.viewPager1.orientation =
        //_binding!!.viewPager1

        //val max = Int.MAX_VALUE
        _binding!!.viewPager1.setCurrentItem(((Int.MAX_VALUE/2))) //대충 중간정도 위치에서 검색화면 맞추기

        //notificationsViewModel

        /*_binding!!.textNotifications.setOnClickListener {
            adapter.onclick()
        }*/


        /*_binding!!.viewPager1.setOnLongClickListener {
            it.callOnClick()
            //adapter.longonclick()
        }*/
        _binding!!.viewPager1.setOnClickListener {
            adapter.onclick()
        }

        _binding!!.viewPager1.setOnClickListener {
            //listener(requireContext())
            //onClick(it)

        }

        _binding!!.testTVVP.setOnClickListener {
            //listener(requireContext())
        }


        val array = ArrayList<schedule>()
        notificationsViewModel.allSchedule.observe(viewLifecycleOwner, Observer {
            //binding.totalItem.text = adapter.schedule.size.toString()

            adapter.setItem(it)
            adapter.notifyDataSetChanged()
            Log.i("observe; setItem", adapter.schedule.toString()) //값들어감

            array.apply {
                this.addAll(adapter.getItem())
            }

            Log.i("observe; array", array.toString()) //값들어감

        })
        Log.i("break; array", array.toString()) //값들어감


        val totalRate = binding.totalRate
        notificationsViewModel.rateCount.observe(viewLifecycleOwner, Observer {
            //val array = adapter.setRate()

            totalRate.text = "/ 5"
        })

        //binding.totalItem.text = adapter.schedule.size.toString()

        val avgRate = binding.avrRate
        notificationsViewModel.rate.observe(viewLifecycleOwner, Observer {
            array.apply {
                this.addAll(adapter.getItem())
            }

            var count:Float = 0f
            var div:Float = 0f

            val arrayFloat = ArrayList<Float>()

            if (array.isNotEmpty()){
                for (item in array){
                    count = item.rating + count

                    if(item.rating != 0f){
                        arrayFloat.add(item.rating)
                    }
                    div = count.div(array.size)
                    Log.i("avgRate1; div", div.toString()) //
                    Log.i("avgRate1; arrayFloat", arrayFloat.toString()) //
                     
                }
            }
            Log.i("avgRate2; div", div.toString()) //
            Log.i("avgRate2; arrayFloat", arrayFloat.toString()) //

            //totalRate.text//array.size.toString()
            Log.i("observe; rateCount", adapter.schedule.toString()) //
            adapter.notifyDataSetChanged()
        })

        val totalItem = binding.totalItem
        notificationsViewModel.countAll.observe(viewLifecycleOwner, Observer {
            totalItem.text = adapter.schedule.size.toString()
            Log.i("observe; countAll", adapter.schedule.toString()) //값들어감
            adapter.notifyDataSetChanged()

        })


        //val layoutInflater = LayoutInflater.from(context).inflate(R.layout.viewpageritem1, parent, false)

        //adapter.PagerViewHolder().itemView.setOnClickListener {
        //adapter.listerner
            //Log.i("requireView", "123")

        /*val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
/*
        val rate2 = binding.textNotifications
        notificationsViewModel.rate.observe(viewLifecycleOwner, Observer {
            rate2.text = it.toString()

            rate2.text = "123" //mutable
        })*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    interface intentListener{
        fun onClick(code : Int)

    }


    fun onClick(listener: (Int) -> Unit){ // listner : (Int) -> Unit 이라는 것은 매개변수 하나만 가지고 있으며, 리턴 타입이 없는 함수라는 뜻; 안씀
        val listener = object : intentListener{
            override fun onClick(code: Int) {
                listener(code)
            }
        }
    }

    override fun google() {
        val intent = Intent(requireContext(), NotificationsSearch::class.java)

        startActivity(intent)
    }

    override fun naver() {
        val intent = Intent(requireContext(), NotificationSearchNaver::class.java)

        startActivity(intent)
    }

    override fun alarm() {
        //알람설정 창 띄워서 해야하는데 알람매니저 안댐..
    }
}