package com.example.memeviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.memeviewer.Models.UrlEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton

//class FavouriteAdapter {
//}


class FavouriteAdapter(val buttonInterface: onDeleteInterface): RecyclerView.Adapter<FavouriteAdapter.FavHolder>() {

    val arrayList= ArrayList<UrlEntity>()

    class FavHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView =itemView.findViewById(R.id.itemImage)
        val removeButton: FloatingActionButton =itemView.findViewById(R.id.itemAddToFavourite)
        val progressBar:ProgressBar=itemView.findViewById(R.id.itemProgressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.like_item,parent,false)
        val holder=FavHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: FavHolder, position: Int) {
        holder.progressBar.visibility=View.GONE
        holder.removeButton.setImageResource(R.drawable.ic_delete)
        val currentItem=arrayList[position]
        Glide.with(holder.itemView.context).load(currentItem.url).into(holder.image)

        holder.removeButton.setOnClickListener {
            buttonInterface.onRemoveButtonClick(arrayList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun updateList(list:List<UrlEntity>){
        arrayList.clear()
        arrayList.addAll(list)

        notifyDataSetChanged()
    }
}
interface onDeleteInterface{

    fun onRemoveButtonClick(item:UrlEntity)


}