package com.ethanhua.skeleton

import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ethanhua on 2017/7/29.
 */
class RecyclerViewSkeletonScreen private constructor(builder: Builder) : SkeletonScreen {
    private val mRecyclerView: RecyclerView
    private val mActualAdapter: RecyclerView.Adapter<*>?
    private val mSkeletonAdapter: SkeletonAdapter
    private val mRecyclerViewFrozen: Boolean
    override fun show(): RecyclerViewSkeletonScreen {
        mRecyclerView.adapter = mSkeletonAdapter
        if (!mRecyclerView.isComputingLayout && mRecyclerViewFrozen) {
            mRecyclerView.suppressLayout(true)
        }
        return this
    }

    override fun hide() {
        mRecyclerView.adapter = mActualAdapter
    }

    class Builder(val mRecyclerView: RecyclerView) {
        var mActualAdapter: RecyclerView.Adapter<*>? = null
        var mShimmer = true
        var mItemCount = 10
        var mItemResID = R.layout.layout_default_item_skeleton
        var mItemsResIDArray: IntArray = IntArray(0)
        var mShimmerColor: Int
        var mShimmerDuration = 1000
        var mShimmerAngle = 20
        var mFrozen = true

        /**
         * @param adapter the target recyclerView actual adapter
         */
        fun adapter(adapter: RecyclerView.Adapter<*>?): Builder {
            mActualAdapter = adapter
            return this
        }

        /**
         * @param itemCount the child item count in recyclerView
         */
        fun count(itemCount: Int): Builder {
            mItemCount = itemCount
            return this
        }

        /**
         * @param shimmer whether show shimmer animation
         */
        fun shimmer(shimmer: Boolean): Builder {
            mShimmer = shimmer
            return this
        }

        /**
         * the duration of the animation , the time it will take for the highlight to move from one end of the layout
         * to the other.
         *
         * @param shimmerDuration Duration of the shimmer animation, in milliseconds
         */
        fun duration(shimmerDuration: Int): Builder {
            mShimmerDuration = shimmerDuration
            return this
        }

        /**
         * @param shimmerColor the shimmer color
         */
        fun color(@ColorRes shimmerColor: Int): Builder {
            mShimmerColor = ContextCompat.getColor(mRecyclerView.context, shimmerColor)
            return this
        }

        /**
         * @param shimmerAngle the angle of the shimmer effect in clockwise direction in degrees.
         */
        fun angle(@IntRange(from = 0, to = 30) shimmerAngle: Int): Builder {
            mShimmerAngle = shimmerAngle
            return this
        }

        /**
         * @param skeletonLayoutResID the loading skeleton layoutResID
         */
        fun load(@LayoutRes skeletonLayoutResID: Int): Builder {
            mItemResID = skeletonLayoutResID
            return this
        }

        /**
         * @param skeletonLayoutResIDs the loading array of skeleton layoutResID
         */
        fun loadArrayOfLayouts(@ArrayRes skeletonLayoutResIDs: IntArray): Builder {
            mItemsResIDArray = skeletonLayoutResIDs
            return this
        }

        /**
         * @param frozen whether frozen recyclerView during skeleton showing
         * @return
         */
        fun frozen(frozen: Boolean): Builder {
            mFrozen = frozen
            return this
        }

        fun build(): RecyclerViewSkeletonScreen {
            return RecyclerViewSkeletonScreen(this)
        }

        /**
         * build and show skeleton screen
         */
        @Deprecated("use build() then show()")
        fun show(): RecyclerViewSkeletonScreen {
            val recyclerViewSkeleton = RecyclerViewSkeletonScreen(this)
            recyclerViewSkeleton.show()
            return recyclerViewSkeleton
        }

        init {
            mShimmerColor = ContextCompat.getColor(mRecyclerView.context, R.color.shimmer_color)
        }
    }

    init {
        mRecyclerView = builder.mRecyclerView
        mActualAdapter = builder.mActualAdapter
        mSkeletonAdapter = SkeletonAdapter().apply {
            itemCount = builder.mItemCount
            layoutReference = builder.mItemResID
            layoutArrayReferences = builder.mItemsResIDArray
            shimmer = builder.mShimmer
            color = builder.mShimmerColor
            shimmerAngle = builder.mShimmerAngle
            shimmerDuration = builder.mShimmerDuration
        }
        mRecyclerViewFrozen = builder.mFrozen
    }
}