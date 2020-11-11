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
    private var mItemCount = 0
    private var mLayoutReference = 0
    private var mLayoutArrayReferences: IntArray = IntArray(0)
    private var mColor = 0
    private var mShimmer = false
    private var mShimmerDuration = 0
    private var mShimmerAngle = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (doesArrayOfLayoutsExist()) {
            mLayoutReference = viewType
        }
        return if (mShimmer) {
            ShimmerViewHolder(inflater, parent, mLayoutReference)
        } else object : ViewHolder(inflater.inflate(mLayoutReference, parent, false)) {}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mShimmer) {
            val layout = holder.itemView as ShimmerLayout
            layout.setShimmerAnimationDuration(mShimmerDuration)
            layout.setShimmerAngle(mShimmerAngle)
            layout.setShimmerColor(mColor)
            layout.startShimmerAnimation()
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

    override fun getItemCount(): Int {
        return mItemCount
    }

    fun setLayoutReference(layoutReference: Int) {
        mLayoutReference = layoutReference
    }

    fun setArrayOfLayoutReferences(layoutReferences: IntArray) {
        mLayoutArrayReferences = layoutReferences
    }

    fun setItemCount(itemCount: Int) {
        mItemCount = itemCount
    }

    fun setShimmerColor(color: Int) {
        mColor = color
    }

    fun shimmer(shimmer: Boolean) {
        mShimmer = shimmer
    }

    fun setShimmerDuration(shimmerDuration: Int) {
        mShimmerDuration = shimmerDuration
    }

    fun setShimmerAngle(@IntRange(from = 0, to = 30) shimmerAngle: Int) {
        mShimmerAngle = shimmerAngle
    }

    fun getCorrectLayoutItem(position: Int): Int {
        return if (doesArrayOfLayoutsExist()) {
            mLayoutArrayReferences[position % mLayoutArrayReferences.size]
        } else mLayoutReference
    }

    private fun doesArrayOfLayoutsExist(): Boolean {
        return mLayoutArrayReferences.isNotEmpty()
    }
}