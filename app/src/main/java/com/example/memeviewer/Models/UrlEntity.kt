package com.example.memeviewer.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Url_Table")
class UrlEntity(
    @ColumnInfo(name = "name")val name:String,
    @ColumnInfo(name = "url")val url:String,

    )
{
    @PrimaryKey(autoGenerate = true) var id:Int=0;
}