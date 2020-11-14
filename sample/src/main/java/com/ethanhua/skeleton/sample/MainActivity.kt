package com.ethanhua.skeleton.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_list.setOnClickListener {
            RecyclerViewActivity.start(this, RecyclerViewActivity.TYPE_LINEAR)
        }
        btn_grid.setOnClickListener {
            RecyclerViewActivity.start(this, RecyclerViewActivity.TYPE_GRID)
        }
        btn_view.setOnClickListener {
            ViewActivity.start(this, ViewActivity.TYPE_VIEW)
        }
        btn_Imgloading.setOnClickListener {
            ViewActivity.start(this, ViewActivity.TYPE_IMG_LOADING)
        }
        btn_status.setOnClickListener {
            startActivity(Intent(this, StatusViewActivity::class.java))
        }
    }
}