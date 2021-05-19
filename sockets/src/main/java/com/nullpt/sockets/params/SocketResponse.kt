package com.nullpt.sockets.params

/**
 * 数据响应类
 */
data class SocketResponse(
    val protocolId: Int,
    val body: ByteArray,
    val code: Int
) {

    companion object {

        /**
         * 保留协议号：蓝牙连接成功
         */
        const val PROTOCOL_BLUETOOTH_CONNECTED = -5

        /**
         * 保留协议号：蓝牙连接
         */
        const val PROTOCOL_BLUETOOTH_CONNECT = -4

        /**
         * 保留协议号：蓝牙服务
         */
        const val PROTOCOL_BLUETOOTH_SERVICE = -3

        /**
         * 保留协议号：失败
         */
        const val PROTOCOL_ERROR = -1

        /**
         * 保留协议号：心跳
         */
        const val PROTOCOL_HEART = 0

        /**
         * 状态码：成功
         */
        const val OK = 100

        /**
         * 状态码：失败
         */
        const val ERROR = 101

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocketResponse

        if (protocolId != other.protocolId) return false
        if (!body.contentEquals(other.body)) return false
        if (code != other.code) return false

        return true
    }

    override fun hashCode(): Int {
        var result = protocolId
        result = 31 * result + body.contentHashCode()
        result = 31 * result + code
        return result
    }

}