package com.example.last.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.last.R
import com.example.last.ROOM.appDatabase
import com.example.last.ROOM.schedule.schedule
import com.example.last.ROOM.schedule.scheduleDao
import com.example.last.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var homeAdapter: HomeAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var schedules = listOf<schedule>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
        val dao: memoDao = appDatabase.getDatabase(requireContext()).memoDao()

        val repos = DashboardRepository(dao)
        val factory = DashboardViewModelFactory(repos)
         */
        val dao: scheduleDao = appDatabase.getDatabase(requireContext()).scheduleDao()
        val repos = HomeRepository(dao)
        val factory = HomeViewModelFactory(repos)

        homeViewModel =
            ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        _binding!!.recyclerviewDashboardLayout.recyclerviewDashboard.setHasFixedSize(true)
        _binding!!.recyclerviewDashboardLayout.recyclerviewDashboard.layoutManager = GridLayoutManager(this.context, 7)

        homeAdapter = HomeAdapter( requireContext(), this ,_binding!!, homeViewModel)
        /*{
            val intent = Intent(this.requireActivity(), HomeIntent::class.java)

            startActivity(intent)
        }*/
        _binding!!.recyclerviewDashboardLayout.recyclerviewDashboard.adapter = homeAdapter


        _binding!!.recyclerviewDashboardLayout.recyclerviewDashboard.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.HORIZONTAL))
        _binding!!.recyclerviewDashboardLayout.recyclerviewDashboard.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        _binding!!.recyclerviewDashboardLayout.next.setOnClickListener {
            homeAdapter.goNextMonth()
        }

        _binding!!.recyclerviewDashboardLayout.prev.setOnClickListener {
            homeAdapter.goPrevMonth()
        }

        //homeViewModel.insert(schedule(0, "11.02", "title1", "content1"))
        //homeViewModel.insert(schedule("2021 11 10", "title1?", "content1?")) //
///////////////////////////////  옵저버   ///////////////////////////////
        homeViewModel.allSchedule.observe(viewLifecycleOwner, Observer {
            homeAdapter.setItem(it)
            homeAdapter.notifyDataSetChanged()
        })
        /*
                dashboardViewModel.allMemo.observe(viewLifecycleOwner, Observer {
            adapter.setMemo(it)
            adapter.notifyDataSetChanged()
            Log.i("allMemo", it.toString())
        })
         */
/*    ///////////////////////////////////////////////////////////////////////
        _binding!!.recyclerviewDashboardLayout.testButton.setOnClickListener {
            val intent = Intent(this.requireContext(), HomeIntent::class.java)
            //intent.putExtra()
        }
*/
        val root: View = binding.root




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun refreshCalendar(calendar: Calendar) {
        val dateformat = SimpleDateFormat("yyyy MM", Locale.KOREAN)

        this._binding!!.recyclerviewDashboardLayout.current.text = dateformat.format(calendar.time)
    }


    interface HomeListener { //x
        fun insert(schedule: schedule)
    }

}