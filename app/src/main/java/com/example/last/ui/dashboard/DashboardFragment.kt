package com.example.last.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.last.ROOM.appDatabase
import com.example.last.ROOM.memo.memo
import com.example.last.ROOM.memo.memoDao
import com.example.last.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), TextWatcher {
    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private lateinit var adapter: DashboardAdapter
    //var owner = viewLifecycleOwner
    private val memoList: ArrayList<memo> = ArrayList()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var memolist = ArrayList<memo>()
    private var filteredList = ArrayList<memo>()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dao: memoDao = appDatabase.getDatabase(requireContext()).memoDao()

        val repos = DashboardRepository(dao)
        val factory = DashboardViewModelFactory(repos)
        dashboardViewModel =
            ViewModelProvider(this, factory).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding!!.importdashboard.memoRecyclerView.setHasFixedSize(true)
        _binding!!.importdashboard.memoRecyclerView.layoutManager = LinearLayoutManager(context)

        //dashboardViewModel.insert(memo(0, "타이틀1", "내용1", "11.02", "6:38", false))

        //DashboardAdapter(memo)
        //val memo: LiveData<List<memo>> = dashboardViewModel.getAll()// = dashboardViewModel.repository.allMemo
        //val listMemo:LiveData<List<memo>> = dashboardViewModel.getAll()
        //memo.asFlow().
        //Log.i("da1", dashboardViewModel.getAll().toString())
        adapter = DashboardAdapter(this.requireContext(), _binding!!, dashboardViewModel)
        //adapter.setList(listMemo)   dashboardViewModel
        _binding!!.importdashboard.memoRecyclerView.adapter = adapter
        // = viewLifecycleOwner

        _binding!!.importdashboard.insertMemoButton.setOnClickListener {
            val dialog = customDialog(requireContext(), dashboardViewModel)
            dialog.showIMDialog()
        }



        _binding!!.importdashboard.memoButton.setOnClickListener {
            //_binding!!.importdashboard.memoEditText.
        }
        //_binding!!.importdashboard.memoRecyclerView
        //recyclerView.adapter = adapter
/*
        dashboardViewModel.getAll().observe(this, Observer {
            Log.i("it1", it.toString())
            adapter.apply { adapter.items = it }
            Log.i("it2", it.toString())
            //adapter.items = it

        })*/
        dashboardViewModel.allMemo.observe(viewLifecycleOwner, Observer {
            adapter.setMemo(it)
            adapter.notifyDataSetChanged()
            Log.i("allMemo", it.toString())


        })

        _binding!!.importdashboard.memoButton.setOnClickListener {
            adapter.filter.filter(_binding!!.importdashboard.memoEditText.text.toString())
            Log.i("검색?", _binding!!.importdashboard.memoEditText.text.toString()) //o
        }

        Log.i("allMemo?", adapter.getItems().toString())

        //var list = adapter.getItems()
        //var arrayAdapter = ArrayAdapter<String>(requireContext(), _binding!!.importdashboard.memoEditText, adapter.getItems())



        /*_binding!!.importdashboard.memoEditText.setAdapter(
            ArrayAdapter<String>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, list

        ))*/


/*
        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        TODO("Not yet implemented")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        TODO("Not yet implemented")
    }

    override fun afterTextChanged(s: Editable?) {
        TODO("Not yet implemented")
    }


}

