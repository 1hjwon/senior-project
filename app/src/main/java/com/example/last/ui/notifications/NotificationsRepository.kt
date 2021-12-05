package com.example.last.ui.notifications

import com.example.last.ROOM.schedule.schedule
import com.example.last.ROOM.schedule.scheduleDao

class NotificationsRepository(private val dao:scheduleDao) {
    val schedules = dao.getAllSchedule()

    suspend fun insert(schedule: schedule){
        dao.insertSchedule(schedule)
    }

    suspend fun update(schedule: schedule){
        dao.updateSchedule(schedule)
    }

    suspend fun delete(schedule: schedule){
        dao.deleteSchedule(schedule)
    }

    suspend fun deleteAll(){
        dao.deleteAllSchedule()
    }

    fun findSchedule(date: String): schedule {
        return dao.findSchedule(date)
    }
}