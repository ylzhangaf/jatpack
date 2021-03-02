package com.ylozhangaf.ppjoke.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ylozhangaf.ppjoke.model.Feed

class FeedAdapter(itemCallBack : DiffUtil.ItemCallback<Feed>) : PagedListAdapter<Feed, FeedAdapter.ViewHolder>(itemCallBack) {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("")
    }

}