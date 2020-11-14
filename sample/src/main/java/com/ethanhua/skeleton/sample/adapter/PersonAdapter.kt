package com.ethanhua.skeleton.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.sample.R

/**
 * Created by ethanhua on 2017/7/29.
 */
class PersonAdapter : RecyclerView.Adapter<SimpleRcvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleRcvViewHolder {
        return LayoutInflater.from(parent.context)
                .inflate(R.layout.item_person, parent, false).let {
                    SimpleRcvViewHolder(it)
                }
    }

    override fun onBindViewHolder(holder: SimpleRcvViewHolder, position: Int) {}
    override fun getItemCount() = 10
}