package com.example.memeviewer.Daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.memeviewer.Models.UrlEntity


@Dao
interface UrlDao {

    @Query("select * from Url_Table order by id ASC")
    fun fetchAllData():LiveData<List<UrlEntity>>

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun insert(entity:UrlEntity)

    @Delete
    suspend fun delete(entity: UrlEntity)
}