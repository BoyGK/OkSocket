package com.nullpt.sockets

import com.nullpt.sockets.params.SocketRequest
import com.nullpt.sockets.params.SocketResponse

private var socketClient: SocketClient? = null

/**
 * 开启长连接服务
 */
fun connectSocket(client: SocketClient) {
    socketClient = client
}

/**
 * 仅发送长连接数据
 */
fun executeRequest(request: SocketRequest) {
    socketClient?.execute(request) {}
}

/**
 * 发送长连接数据，且接收回复数据
 */
fun executeRequest(request: SocketRequest, response: (socketResponse: SocketResponse) -> Unit) {
    socketClient?.execute(request, response)
}

/**
 * 仅发送长连接数据
 */
fun submitRequest(request: SocketRequest) {
    socketClient?.submit(request) {}
}

/**
 * 发送长连接数据，且接收回复数据
 */
fun submitRequest(request: SocketRequest, response: (socketResponse: SocketResponse) -> Unit) {
    socketClient?.submit(request, response)
}

/**
 * 注册回复数据接收
 */
fun registerResponse(protocolId: Int, response: (socketResponse: SocketResponse) -> Unit) {
    socketClient?.execute(SocketRequest.default(protocolId), response)
}