package com.example.last.ROOM

import androidx.room.TypeConverter
import java.sql.Date

class typeConverter { //결국에는 안씀
    @TypeConverter
    fun dateToLong(value: Long?) : Date? = value?.let { Date(it) }

    @TypeConverter
    fun longToDate(date : Date?) : Long? = date?.time
}