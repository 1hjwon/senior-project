package com.example.last.ROOM.memo

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface memoDao {
    @Query("SELECT * FROM memo")
    fun getAllMemo(): LiveData<List<memo>>

    @Query("SELECT * FROM memo WHERE Title IN (:Title)")
    suspend fun loadAllMemoByTitles(Title: IntArray): List<memo>

    @Update
    suspend fun updateMemo(memos: memo)
    //@Query("SELECT * FROM schedule WHERE first_name LIKE :first AND " +
    //        "last_name LIKE :last LIMIT 1")
    //fun findByName(first: String, last: String): schedule

    @Insert
    suspend fun insertMemo(memos: memo)

    @Delete
    suspend fun deleteMemo(memos: memo)

    @Query("delete from memo")
    suspend fun deleteAllMemo()

}