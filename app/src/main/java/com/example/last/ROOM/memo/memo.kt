package com.example.last.ROOM.memo

import androidx.room.*

@Entity(indices = [Index(value = ["title"])])
data class memo(
    @PrimaryKey(autoGenerate = true)  var memoID:Int,
    @ColumnInfo(name= "title") val Title:String?,
    @ColumnInfo(name= "content") val Content:String?,
    @ColumnInfo(name= "date") val Date:String,
    @ColumnInfo(name = "time") val Time:String
)

/*
,
    @Ignore @ColumnInfo(name = "isClicked") val Click:Boolean = false

memo(int,java.lang.String,java.lang.String,java.lang.String,java.lang.String,boolean) ->
    [param:memoID -> matched field:memoID,
    param:Title -> matched field:Title,
    param:Content -> matched field:Content,
    param:Date -> matched field:Date,
    param:Time -> matched field:Time,
    param:Click -> matched field:unmatched]
 */