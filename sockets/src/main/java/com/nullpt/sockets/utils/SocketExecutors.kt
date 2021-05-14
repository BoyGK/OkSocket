package com.nullpt.sockets.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

/**
 * 线程调度
 */
object SocketExecutors {

    /**
     * 默认异步执行
     */
    private val defaultExecutor = Executors.newCachedThreadPool()

    /**
     * 阻塞异步执行
     */
    private val blockExecutor = Executors.newSingleThreadExecutor()

    /**
     * 主线程执行
     */
    private val mainExecutor = Handler(Looper.getMainLooper())

    /**
     * 立刻执行
     */
    fun executeByNow(runnable: Runnable) {
        defaultExecutor.execute(runnable)
    }

    /**
     * 最多同时执行一个,阻塞
     */
    fun executeByBlock(runnable: Runnable) {
        blockExecutor.execute(runnable)
    }

    /**
     * 主线程实行
     */
    fun executeByMain(runnable: Runnable) {
        mainExecutor.post(runnable)
    }

}