package com.nullpt.sockets.intercept

import com.nullpt.sockets.params.SocketRequest
import com.nullpt.sockets.params.SocketResponse


class RealCallInterceptorChain(
    private var request: SocketRequest,
    private var response: (response: SocketResponse) -> Unit,
    private var interceptors: List<Interceptor>,
    private var index: Int
) : Interceptor.Chain {

    constructor() : this(SocketRequest.default(0), {}, listOf(), 0)

    fun init(
        request: SocketRequest,
        response: (response: SocketResponse) -> Unit,
        interceptors: List<Interceptor>,
        index: Int
    ) {
        this.request = request
        this.response = response
        this.interceptors = interceptors
        this.index = index
    }

    override fun request(): SocketRequest {
        return request
    }

    override fun response(): (response: SocketResponse) -> Unit {
        return response
    }

    override fun proceed(request: SocketRequest, response: (response: SocketResponse) -> Unit) {
        val intercept: Interceptor = interceptors[index]
        val chain = RealCallInterceptorChain(request, response, interceptors, index + 1)
        intercept.station(chain)
    }

}