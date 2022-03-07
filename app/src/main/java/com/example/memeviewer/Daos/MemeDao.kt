package com.example.memeviewer.Daos

import com.example.memeviewer.Models.Meme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MemeDao {
    val db=FirebaseFirestore.getInstance()
    val memeCollecton=db.collection("Meme")

    fun addMeme(meme: Meme){
        GlobalScope.launch {
            memeCollecton.document().set(meme)
        }
    }

}