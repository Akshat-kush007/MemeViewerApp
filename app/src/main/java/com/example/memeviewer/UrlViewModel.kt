package com.example.memeviewer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.memeviewer.Models.UrlEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UrlViewModel(application: Application) : AndroidViewModel(application) {

    private val repository:UrlRepository
    var allData:LiveData<List<UrlEntity>>

    init{
        val dao=UrlDataBase.getDatabase(application).getUrlDao()

        repository=UrlRepository(dao)

        allData=repository.fetchAllData
    }

    //As suspend function can't call by a normal foreGround function
    //Make this function Coroutine function to work on backGround thread
    fun deleteUrl(urlEntity: UrlEntity)=viewModelScope.launch(Dispatchers.IO){
        repository.delete(urlEntity)
    }

    fun insertUrl(urlEntity: UrlEntity)=viewModelScope.launch(Dispatchers.IO){
        repository.insert(urlEntity)
    }
}