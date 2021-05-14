package com.nullpt.sockets.params

data class SocketRequest(
    val protocolId: Int,
    val body: ByteArray
) {
    companion object {
        /**
         * 保留协议号：重连
         */
        const val PROTOCOL_RECONNECT = -2

        /**
         * 保留协议号：心跳
         */
        const val PROTOCOL_HEART = 0

        /**
         * 默认请求
         */
        fun default(protocolId: Int) = SocketRequest(protocolId, ByteArray(0))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocketRequest

        if (protocolId != other.protocolId) return false
        if (!body.contentEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = protocolId
        result = 31 * result + body.contentHashCode()
        return result
    }
}