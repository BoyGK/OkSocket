package com.nullpt.sockets.intercept

import com.nullpt.sockets.params.SocketRequest
import com.nullpt.sockets.params.SocketResponse

interface Interceptor {

    fun station(chain: Chain)

    fun response(response: SocketResponse)

    interface Chain {

        fun request(): SocketRequest

        fun response(): (response: SocketResponse) -> Unit

        fun proceed(request: SocketRequest, response: (response: SocketResponse) -> Unit)

    }

}