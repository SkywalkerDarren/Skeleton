package com.ethanhua.skeleton.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.sample.R

/**
 * Created by ethanhua on 2017/7/29.
 */
class TopicAdapter : RecyclerView.Adapter<SimpleRcvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleRcvViewHolder {
        val layoutId = if (viewType == TYPE_HEADER) {
            R.layout.item_title_more
        } else {
            R.layout.item_topic
        }
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false).let {
            SimpleRcvViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: SimpleRcvViewHolder, position: Int) {}
    override fun getItemCount() = 7

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_CONTENT
        }
    }

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_CONTENT = 2
    }
}