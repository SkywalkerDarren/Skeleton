package com.ethanhua.skeleton

import androidx.annotation.*
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ethanhua on 2017/7/29.
 */
class RecyclerViewSkeletonScreen private constructor(builder: Builder) : SkeletonScreen {
    private val mRecyclerView = builder.mRecyclerView
    private val mActualAdapter = builder.mActualAdapter
    private val mSkeletonAdapter = SkeletonAdapter().apply {
        useAlpha = builder.mUseAlpha
        itemCount = builder.mItemCount
        layoutReference = builder.mItemResID
        layoutArrayReferences = builder.mItemsResIDArray
        shimmer = builder.mShimmer
        alpha = builder.mShimmerAlpha
        baseAlpha = builder.mShimmerBaseAlpha
        color = builder.mShimmerColor
        baseColor = builder.mShimmerBaseColor
        shimmerAngle = builder.mShimmerAngle
        shimmerDuration = builder.mShimmerDuration
        direction = builder.mDirection
    }
    private val mRecyclerViewFrozen = builder.mFrozen
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
        internal var mActualAdapter: RecyclerView.Adapter<*>? = null
        internal var mUseAlpha: Boolean = true
        internal var mShimmer = true
        internal var mDirection = Direction.LEFT_TO_RIGHT
        internal var mItemCount = 10
        internal var mItemResID = R.layout.layout_default_item_skeleton
        internal var mItemsResIDArray: IntArray = IntArray(0)
        internal var mShimmerBaseAlpha = 0.4f
        internal var mShimmerAlpha = 1f
        internal var mShimmerColor: Int
        internal var mShimmerBaseColor: Int
        internal var mShimmerDuration = 1000L
        internal var mShimmerAngle = 20
        internal var mFrozen = true

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
        fun duration(shimmerDuration: Long): Builder {
            mShimmerDuration = shimmerDuration
            return this
        }

        fun baseColor(@ColorRes color: Int): Builder {
            mShimmerBaseColor = ContextCompat.getColor(mRecyclerView.context, color)
            return this
        }

        /**
         * @param shimmerColor the shimmer color
         */
        fun color(@ColorRes shimmerColor: Int): Builder {
            mShimmerColor = ContextCompat.getColor(mRecyclerView.context, shimmerColor)
            return this
        }

        fun direction(direction: Direction): Builder {
            mDirection = direction
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

        fun setUseAlpha(use: Boolean): Builder {
            mUseAlpha = use
            return this
        }

        fun baseAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): Builder {
            mShimmerBaseAlpha = alpha
            return this
        }

        fun alpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): Builder {
            mShimmerAlpha = alpha
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
            mShimmerBaseColor = ContextCompat.getColor(mRecyclerView.context, R.color.shimmer_base_color)
        }
    }
}