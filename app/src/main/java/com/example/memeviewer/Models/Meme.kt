package com.example.memeviewer.Models

data class Meme (
    var memeUrl:String="",
    var memeTitle:String="",
    val likedBy: ArrayList<String> = ArrayList()
        )