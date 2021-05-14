package com.nullpt.sockettest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nullpt.sockets.*
import com.nullpt.sockets.params.SocketRequest
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //测试仅注册
        registerResponse(2) {
            log { "2 : ${Thread.currentThread().name}\n ${String(it.body)}" }
        }
    }

    fun click(view: View) {
        //测试并行
        thread {
            submitRequest(SocketRequest(1, "client 1 ".toByteArray())) {
                log { "1 : ${Thread.currentThread().name}\n ${String(it.body)}" }
            }
        }
        thread {
            submitRequest(SocketRequest(2, "client 2 ".toByteArray()))
        }
        thread {
            submitRequest(SocketRequest(3, "client 3 ".toByteArray())) {
                log { "3 : ${Thread.currentThread().name}\n ${String(it.body)}" }
            }
        }
        //测试线程调度
        executeRequest(SocketRequest(8, "client 8 ".toByteArray())) {
            log { "8 : ${Thread.currentThread().name}\n ${String(it.body)}" }
        }
    }
}