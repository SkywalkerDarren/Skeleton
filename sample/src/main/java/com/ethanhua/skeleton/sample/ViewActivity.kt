package com.ethanhua.skeleton.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.ethanhua.skeleton.RepeatMode
import com.ethanhua.skeleton.Shape
import com.ethanhua.skeleton.Skeleton.bind
import com.ethanhua.skeleton.SkeletonScreen
import com.ethanhua.skeleton.sample.adapter.TopicAdapter
import kotlinx.android.synthetic.main.activity_view.*
import java.lang.ref.WeakReference

class ViewActivity : AppCompatActivity() {
    private var skeletonScreen: SkeletonScreen? = null

    class MyHandler internal constructor(activity: ViewActivity) : Handler() {
        private val activityWeakReference: WeakReference<ViewActivity> = WeakReference(activity)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            activityWeakReference.get()?.let {
                it.skeletonScreen?.hide()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        val type = intent.getStringExtra(PARAMS_TYPE)
        val adapter = TopicAdapter()
        recyclerView.adapter = adapter
        if (TYPE_VIEW == type) {
            skeletonScreen = bind(rootView)
                    .load(R.layout.activity_view_skeleton)
                    .duration(1000)
                    .baseAlpha(0.1f)
                    .alpha(1f)
                    .angle(30)
                    .repeatMode(RepeatMode.REVERSE)
                    .shape(Shape.LINEAR)
                    .build()
                    .show()
        }
        if (TYPE_IMG_LOADING == type) {
            skeletonScreen = bind(rootView)
                    .load(R.layout.layout_img_skeleton)
                    .duration(1000)
                    .alpha(1f)
                    .build()
                    .show()
        }
        val myHandler = MyHandler(this)
        myHandler.sendEmptyMessageDelayed(1, 5000)
    }

    companion object {
        private const val PARAMS_TYPE = "params_type"
        const val TYPE_IMG_LOADING = "type_img"
        const val TYPE_VIEW = "type_view"
        fun start(context: Context, type: String) {
            val intent = Intent(context, ViewActivity::class.java)
            intent.putExtra(PARAMS_TYPE, type)
            context.startActivity(intent)
        }
    }
}