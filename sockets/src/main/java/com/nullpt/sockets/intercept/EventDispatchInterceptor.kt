package com.nullpt.sockets.intercept

import com.nullpt.sockets.params.SocketRequest
import com.nullpt.sockets.params.SocketResponse

/**
 * 响应事件分发
 */
class EventDispatchInterceptor : Interceptor {

    /**
     * 基于事件协议id，存储对应的响应回调
     */
    private val eventMap = LinkedHashMap<Int, MutableSet<(response: SocketResponse) -> Unit>>()

    /**
     * 特定协议号不需要注册响应
     */
    private val unProtocolIds = mutableListOf(SocketRequest.PROTOCOL_RECONNECT)

    override fun station(chain: Interceptor.Chain) {
        val request = chain.request()
        val protocolId = request.protocolId

        if (!unProtocolIds.contains(protocolId)) {
            val responseCall = chain.response()
            if (eventMap[protocolId] == null) {
                eventMap[protocolId] = mutableSetOf(responseCall)
            } else {
                eventMap[protocolId]!!.add(responseCall)
            }
        }

        chain.proceed(request, ::response)
    }

    override fun response(response: SocketResponse) {

        val protocolId = response.protocolId

        eventMap[protocolId]?.let {
            it.forEach { responseCall ->
                responseCall.invoke(response)
            }
        }
    }

}