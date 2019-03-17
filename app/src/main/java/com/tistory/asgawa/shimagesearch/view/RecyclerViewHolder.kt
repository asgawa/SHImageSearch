package com.tistory.asgawa.shimagesearch.view

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tistory.asgawa.shimagesearch.util.SHLog
import kotlinx.android.synthetic.main.search_image_item.view.*

class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
    private val log = SHLog("RecyclerViewHolder")
    fun bindItems(data : String) {
        //TODO #TS5 Begin Loading show
        log.d("Image load start")
        //#TS5 End
        Glide.with(itemView.context)
            .load(data)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(android.R.drawable.ic_menu_report_image)   //#TS5 Loading
            .error(android.R.drawable.stat_notify_error)    //#TS4 error
            .priority(Priority.HIGH)
            .fitCenter()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    //#TS4 Begin load fail
                    e?.printStackTrace()
                    Glide.with(itemView.context)
                        .clear(itemView)
                    //#TS4 End
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?,target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    //TODO #TS5 Begin Loading hide
                    log.d("Image load end")
                    //#TS5 End
                    return false
                }
            })
            .dontAnimate()
            .into(itemView.imageViewRemoteImage)
    }

    override fun onClick(v: View) {
        //do something
    }
}