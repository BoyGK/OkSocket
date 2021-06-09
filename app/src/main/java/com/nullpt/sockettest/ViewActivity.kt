package com.nullpt.sockettest

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nullpt.sockets.log

class ViewActivity : AppCompatActivity() {

    val handler = Handler(Looper.getMainLooper())

    //轻轻的你来了，点个赞再走呗~
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)


        val bgq = findViewById<BGQView>(R.id.bgq)
        bgq.setOnClickListener {
            Toast.makeText(this, "123456", Toast.LENGTH_SHORT).show()
        }

//        val view = View(this)
//        handler.post {
//            log { "1" }
//        }
//        runOnUiThread {
//            log { "2" }
//        }
//        view.post {
//            log { "3" }
//        }
//
//        findViewById<ViewGroup>(R.id.parent).addView(view)

//        val intent = Intent(this, MainActivity::class.java)
//        val pendingIntent =
//            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        pendingIntent.send()
    }
}