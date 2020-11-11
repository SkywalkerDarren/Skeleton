package com.ethanhua.skeleton

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import io.supercharge.shimmerlayout.ShimmerLayout

/**
 * Created by ethanhua on 2017/7/29.
 */
class SkeletonAdapter : RecyclerView.Adapter<ViewHolder>() {
    var layoutReference = 0
    var layoutArrayReferences: IntArray = IntArray(0)
    var color = 0
    var shimmer = false
    var shimmerDuration = 0
    var mItemCount = 0
    @IntRange(from = 0, to = 30)
    var shimmerAngle = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (doesArrayOfLayoutsExist()) {
            layoutReference = viewType
        }
        return if (shimmer) {
            ShimmerViewHolder(inflater, parent, layoutReference)
        } else object : ViewHolder(inflater.inflate(layoutReference, parent, false)) {}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (shimmer) {
            (holder.itemView as ShimmerLayout).apply {
                setShimmerAnimationDuration(shimmerDuration)
                setShimmerAngle(shimmerAngle)
                setShimmerColor(color)
                startShimmerAnimation()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (doesArrayOfLayoutsExist()) {
            getCorrectLayoutItem(position)
        } else super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getCorrectLayoutItem(position: Int): Int {
        return if (doesArrayOfLayoutsExist()) {
            layoutArrayReferences[position % layoutArrayReferences.size]
        } else layoutReference
    }

    private fun doesArrayOfLayoutsExist(): Boolean {
        return layoutArrayReferences.isNotEmpty()
    }

    override fun getItemCount() = mItemCount

    fun setItemCount(itemCount: Int) {
        mItemCount = itemCount
    }
}