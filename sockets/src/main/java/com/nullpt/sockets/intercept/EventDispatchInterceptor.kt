package com.nullpt.sockets.intercept

import com.nullpt.sockets.params.SocketResponse

/**
 * 响应事件分发
 */
class EventDispatchInterceptor : Interceptor {

    /**
     * 基于事件协议id，存储对应的响应回调
     */
    private val eventMap = LinkedHashMap<Int, MutableSet<(response: SocketResponse) -> Unit>>()

    override fun station(chain: Interceptor.Chain) {
        val request = chain.request()
        val protocolId = request.protocolId

        //大于等于0的协议，视为正常协议号
        if (protocolId >= 0) {
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

        //大于等于0的协议，视为正常协议号
        if (protocolId >= 0) {
            eventMap[protocolId]?.let {
                it.forEach { responseCall ->
                    responseCall.invoke(response)
                }
            }
        }
    }

}