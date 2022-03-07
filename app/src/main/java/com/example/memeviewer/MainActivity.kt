package com.example.memeviewer

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memeshare.util.ConnectionManager
import com.example.memeviewer.Daos.MemeDao
import com.example.memeviewer.Models.Meme
import com.example.memeviewer.Models.UrlEntity
import com.example.memeviewer.util.MySingleton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var memeText: TextView
    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var btNextMeme: Button
    private lateinit var like: ImageView
    private lateinit var addToFavourite: FloatingActionButton
    private lateinit var likedButton: Button
    private lateinit var favouriteButton: Button

    private lateinit var memeUrl: Any
    private lateinit var memeTitle: String

    lateinit var viewModel:UrlViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memeText = findViewById(R.id.memeText)
        imageView = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.progressBar)
        btNextMeme = findViewById(R.id.btNextMeme)
        like = findViewById(R.id.like)
        addToFavourite = findViewById(R.id.addToFavourite)
        likedButton=findViewById(R.id.likedButton)
        favouriteButton=findViewById(R.id.favouriteButton)

        auth = Firebase.auth

        loadMeme()
        btNextMeme.setOnClickListener {
            loadMeme()
            like.setImageResource(R.drawable.ic_unlike)
        }

        like.setOnClickListener {

            val meme = Meme()
            meme.memeTitle = memeTitle
            meme.memeUrl = memeUrl.toString()
            val currentUserId = auth.currentUser!!.uid
            meme.likedBy.add(currentUserId)
            val memeDao = MemeDao()
            memeDao.addMeme(meme)

            like.setImageResource(R.drawable.ic_like)

        }

        likedButton.setOnClickListener {
            val intent=Intent(this,LikedActivity::class.java)
            startActivity(intent)
        }
        favouriteButton.setOnClickListener {
            val intent=Intent(this,FavouriteActivity::class.java)
            startActivity(intent)
        }


        //ViewModel----------------------------------------------------------------------------------------------
        viewModel= ViewModelProvider( this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(UrlViewModel::class.java)


        addToFavourite.setOnClickListener {
            val insert= UrlEntity(memeTitle,memeUrl.toString())
            viewModel.insertUrl(insert)
            Toast.makeText(this,memeTitle+" Inserting...",Toast.LENGTH_SHORT).show()
        }

    }

    fun loadMeme() {

        if (ConnectionManager().checkConnectin(this)) {
            //If connection Succeed

            val url = "https://meme-api.herokuapp.com/gimme"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, {
                memeTitle = it.get("title").toString()
                memeText.text = memeTitle
                progressBar.visibility = View.VISIBLE

                // Extracting URL from the JSON object - (Obtained by JsonObjectRequest)
                memeUrl = it.get("url")

                // Loading the image on the extracted url into imgView
//                Glide.with(this).load(img).into(imgImageView)
                Glide.with(this).load(memeUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(imageView)

            }, {
                Toast.makeText(this, "Json Parsing Failed", Toast.LENGTH_SHORT).show()
            })
            // Add the request to the RequestQueue.
            //This queue is replaced by MySingleton class -->    queue.add(jsonObjectRequest)
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


        } else {
            //If Connection Failed
            Toast.makeText(this, "No Internet Found!!!", Toast.LENGTH_SHORT).show()
        }
    }
}