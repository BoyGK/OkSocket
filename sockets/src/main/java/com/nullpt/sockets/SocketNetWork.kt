package com.nullpt.sockets

import com.nullpt.sockets.intercept.*
import com.nullpt.sockets.params.SocketRequest
import com.nullpt.sockets.params.SocketResponse
import java.util.*

/**
 * 长连接消息任务
 */
internal class SocketNetWork {

    /**
     * 数据流过滤器
     */
    private val interceptors = LinkedList<Interceptor>()

    /**
     * 真实的请求分发链
     */
    private val realCallInterceptorChain: RealCallInterceptorChain

    init {
        log { "请求过滤器拼接；链路初始化；" }
        interceptors += EventDispatchInterceptor()
        interceptors += ThreadDispatchInterceptor()
        interceptors += SocketInterceptor()
        realCallInterceptorChain = RealCallInterceptorChain()
    }

    /**
     * 整套法案送流程
     * 过程包含阻塞方法，可能引起阻塞
     */
    @Synchronized
    fun send(request: SocketRequest, response: (socketResponse: SocketResponse) -> Unit) {
        log { "开始执行发送数据流程" }
        realCallInterceptorChain.init(request, response, interceptors, 0)
        realCallInterceptorChain.proceed(request, response)
    }

}