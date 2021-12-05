package com.example.last.ROOM.schedule

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface scheduleDao {
    @Query("SELECT * FROM schedule")
    fun getAllSchedule(): LiveData<List<schedule>>

    //@Query("select from ")
    @Update
    suspend fun updateSchedule(scheds: schedule)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSchedule( scheds: schedule)

    @Delete
    suspend fun deleteSchedule(scheds: schedule)

    @Query("DELETE FROM schedule ")
    suspend fun deleteAllSchedule()

    @Query("select * from schedule where date like :getDate")
    fun findSchedule(getDate:String) : schedule
    //@Query("SELECT * FROM schedule WHERE first_name LIKE :first AND " +
    //        "last_name LIKE :last LIMIT 1")
    //fun findByName(first: String, last: String): schedule

    //@Query("INSERT INTO schedule(rating) VALUES (:rating) " ) //where date = :date LIMIT 1 일단해보고 안되면 업데이트 쪽으로 ...
    //suspend fun insertRating(rating: Float) //, date:String


}