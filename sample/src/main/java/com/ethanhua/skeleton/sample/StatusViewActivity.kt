package com.ethanhua.skeleton.sample

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ethanhua.skeleton.ViewReplacer
import kotlinx.android.synthetic.main.activity_status_view.*

class StatusViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_view)
        val viewReplacer = ViewReplacer(tv_content)
        btn_loading.setOnClickListener {
            viewReplacer.replace(R.layout.layout_progress)
        }
        btn_error.setOnClickListener {
            viewReplacer.replace(R.layout.layout_error)
        }
        btn_empty.setOnClickListener {
            viewReplacer.replace(R.layout.layout_empty_view)
        }
        btn_content.setOnClickListener {
            viewReplacer.restore()
        }
    }

    fun gotoSet(view: View) {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
    }
}