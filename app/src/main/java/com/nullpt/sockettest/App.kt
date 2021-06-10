package com.nullpt.sockettest

import android.app.Application
import com.nullpt.sockets.SocketClient
import com.nullpt.sockets.connectSocket

class App : Application() {

    override fun onCreate() {
        super.onCreate()
//        connectSocket(SocketClient.Builder())
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }

}