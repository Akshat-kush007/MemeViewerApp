package com.example.memeviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memeviewer.Models.UrlEntity

class FavouriteActivity : AppCompatActivity(), onDeleteInterface {
    lateinit var favRecyclerView: RecyclerView
    lateinit var favAdapter: FavouriteAdapter

    lateinit var viewModel:UrlViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        title="Favourites"

        favRecyclerView=findViewById(R.id.favRecyclerView)
        favRecyclerView.layoutManager= LinearLayoutManager(this)
        favAdapter= FavouriteAdapter(this)
        favRecyclerView.adapter=favAdapter


        //ViewModel----------------------------------------------------------------------------------------------
        viewModel= ViewModelProvider( this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(UrlViewModel::class.java)

        //Using the liveData method
        //Here we got the list as live data---------
        viewModel.allData.observe(this, Observer {list->
            list?.let {
                favAdapter.updateList(it)
            }
        })
        //ViewModel----------------------------------------------------------------------------------------------

    }

    override fun onRemoveButtonClick(item: UrlEntity) {
        viewModel.deleteUrl(item)
        Toast.makeText(this,item.name+" Deleting...", Toast.LENGTH_SHORT).show()
    }
}