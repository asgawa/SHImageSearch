package com.tistory.asgawa.shimagesearch.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.tistory.asgawa.shimagesearch.R
import kotlinx.android.synthetic.main.search_image_item.view.*

class RecyclerViewAdapter(private var urlList:ArrayList<String>): RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun getItemCount(): Int {
        return urlList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_image_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindItems(urlList[position])
    }

    fun update(urls: ArrayList<String>) {
        urlList = urls
        notifyDataSetChanged()
    }
}