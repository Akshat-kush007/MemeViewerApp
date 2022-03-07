package com.example.memeviewer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.memeviewer.Daos.UrlDao
import com.example.memeviewer.Models.UrlEntity

//class UrlDataBase {
//}

@Database(entities = arrayOf(UrlEntity::class), version = 1, exportSchema = false)
public abstract class UrlDataBase : RoomDatabase() {

    //abstract function------------------------------
    abstract fun getUrlDao(): UrlDao


    //This will->
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UrlDataBase? = null


        // Singleton function
        fun getDatabase(context: Context): UrlDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UrlDataBase::class.java,
                    "url_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}

