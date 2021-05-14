package com.nullpt.sockets.strategy

import com.nullpt.sockets.log
import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 * 默认协议解析处理方式
 *
 * 数据交换格式：
 * [0,4:bodyLength;4,8:protocolId;8,8+bodyLength:body]
 */
open class DefaultProtocolStrategy : ProtocolStrategy {

    override fun createRequestBody(
        protocolId: Int,
        body: ByteArray,
        byteArrayOutputStream: ByteArrayOutputStream
    ) {
        log { "请求协议字段拼接" }
        val lengthBuffer = intToBytes(body.size)
        val protocolIdBuffer = intToBytes(protocolId)
        byteArrayOutputStream.write(lengthBuffer)
        byteArrayOutputStream.write(protocolIdBuffer)
        byteArrayOutputStream.write(body)
    }

    override fun parseResponseBody(
        inputStream: InputStream,
        callback: (protocolId: Int, body: ByteArray) -> Unit
    ) {
        log { "读取长度并转换为int" }
        val lengthBuffer = ByteArray(4)
        inputStream.read(lengthBuffer, 0, 4)
        val length = bytesToInt(lengthBuffer)

        log { "读取协议号并转换为int" }
        val protocolIdBuffer = ByteArray(4)
        inputStream.read(protocolIdBuffer, 0, 4)
        val protocolId = bytesToInt(protocolIdBuffer)

        log { "按长度读取数据" }
        val body = ByteArray(length)
        inputStream.read(body, 0, length)

        log { "响应解析完毕" }
        callback(protocolId, body)
    }

    /**
     * 将int数值转换为占四个字节的byte数组
     */
    protected fun intToBytes(value: Int): ByteArray {
        val src = ByteArray(4)
        src[0] = ((value and 0xFF).toByte())
        src[1] = (((value shr 8) and 0xFF).toByte())
        src[2] = (((value shr 16) and 0xFF).toByte())
        src[3] = (((value shr 24) and 0xFF).toByte())
        return src
    }

    /**
     * byte数组中取int数值
     */
    protected fun bytesToInt(src: ByteArray): Int {
        return ((src[0].toInt() and 0xFF) or
                ((src[1].toInt() and 0xFF) shl 8) or
                ((src[2].toInt() and 0xFF) shl 16) or
                ((src[3].toInt() and 0xFF) shl 24))
    }

}