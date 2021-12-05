package com.example.last.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.last.ROOM.schedule.schedule
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    //private val repository = TodoRepository(application)
    //private val items = repository.getAll()
    //private val repository = repository
    val allSchedule = repository.schedules //repository.getAllSchedule()

    init {
        //deleteAll()
        //insert(schedule( "Date", "title", "content")) // + rating 1f
        //insert(schedule( "2021 11 2", "타이틀1", "내용1"))
        //insert(schedule( "2021 11 3", "타이틀2", "내용2"))
        //insert(schedule( "2021 11 4", "타이틀3", "내용3"))


    }

    fun insert(schedule: schedule){
        insertHVM(schedule)
    }
    fun insertHVM(schedule: schedule) = viewModelScope.launch{
        repository.insert(schedule)
    }

    fun update(schedule: schedule){
        updateHVM(schedule)
    }
    fun updateHVM(schedule: schedule) = viewModelScope.launch {
        repository.update(schedule)
    }
    fun delete(schedule: schedule){
        deleteHVM(schedule)
    }
    fun deleteHVM(schedule: schedule) = viewModelScope.launch {
        repository.delete(schedule)
    }

    fun deleteAll(){
        deleteAllHVM()
    }
    fun deleteAllHVM() = viewModelScope.launch{
        repository.deleteAll()
    }




    suspend fun findSchedule(date: String): schedule {
        //val item:schedule
        //findHVM(date)
        Log.i("뷰모델; find1 ", "순서 1")
        val dItem = findHVM(date).await()

        Log.i("뷰모델; find2 ", dItem.toString())



        return dItem
    }

    private var list = ArrayList<schedule>()
    //var getItem:schedule

    fun findHVM(date:String) = viewModelScope.async {
        val item = repository.findSchedule(date)

        Log.i("뷰모델스코프; find", item.toString()) //나와요
        return@async item

    }


    /*
    public interface clickListener {
        fun insert(schedule: schedule)
    }*/
}

class HomeViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}