package com.nullpt.server

import java.io.ByteArrayOutputStream
import java.net.ServerSocket
import kotlin.concurrent.thread

fun main() {

    val server = ServerSocket(6789)
    while (true) {
        val socket = server.accept()
        thread {
            val inputStream = socket.getInputStream()
            val outputStream = socket.getOutputStream()

            val lengthBuffer = ByteArray(4)
            while (inputStream.read(lengthBuffer, 0, 4) != -1) {
                val length = bytesToInt(lengthBuffer)

                val protocolIdBuffer = ByteArray(4)
                inputStream.read(protocolIdBuffer, 0, 4)

                val body = ByteArray(length)
                inputStream.read(body, 0, length)
                println("protocolId:${bytesToInt(protocolIdBuffer)},${String(body)}")

                val resbody = "this is Server response".toByteArray()
                val lenBuffer = intToBytes(resbody.size)
                val osbf = ByteArrayOutputStream()
                osbf.write(lenBuffer)
                osbf.write(protocolIdBuffer)
                osbf.write(resbody)
                outputStream.write(osbf.toByteArray())
                outputStream.flush()
                osbf.close()
            }
        }
    }


}

/**
 * 将int数值转换为占四个字节的byte数组
 */
private fun intToBytes(value: Int): ByteArray {
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
private fun bytesToInt(src: ByteArray): Int {
    return ((src[0].toInt() and 0xFF) or
            ((src[1].toInt() and 0xFF) shl 8) or
            ((src[2].toInt() and 0xFF) shl 16) or
            ((src[3].toInt() and 0xFF) shl 24))
}