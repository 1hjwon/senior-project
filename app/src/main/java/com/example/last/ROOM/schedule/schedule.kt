package com.example.last.ROOM.schedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class schedule(
    //@PrimaryKey(autoGenerate = true) val id:Int ,
    @PrimaryKey @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name= "title") val title:String?,
    @ColumnInfo(name= "content") val content:String?,
    @ColumnInfo(name = "rating") val rating:Float = 0f

)
