package com.nullpt.sockets

import android.util.Log

internal object Config {

    const val TAG = "socket-"
    const val DEBUG = true

    var ip = "10.160.2.251"
    var port = 6789
    var timeOut = 10000

    var heartBody = byteArrayOf(0)
    var heartTime = 10000

    var connection = SocketClient.Connection.SOCKET
}

fun log(message: () -> String) {
    Log.d(Config.TAG, message.invoke())
}