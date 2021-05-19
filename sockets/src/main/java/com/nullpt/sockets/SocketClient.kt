package com.nullpt.sockets

import com.nullpt.sockets.params.SocketRequest
import com.nullpt.sockets.params.SocketResponse
import com.nullpt.sockets.utils.SocketExecutors
import java.lang.IllegalArgumentException
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicInteger

/**
 * socket client
 */
class SocketClient : Runnable {

    /**
     * socket发送数据管理
     */
    private val socketNetWork = SocketNetWork()

    /**
     * 请求任务队列
     */
    private val requestWorks =
        LinkedBlockingQueue<Pair<SocketRequest, (response: SocketResponse) -> Unit>>()

    init {
        log { "初始化心跳" }
        SocketExecutors.executeByBlock(HeartWork(socketNetWork))
    }

    /**
     * 同步执行
     * 阻塞
     */
    fun submit(request: SocketRequest, response: (response: SocketResponse) -> Unit) {
        requestWorks.put(request to response)
        this.run()
    }

    /**
     * 异步执行
     * 回调为main thread
     */
    fun execute(request: SocketRequest, response: (response: SocketResponse) -> Unit) {
        requestWorks.put(request to response)
        SocketExecutors.executeByNow(this)
    }

    override fun run() {
        val pair = requestWorks.take()
        socketNetWork.send(pair.first, pair.second)
    }

    /**
     * SocketClient构建者
     */
    class Builder {

        fun ip(ip: String): Builder {
            Config.ip = ip
            return this
        }

        fun port(port: Int): Builder {
            Config.port = port
            return this
        }

        fun timeout(timeout: Int): Builder {
            Config.timeOut = timeout
            return this
        }

        fun heartBody(body: ByteArray): Builder {
            if (body.isEmpty()) {
                throw IllegalArgumentException("body must not be empty")
            }
            Config.heartBody = body
            return this
        }

        fun heartTime(time: Int): Builder {
            Config.heartTime = time
            return this
        }

        fun connection(connection: Connection): Builder {
            Config.connection = connection
            return this
        }

        fun build(): SocketClient {
            return SocketClient()
        }
    }

    enum class Connection {
        /**
         * Socket
         */
        SOCKET,

        /**
         * bluetooth
         */
        BLUETOOTH,

        /**
         * bluetooth le
         */
        BLUETOOTH_LE
    }

    /**
     * 心跳事件处理
     */
    private class HeartWork(private val socketNetWork: SocketNetWork) : Runnable {

        /**
         * 心跳包偏移量
         */
        private var offset = AtomicInteger(0)

        private var lock = Object()

        override fun run() {
            while (true) {
                val heartRequest = SocketRequest(SocketRequest.PROTOCOL_HEART, Config.heartBody)
                socketNetWork.send(heartRequest, ::response)
                val off = offset.incrementAndGet()
                if (off > 3) {
                    log { "心跳偏移量大于3，重连" }
                    offset.set(0)
                    val reconnectRequest = SocketRequest.default(SocketRequest.PROTOCOL_RECONNECT)
                    socketNetWork.send(reconnectRequest) {}
                }

                synchronized(lock) {
                    try {
                        lock.wait(Config.heartTime.toLong())
                    } catch (e: Exception) {
                        if (Config.DEBUG) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        private fun response(response: SocketResponse) {
            if (Config.DEBUG && response.protocolId != SocketResponse.PROTOCOL_HEART) {
                log { "心跳响应协议错误，EventDispatchInterceptor出现异常" }
                return
            }
            val off = offset.decrementAndGet()
            if (off < 0) {
                log { "心跳偏移量小于0，发送过程可能出现过重连或异常" }
                offset.set(0)
            }
        }

    }

}