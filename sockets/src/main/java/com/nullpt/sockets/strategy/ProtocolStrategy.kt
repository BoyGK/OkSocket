package com.nullpt.sockets.strategy

import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 *协议解析策略
 */
interface ProtocolStrategy {

    /**
     * 创建请求数据体
     */
    fun createRequestBody(
        protocolId: Int,
        body: ByteArray,
        byteArrayOutputStream: ByteArrayOutputStream
    )

    /**
     * 解析请求数据体
     */
    fun parseResponseBody(
        inputStream: InputStream,
        callback: (protocolId: Int, body: ByteArray) -> Unit
    )
}