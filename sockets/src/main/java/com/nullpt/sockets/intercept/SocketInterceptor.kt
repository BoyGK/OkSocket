package com.nullpt.sockets.intercept

import com.nullpt.sockets.Config
import com.nullpt.sockets.log
import com.nullpt.sockets.params.SocketRequest
import com.nullpt.sockets.params.SocketResponse
import com.nullpt.sockets.strategy.ProtocolStrategyFactory
import com.nullpt.sockets.utils.SocketExecutors
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.util.*

/**
 * 事件流重点
 * 专注于处理网络连接和数据交换
 */
class SocketInterceptor : Interceptor {

    /**
     * 网络连接唯一socket
     */
    private var socket = Socket()

    /**
     * socket默认尝试连接次数
     */
    private var tryConnectTimes = 5

    /**
     * 上游
     */
    private lateinit var upChain: Interceptor.Chain

    /**
     * 作为数据流的终点，不可调用chain.proceed(request)继续传递
     */
    override fun station(chain: Interceptor.Chain) {
        upChain = chain


        if (chain.request().protocolId == SocketRequest.PROTOCOL_RECONNECT) {
            log { "心跳重连" }
            if (socket.isConnected) {
                close()
            }
            socket = Socket()
            tryConnectTimes = 5
        }

        if (!socket.isConnected) {
            log { "创建链接" }
            log { "至多尝试${tryConnectTimes}次" }
            while ((!socket.isConnected && !socket.isClosed) && tryConnectTimes > 0) {
                try {
                    socket.connect(InetSocketAddress(Config.ip, Config.port), Config.timeOut)
                    log { "创建连接成功" }
                } catch (ignore: IOException) {
                    tryConnectTimes--
                }
            }
            if (!socket.isConnected) {
                log { "创建链接失败" }
                chain.response().invoke(
                    SocketResponse(
                        SocketResponse.PROTOCOL_ERROR, ByteArray(0), SocketResponse.ERROR
                    )
                )
                return
            }
            log { "开启数据接收" }
            SocketExecutors.executeByNow(ReaderWork(socket.getInputStream(), ::response))
        }

        log { "发送数据" }
        WriterWork.obtain(socket.getOutputStream(), chain.request()).run()
    }

    override fun response(response: SocketResponse) {
        upChain.response().invoke(response)
    }

    /**
     * 关闭当前连接
     */
    private fun close() {
        if (!socket.isOutputShutdown) {
            try {
                socket.shutdownOutput()
            } catch (ignore: IOException) {
            }
        }
        if (!socket.isInputShutdown) {
            try {
                socket.shutdownInput()
            } catch (ignore: IOException) {
            }
        }
        try {
            socket.close()
        } catch (ignore: IOException) {
        }
    }

    /**
     * 发送数据任务
     */
    class WriterWork(
        private var outputStream: OutputStream,
        private var request: SocketRequest
    ) : Runnable {

        companion object {
            /**
             * 发送消息任务集合
             */
            private val writerWorks = LinkedList<WriterWork>()

            /**
             * 获取任务对象
             */
            fun obtain(outputStream: OutputStream, request: SocketRequest): WriterWork {
                synchronized(writerWorks) {
                    if (writerWorks.isNotEmpty()) {
                        //复用发送任务对象
                        val runnable = writerWorks.removeFirst()
                        runnable.outputStream = outputStream
                        runnable.request = request
                        return runnable
                    }
                }
                //创建发送任务对象
                return WriterWork(outputStream, request)
            }
        }

        /**
         * 辅助数据封装
         */
        private val outputBufferStream = ByteArrayOutputStream()

        /**
         * 协议处理策略
         */
        private val protocolStrategy = ProtocolStrategyFactory.getStrategy()

        override fun run() {
            if (request.body.isEmpty()) {
                log { "校验数据异常-无数据发送" }
                return
            }
            try {
                protocolStrategy.createRequestBody(
                    request.protocolId,
                    request.body,
                    outputBufferStream
                )
                log { "发送数据:protocolId=${request.protocolId}" }
                outputStream.write(outputBufferStream.toByteArray())
                outputStream.flush()
            } catch (e: Exception) {
                if (Config.DEBUG) {
                    e.printStackTrace()
                }
                log { "发送数据异常:protocolId=${request.protocolId}" }
            } finally {
                outputBufferStream.reset()
                writerWorks.add(this)
            }
        }


    }

    /**
     * 接收数据任务
     */
    class ReaderWork(
        private val inputStream: InputStream,
        private val response: (response: SocketResponse) -> Unit
    ) : Runnable {
        /**
         * 协议处理策略
         */
        private val protocolStrategy = ProtocolStrategyFactory.getStrategy()

        override fun run() {
            while (true) {
                try {
                    protocolStrategy.parseResponseBody(inputStream) { protocolId, body ->
                        log { "响应分发:protocolId=$protocolId" }
                        response(SocketResponse(protocolId, body, SocketResponse.OK))
                    }
                } catch (e: Exception) {
                    if (Config.DEBUG) {
                        e.printStackTrace()
                    }
                    log { "响应处理任务异常" }
                    break
                }
            }
        }

    }

}