package com.example.last.ui.home

import androidx.lifecycle.viewModelScope
import com.example.last.ROOM.schedule.schedule
import kotlinx.coroutines.launch

interface interfaceListener {
    fun insert(schedule: schedule)
    fun insertHVM(schedule: schedule)
    fun deleteHVM(schedule: schedule)
    fun updateHVM(schedule: schedule)

}