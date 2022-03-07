package com.example.memeviewer

import com.example.memeviewer.Daos.UrlDao
import com.example.memeviewer.Models.UrlEntity

class UrlRepository(val urlDao: UrlDao) {

    val fetchAllData=urlDao.fetchAllData()

    suspend fun insert(urlEntity: UrlEntity){
        urlDao.insert(urlEntity)
    }

    suspend fun delete(urlEntity: UrlEntity){
        urlDao.delete(urlEntity)
    }
}