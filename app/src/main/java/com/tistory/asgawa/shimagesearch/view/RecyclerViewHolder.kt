package com.tistory.asgawa.shimagesearch.view

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.search_image_item.view.*

class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
    fun bindItems(data : String){
        Glide.with(itemView.context).load(data)
            .into(itemView.imageViewRemoteImage)
    }

    override fun onClick(v: View) {
        //do something
    }
}