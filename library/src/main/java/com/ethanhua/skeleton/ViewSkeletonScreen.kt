package com.ethanhua.skeleton

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

/**
 * Created by ethanhua on 2017/7/29.
 */
class ViewSkeletonScreen private constructor(builder: Builder) : SkeletonScreen {
    private val mViewReplacer = ViewReplacer(builder.mView)
    private val mActualView = builder.mView
    private val mUseAlpha = builder.mUseAlpha
    private val mShimmerAngle = builder.mShimmerAngle
    private val mSkeletonResID = builder.mSkeletonLayoutResID
    private val mShimmerColor = builder.mShimmerColor
    private val mShimmerBaseColor = builder.mShimmerBaseColor
    private val mShimmerAlpha = builder.mShimmerAlpha
    private val mShimmerBaseAlpha = builder.mShimmerBaseAlpha
    private val mShimmer = builder.mShimmer
    private val mShimmerDuration = builder.mShimmerDuration
    private val mShimmerDirection = builder.mShimmerDirection
    private val mShimmerShape = builder.mShimmerShape
    private val mRepeatCount = builder.mRepeatCount
    private val mRepeatDelay = builder.mRepeatDelay
    private val mRepeatMode = builder.mRepeatMode
    private fun generateShimmerContainerLayout(parentView: ViewGroup): ShimmerFrameLayout {
        val shimmerLayout = LayoutInflater.from(mActualView.context).inflate(R.layout.layout_shimmer, parentView, false) as ShimmerFrameLayout
        val shimmer = if (mUseAlpha) {
            Shimmer.AlphaHighlightBuilder()
                    .setBaseAlpha(mShimmerBaseAlpha)
                    .setHighlightAlpha(mShimmerAlpha)
        } else {
            Shimmer.ColorHighlightBuilder()
                    .setBaseColor(mShimmerBaseColor)
                    .setHighlightColor(mShimmerColor)
        }.apply {
            setTilt(mShimmerAngle.toFloat())
            setShape(mShimmerShape.value)
            setDirection(mShimmerDirection.value)
            setDuration(mShimmerDuration)
            setRepeatCount(mRepeatCount)
            setRepeatDelay(mRepeatDelay)
            setRepeatMode(mRepeatMode.value)
        }.build()
        shimmerLayout.setShimmer(shimmer)
        val innerView = LayoutInflater.from(mActualView.context).inflate(mSkeletonResID, shimmerLayout, false)
        innerView.layoutParams?.let {
            shimmerLayout.layoutParams = it
        }
        shimmerLayout.addView(innerView)
        shimmerLayout.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                shimmerLayout.startShimmer()
            }

            override fun onViewDetachedFromWindow(v: View) {
                shimmerLayout.stopShimmer()
            }
        })
        shimmerLayout.startShimmer()
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

    override fun show(): ViewSkeletonScreen {
        val skeletonLoadingView = generateSkeletonLoadingView()
        if (skeletonLoadingView != null) {
            mViewReplacer.replace(skeletonLoadingView)
        }
        return this
    }

    override fun hide() {
        (mViewReplacer.targetView as? ShimmerFrameLayout)?.stopShimmer()
        mViewReplacer.restore()
    }

    class Builder(val mView: View) {
        internal var mSkeletonLayoutResID = 0
        internal var mShimmer = true
        internal var mUseAlpha = true
        internal var mShimmerBaseAlpha = 0.4f
        internal var mShimmerAlpha = 1f
        internal var mShimmerColor: Int
        internal var mShimmerBaseColor: Int
        internal var mShimmerDuration = 1000L
        internal var mShimmerDirection = Direction.LEFT_TO_RIGHT
        internal var mShimmerAngle = 30
        internal var mShimmerShape = Shape.LINEAR
        internal var mRepeatCount = -1
        internal var mRepeatDelay = 0L
        internal var mRepeatMode = RepeatMode.RESTART

        /**
         * @param skeletonLayoutResID the loading skeleton layoutResID
         */
        fun load(@LayoutRes skeletonLayoutResID: Int): Builder {
            mSkeletonLayoutResID = skeletonLayoutResID
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

        fun baseColor(@ColorRes shimmerBaseColor: Int): Builder {
            mShimmerBaseColor = ContextCompat.getColor(mView.context, shimmerBaseColor)
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
        fun duration(shimmerDuration: Long): Builder {
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

        fun direction(direction: Direction): Builder {
            mShimmerDirection = direction
            return this
        }

        fun setUseAlpha(use: Boolean): Builder {
            mUseAlpha = use
            return this
        }

        fun shape(shape: Shape): Builder {
            mShimmerShape = shape
            return this
        }

        fun repeatCount(repeatCount: Int): Builder {
            mRepeatCount = repeatCount
            return this
        }

        fun repeatMode(repeatMode: RepeatMode): Builder {
            mRepeatMode = repeatMode
            return this
        }

        fun repeatDelay(repeatDelay: Long): Builder {
            mRepeatDelay = repeatDelay
            return this
        }

        fun build(): ViewSkeletonScreen {
            return ViewSkeletonScreen(this)
        }

        /**
         * build and show skeleton screen
         */
        @Deprecated("use build() then show()")
        fun show(): ViewSkeletonScreen {
            val skeletonScreen = ViewSkeletonScreen(this)
            skeletonScreen.show()
            return skeletonScreen
        }

        init {
            mShimmerColor = ContextCompat.getColor(mView.context, R.color.shimmer_color)
            mShimmerBaseColor = ContextCompat.getColor(mView.context, R.color.shimmer_base_color)
        }
    }

    companion object {
        private val TAG = ViewSkeletonScreen::class.java.name
    }
}