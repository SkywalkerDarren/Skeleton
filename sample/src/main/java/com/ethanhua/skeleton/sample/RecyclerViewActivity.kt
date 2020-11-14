package com.ethanhua.skeleton.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Direction
import com.ethanhua.skeleton.RepeatMode
import com.ethanhua.skeleton.Shape
import com.ethanhua.skeleton.Skeleton.bind
import com.ethanhua.skeleton.SkeletonScreen
import com.ethanhua.skeleton.sample.adapter.NewsAdapter
import com.ethanhua.skeleton.sample.adapter.PersonAdapter

/**
 * Created by ethanhua on 2017/7/27.
 */
class RecyclerViewActivity : AppCompatActivity() {
    private var mType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        mType = intent.getStringExtra(PARAMS_TYPE)
        init()
    }

    private fun init() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        if (TYPE_LINEAR == mType) {
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = NewsAdapter()
            val skeletonScreen: SkeletonScreen = bind(recyclerView)
                    .adapter(adapter)
                    .shimmer(true)
                    .angle(20)
                    .direction(Direction.LEFT_TO_RIGHT)
                    .frozen(false)
                    .duration(1200)
                    .count(10)
                    .load(R.layout.item_skeleton_news)
                    .repeatMode(RepeatMode.RESTART)
                    .shape(Shape.LINEAR)
                    .build()
                    .show() //default count is 10
            recyclerView.postDelayed({ skeletonScreen.hide() }, 3000)
            return
        }
        if (TYPE_GRID == mType) {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
            val adapter = PersonAdapter()
            val skeletonScreen: SkeletonScreen = bind(recyclerView)
                    .adapter(adapter)
                    .load(R.layout.item_skeleton_person)
                    .shimmer(false)
                    .build()
                    .show()
            recyclerView.postDelayed({ skeletonScreen.hide() }, 3000)
        }
    }

    companion object {
        private const val PARAMS_TYPE = "params_type"
        const val TYPE_LINEAR = "type_linear"
        const val TYPE_GRID = "type_grid"
        fun start(context: Context, type: String?) {
            val intent = Intent(context, RecyclerViewActivity::class.java)
            intent.putExtra(PARAMS_TYPE, type)
            context.startActivity(intent)
        }
    }
}