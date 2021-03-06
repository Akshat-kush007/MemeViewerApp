package com.example.memeviewer.Daos

import com.example.memeviewer.Models.Users

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
    private val db= FirebaseFirestore.getInstance()
    private val userCollection=db.collection("users")

    fun addUser(user: Users?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO){
                userCollection.document(it.uid).set(it)
            }
        }
    }

    fun getUserById(uId: String): Task<DocumentSnapshot> {

        return userCollection.document(uId).get()
    }
//    fun getUserById(){
//
//    }
}