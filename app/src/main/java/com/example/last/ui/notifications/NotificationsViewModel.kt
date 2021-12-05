package com.example.last.ui.notifications

import androidx.lifecycle.*

class NotificationsViewModel(private val repository: NotificationsRepository) : ViewModel() {
    val allSchedule = repository.schedules

    private val _countAll = MutableLiveData<Int>().apply {
        value = 1
    }
    val countAll = _countAll

    private val _rateCount = MutableLiveData<Float>().apply {
        value = 2f
    }
    val rateCount = _rateCount


    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private val _rate = MutableLiveData<Int>().apply {
        this.value = 3
    }
    val rate = _rate



}
class NotificationsViewModelFactory(private val repository: NotificationsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificationsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}