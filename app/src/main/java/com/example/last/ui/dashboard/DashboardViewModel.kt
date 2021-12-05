package com.example.last.ui.dashboard

import androidx.lifecycle.*
import com.example.last.ROOM.memo.memo
import kotlinx.coroutines.*

class DashboardViewModel
    (private val repository: DashboardRepository) : ViewModel()
{
    val allMemo: LiveData<List<memo>> = repository.memos

    init {
        //deleteAll()

        //insert(memo(0, "제목", "내용", "날짜", "시간"))
        //insert(memo(0, "title2", "content2", "11 02", "6:36:11"))


    }

    fun getRepos(): DashboardRepository {
        return this.repository
    }

    init {
        allMemo.apply { getAll() }
    }


    fun getAll():LiveData<List<memo>>{
        return allMemo
    }

    fun insert(memo: memo){
        insertDVM(memo)
    }

    fun update(memo: memo){
        updateDVM(memo)
    }

    fun deleteAll(){
        deleteALLDVM()
    }

    fun delete(memo: memo){
        deleteDVM(memo)
    }

    fun insertDVM(memo: memo)= viewModelScope.launch {
        repository.insert(memo)
    }

    fun updateDVM(memo: memo) = viewModelScope.launch {
        repository.update(memo)
    }

    fun deleteDVM(memo: memo) = viewModelScope.launch {
        repository.delete(memo)
    }

    fun deleteALLDVM() = viewModelScope.launch {
        repository.deleteAll()
    }

/*
    fun deleteDVM(memo: memo) = viewModelScope.launch {
        repository.deleteMemo(memo)
    }

    fun updateDVM(memo: memo) = viewModelScope.launch {
        repository.updateMemo(memo)
    }

    fun deleteAllDVM(memo: memo) = viewModelScope.launch {
        repository.deleteAllMemo(memo)
    }*/
/*
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

*/

}

class DashboardViewModelFactory(private val repository: DashboardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}