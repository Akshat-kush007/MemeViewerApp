package com.example.memeviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memeviewer.Daos.MemeDao
import com.example.memeviewer.Models.Meme
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class LikedActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LikedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liked)
        title="Liked Memes"

        recyclerView=findViewById(R.id.recyclerView)


        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val memeDao=MemeDao()
        val memeCollection=memeDao.memeCollecton
        val option=FirestoreRecyclerOptions.Builder<Meme>().setQuery(memeCollection,Meme::class.java).build()

        adapter=LikedAdapter(option)

        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter=adapter
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}