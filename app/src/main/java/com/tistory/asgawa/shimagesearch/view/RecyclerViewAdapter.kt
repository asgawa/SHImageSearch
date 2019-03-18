package com.tistory.asgawa.shimagesearch.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tistory.asgawa.shimagesearch.R

class RecyclerViewAdapter(private var imageUrls:ArrayList<String>): RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun getItemCount(): Int {
        return imageUrls.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_image_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindItem(imageUrls[position])
    }

    fun update(imageUrls: ArrayList<String>) {
        notifyItemRangeRemoved(0, this.imageUrls.size)
        if (imageUrls.isEmpty()) {
            this.imageUrls.clear()
            notifyDataSetChanged()  //refresh whole recycler view
        } else {
            this.imageUrls = imageUrls
            notifyItemRangeInserted(0, this.imageUrls.size)
        }
    }
}