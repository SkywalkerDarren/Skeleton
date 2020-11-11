package com.ethanhua.skeleton

/**
 * @author darren
 * @date 20-11-11
 */
class SkeletonGroup: SkeletonScreen {
    private val skeletonSet = mutableSetOf<SkeletonScreen>()

    fun addSkeleton(skeleton: SkeletonScreen) {
        skeletonSet.add(skeleton)
    }

    override fun show() {
        skeletonSet.forEach {
            it.show()
        }
    }

    override fun hide() {
        skeletonSet.forEach {
            it.hide()
        }
    }
}