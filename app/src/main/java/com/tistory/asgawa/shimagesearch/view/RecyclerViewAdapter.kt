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
        holder.bindItems(imageUrls[position])
    }

    fun update(imageUrls: ArrayList<String>) {
        if (imageUrls.size == 0) {
            this.imageUrls.clear()
            notifyDataSetChanged()
        } else {
            this.imageUrls = imageUrls
            notifyItemRangeChanged(0, this.imageUrls.size)
        }
    }
}