package com.ethanhua.skeleton

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import io.supercharge.shimmerlayout.ShimmerLayout

/**
 * Created by ethanhua on 2017/7/29.
 */
class ViewSkeletonScreen private constructor(builder: Builder) : SkeletonScreen {
    private val mViewReplacer: ViewReplacer
    private val mActualView: View
    private val mSkeletonResID: Int
    private val mShimmerColor: Int
    private val mShimmer: Boolean
    private val mShimmerDuration: Int
    private val mShimmerAngle: Int
    private fun generateShimmerContainerLayout(parentView: ViewGroup): ShimmerLayout {
        val shimmerLayout = LayoutInflater.from(mActualView.context).inflate(R.layout.layout_shimmer, parentView, false) as ShimmerLayout
        shimmerLayout.setShimmerColor(mShimmerColor)
        shimmerLayout.setShimmerAngle(mShimmerAngle)
        shimmerLayout.setShimmerAnimationDuration(mShimmerDuration)
        val innerView = LayoutInflater.from(mActualView.context).inflate(mSkeletonResID, shimmerLayout, false)
        val lp = innerView.layoutParams
        if (lp != null) {
            shimmerLayout.layoutParams = lp
        }
        shimmerLayout.addView(innerView)
        shimmerLayout.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                shimmerLayout.startShimmerAnimation()
            }

            override fun onViewDetachedFromWindow(v: View) {
                shimmerLayout.stopShimmerAnimation()
            }
        })
        shimmerLayout.startShimmerAnimation()
        return shimmerLayout
    }

    private fun generateSkeletonLoadingView(): View? {
        val viewParent = mActualView.parent
        if (viewParent == null) {
            Log.e(TAG, "the source view have not attach to any view")
            return null
        }
        val parentView = viewParent as ViewGroup
        return if (mShimmer) {
            generateShimmerContainerLayout(parentView)
        } else LayoutInflater.from(mActualView.context).inflate(mSkeletonResID, parentView, false)
    }

    override fun show() {
        val skeletonLoadingView = generateSkeletonLoadingView()
        if (skeletonLoadingView != null) {
            mViewReplacer.replace(skeletonLoadingView)
        }
    }

    override fun hide() {
        if (mViewReplacer.targetView is ShimmerLayout) {
            (mViewReplacer.targetView as ShimmerLayout?)!!.stopShimmerAnimation()
        }
        mViewReplacer.restore()
    }

    class Builder(val mView: View) {
        var mSkeletonLayoutResID = 0
        var mShimmer = true
        var mShimmerColor: Int
        var mShimmerDuration = 1000
        var mShimmerAngle = 20

        /**
         * @param skeletonLayoutResID the loading skeleton layoutResID
         */
        fun load(@LayoutRes skeletonLayoutResID: Int): Builder {
            mSkeletonLayoutResID = skeletonLayoutResID
            return this
        }

        /**
         * @param shimmerColor the shimmer color
         */
        fun color(@ColorRes shimmerColor: Int): Builder {
            mShimmerColor = ContextCompat.getColor(mView.context, shimmerColor)
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
         * @param shimmerAngle the angle of the shimmer effect in clockwise direction in degrees.
         */
        fun angle(@IntRange(from = 0, to = 30) shimmerAngle: Int): Builder {
            mShimmerAngle = shimmerAngle
            return this
        }

        fun show(): ViewSkeletonScreen {
            val skeletonScreen = ViewSkeletonScreen(this)
            skeletonScreen.show()
            return skeletonScreen
        }

        init {
            mShimmerColor = ContextCompat.getColor(mView.context, R.color.shimmer_color)
        }
    }

    companion object {
        private val TAG = ViewSkeletonScreen::class.java.name
    }

    init {
        mActualView = builder.mView
        mSkeletonResID = builder.mSkeletonLayoutResID
        mShimmer = builder.mShimmer
        mShimmerDuration = builder.mShimmerDuration
        mShimmerAngle = builder.mShimmerAngle
        mShimmerColor = builder.mShimmerColor
        mViewReplacer = ViewReplacer(builder.mView)
    }
}