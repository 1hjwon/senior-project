package com.example.last.ui.dashboard

import com.example.last.ROOM.memo.memo
import com.example.last.ROOM.memo.memoDao

class DashboardRepository(private val dao: memoDao) {
    val memos = dao.getAllMemo()

    suspend fun insert(memo: memo){
        dao.insertMemo(memo)
    }

    suspend fun update(memo: memo){
        dao.updateMemo(memo)
    }

    suspend fun delete(memo: memo){
        dao.deleteMemo(memo)
    }

    suspend fun deleteAll(){
        dao.deleteAllMemo()
    }
}