package com.nullpt.sockets.intercept

import com.nullpt.sockets.Config
import com.nullpt.sockets.params.SocketResponse
import com.nullpt.sockets.utils.SocketExecutors
import java.util.concurrent.LinkedBlockingQueue

/**
 * 处理线程调度
 */
class ThreadDispatchInterceptor : Interceptor, Runnable {

    /**
     * 上游
     */
    private lateinit var upChain: Interceptor.Chain

    /**
     * 响应式事件，一个一个切换
     */
    private val responseWorks = LinkedBlockingQueue<SocketResponse>(1)

    override fun station(chain: Interceptor.Chain) {
        upChain = chain
        chain.proceed(chain.request(), ::response)
    }

    override fun response(response: SocketResponse) {
        try {
            responseWorks.put(response)
            SocketExecutors.executeByMain(this)
        } catch (e: Exception) {
            if (Config.DEBUG) {
                e.printStackTrace()
            }
        }
    }

    override fun run() {
        try {
            upChain.response().invoke(responseWorks.take())
        } catch (e: Exception) {
            if (Config.DEBUG) {
                e.printStackTrace()
            }
        }
    }
}