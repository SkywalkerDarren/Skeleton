# Skeleton

[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
![GitHub Action](http://img.shields.io/github/workflow/status/SkywalkerDarren/Skeleton/Android%20CI)
The library provides an easy way to show skeleton loading view like Facebook and Alipay. 
It now uses a memory optimised version of shimmer animation so it is even faster and you can animate bigger layouts as well.

# Preview

![img](screenshots/01.gif)
![img](screenshots/02.gif)
![img](screenshots/03.gif)
![img](screenshots/04.gif)

# Feature

- Light
- Noninvasive, you don't need to make changes to existing code.
- Wide applicability，it is available for all views
- Memory optimised

# Getting started

In your build.gradle:
```
dependencies {
    implementation 'com.skywalkerdarren:skeleton:2.0.1'
}
```


# Usage

For RecyclerView:
```kotlin
skeletonScreen = Skeleton.bind(recyclerView)
                            .adapter(adapter)                        // your actual adapter
                            .load(R.layout.item_skeleton_news)       // skeleton item layout
                            .build()
                            .show()
``` 


For View:
```kotlin
skeletonScreen = Skeleton.bind(rootView)
                            .load(R.layout.layout_img_skeleton)
                            .build()
                            .show()
```


 ```kotlin
  .shimmer(true)                      // whether show shimmer animation.                       default is true
  .useAlpha(true)                     // use alpha mode or color mode.                         default is true
  .baseAlpha(0.4f)                    // the shimmer base alpha.                               default is 0.4f
  .alpha(1f)                          // the shimmer highlight alpha.                          default is 1f
  .baseColor(color)                   // the shimmer base color.                               default is #80878787
  .color(color)                       // the shimmer color.                                    default is #a2878787
  .shape(Shape.LINEAR)                // the shimmer shape.                                    default is Shape.LINEAR
  .angle(20)                          // the shimmer angle.                                    default is 20;
  .direction(Direction.LEFT_TO_RIGHT) // the shimmer animation direction.                      default is Direction.LEFT_TO_RIGHT
  .duration(1000L)                    // the shimmer animation duration.                       default is 1000L
  .repeatMode(RepeatMode.RESTART)     // the animation repeat mode.                            default is RepeatMode.RESTART
  .repeatCount(-1)                    // the animation repeat count.(-1 is infinite)           default is -1
  .repeatDelay(0L)                    // delay in between repeats of the shimmering animation. default is 0L

// if you use recycler view, you should call these first 
  .adapter(adapter)                   // your actual adapter
  .count(10)                          // the recycler view item count.                         default is 10
  .frozen(false)                      // whether frozen recyclerView during skeleton showing   default is true
 ```


when data return you can call the method to hide skeleton loading view 
```kotlin
skeletonScreen.hide()
```


 # Thanks

https://github.com/facebook/shimmer-android
