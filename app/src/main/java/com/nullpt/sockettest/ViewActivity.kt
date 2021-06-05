package com.nullpt.sockettest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.nullpt.sockets.log

class ViewActivity : AppCompatActivity() {

    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val view = View(this)
        handler.post {
            log { "1" }
        }
        runOnUiThread {
            log { "2" }
        }
        view.post {
            log { "3" }
        }

        findViewById<ViewGroup>(R.id.parent).addView(view)
    }
}