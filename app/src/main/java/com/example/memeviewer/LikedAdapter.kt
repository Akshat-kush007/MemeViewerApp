package com.example.memeviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.memeviewer.Models.Meme
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LikedAdapter(options: FirestoreRecyclerOptions<Meme>): FirestoreRecyclerAdapter<Meme, LikedAdapter.LikedHolder>(
    options
) {
    class LikedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView=itemView.findViewById(R.id.itemTitle)
        val image: ImageView =itemView.findViewById(R.id.itemImage)
        val progressBar:ProgressBar=itemView.findViewById(R.id.itemProgressBar)
        val addButton:FloatingActionButton=itemView.findViewById(R.id.itemAddToFavourite)
        val likeButton:ImageView=itemView.findViewById(R.id.itemLike)
        val likeCount:TextView=itemView.findViewById(R.id.itemLikeCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.like_item,parent,false)
        val holder=LikedHolder(view)

        holder.likeButton.setOnClickListener {
//            listener.onLikeClicked(snapshots.getSnapshot(holder.adapterPosition).id)
        }

        return holder
    }

    override fun onBindViewHolder(holder: LikedHolder, position: Int, model: Meme) {
        holder.progressBar.visibility=View.VISIBLE
        holder.title.text=model.memeTitle
        Glide.with(holder.image.context).load(model.memeUrl).into(holder.image)
        holder.progressBar.visibility=View.GONE
        holder.likeCount.text=model.likedBy.size.toString()

        val auth= Firebase.auth
        val currentUser=auth.currentUser!!.uid
        val isLiked=model.likedBy.contains(currentUser)

        if(isLiked){
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_like))

        }else{
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_unlike))

        }
    }
}