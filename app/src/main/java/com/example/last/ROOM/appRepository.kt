package com.example.last.ROOM

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.last.ROOM.achive.achive
import com.example.last.ROOM.achive.achiveDao
import com.example.last.ROOM.memo.memo
import com.example.last.ROOM.memo.memoDao
import com.example.last.ROOM.schedule.schedule
import com.example.last.ROOM.schedule.scheduleDao
import kotlinx.coroutines.*
import java.lang.Exception

class appRepository(context: Context) { //x
    /*
    val job:Job = Job()
    val scope = CoroutineScope(Dispatchers.Default + job)

     fun backgoundDatabase(context: Context) {
        scope.launch {
            try {

                //database = appDatabase.getDatabase(context = context) //데이터베이스에 스코프넣어서 다시 해보기 !!

            } catch (e:Exception){
                println("backgroundDatabase에러!!!!!!!!!!!!")
            }

        }

        println("코루틴룸데이터베이스")
        Log.i("코루틴", "roomDatabase")
    }
    */

    private val schedDao: scheduleDao
    private val schedule: LiveData<List<schedule>>
    init {
        val database = appDatabase.getDatabase(context)
        schedDao = database!!.scheduleDao()
        schedule = database.scheduleDao().getAllSchedule()
    }

    /*
    //val db = database.
    private val scheduleDao = database.scheduleDao()
    private val memoDao = database.memoDao()
    private val achiveDao = database.achiveDao()

    val allScheds = scheduleDao.getAllSchedule()
    val allMemo = memoDao.getAllMemo()
    val allAchive = achiveDao.getAllAchive()
*/
    //@WorkerThread
    suspend fun insertSchedule(schedule: schedule){
        schedDao.insertSchedule(schedule)

    }

    suspend fun deleteSchedule(schedule: schedule){
        schedDao.deleteSchedule(schedule)
    }

    suspend fun deleteAllSchedule(){
        schedDao.deleteAllSchedule()
    }
    suspend fun updateSchedule(schedule: schedule){
        schedDao.updateSchedule(schedule)
    }

    fun getAllSchedule(): LiveData<List<schedule>>{
        return schedDao.getAllSchedule()
    }
/*
    @WorkerThread
    suspend fun insertMemo(memo: memo){
        memoDao.insertMemo(memo)
    }

    @WorkerThread
    suspend fun insertAchive(achive: achive){
        achiveDao.insertAchive(achive)
    }
*/
/*
    suspend fun insertMemoBG(memo: memo){
        val memoDao: memoDao
        coroutineScope {
            launch {
                memoDao =
            }
        }
    }*/

}