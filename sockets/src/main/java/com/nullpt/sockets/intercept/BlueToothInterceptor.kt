package com.nullpt.sockets.intercept

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import com.nullpt.sockets.Config
import com.nullpt.sockets.params.SocketRequest
import com.nullpt.sockets.params.SocketResponse
import com.nullpt.sockets.strategy.ProtocolStrategyFactory
import com.nullpt.sockets.utils.SocketExecutors
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

/**
 * 蓝牙连接协议
 */
class BlueToothInterceptor : Interceptor {

    companion object {
        // Name for the SDP record when creating server socket
        private const val NAME_SECURE = "BluetoothSecure"
        private const val NAME_INSECURE = "BluetoothInsecure"

        // Unique UUID for this application
        private val MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66")
        private val MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
    }

    private val adapter = BluetoothAdapter.getDefaultAdapter()

    @Volatile
    private var isConnected = false

    /**
     * 上游
     */
    private lateinit var upChain: Interceptor.Chain
    private lateinit var device: BluetoothDevice
    private var secure: Boolean = true

    private var acceptWork: AcceptWork? = null
    private var connectWork: ConnectWork? = null
    private var connectedWork: ConnectedWork? = null

    override fun station(chain: Interceptor.Chain) {
        upChain = chain

        val request = chain.request()
        when (request.protocolId) {
            SocketRequest.PROTOCOL_BLUETOOTH_CONNECT -> {
                device = request.device ?: return
                secure = request.secure ?: return
                connectedWork?.cancel()
                connectedWork = null
                connectWork?.cancel()
                connectWork = null
                connectWork = ConnectWork(::response)
                connectWork?.let {
                    SocketExecutors.executeByNow(it)
                }
            }
            SocketRequest.PROTOCOL_BLUETOOTH_SERVICE -> {
                secure = request.secure ?: return
                connectedWork?.cancel()
                connectedWork = null
                acceptWork?.cancel()
                acceptWork = null
                acceptWork = AcceptWork(::response)
                acceptWork?.let {
                    SocketExecutors.executeByNow(it)
                }
            }
            else -> {
                if (request.protocolId > 0 && isConnected) {
                    connectedWork?.write(request)
                }
            }
        }
    }

    override fun response(response: SocketResponse) {
        upChain.response().invoke(response)
    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    inner class AcceptWork(private val response: (response: SocketResponse) -> Unit) : Runnable {

        // The local server socket
        private var serverSocket: BluetoothServerSocket? = null

        init {
            // Create a new listening server socket
            try {
                serverSocket = if (secure) {
                    adapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE)
                } else {
                    adapter.listenUsingInsecureRfcommWithServiceRecord(
                        NAME_INSECURE,
                        MY_UUID_INSECURE
                    )
                }
            } catch (e: IOException) {
                if (Config.DEBUG) {
                    e.printStackTrace()
                }
                error()
            }
        }

        override fun run() {
            val localServerSocket = serverSocket ?: return

            var socket: BluetoothSocket?

            // Listen to the server socket if we're not connected
            while (!isConnected) {
                socket = try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    localServerSocket.accept()
                } catch (e: IOException) {
                    if (Config.DEBUG) {
                        e.printStackTrace()
                    }
                    error()
                    break
                }

                socket?.let {
                    synchronized(this@BlueToothInterceptor) {
                        if (isConnected) {
                            try {
                                it.close()
                            } catch (e: IOException) {
                                if (Config.DEBUG) {
                                    e.printStackTrace()
                                }
                            }
                            error("already connected one")
                        } else {
                            // Start the connected thread
                            connectedWork?.cancel()
                            connectedWork = null
                            connectedWork = ConnectedWork(socket, response)
                            connectedWork?.let {
                                SocketExecutors.executeByNow(it)
                            }
                            return
                        }
                    }
                }
            }
        }

        fun cancel() {
            try {
                serverSocket?.close()
            } catch (e: IOException) {
                if (Config.DEBUG) {
                    e.printStackTrace()
                }
            }
        }

        private fun error(msg: String = "") {
            response(
                SocketResponse(
                    SocketResponse.PROTOCOL_BLUETOOTH_SERVICE,
                    msg.toByteArray(),
                    SocketResponse.ERROR
                )
            )
        }
    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    inner class ConnectWork(private val response: (response: SocketResponse) -> Unit) : Runnable {

        private var socket: BluetoothSocket? = null

        init {
            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                socket = if (secure) {
                    device.createRfcommSocketToServiceRecord(MY_UUID_SECURE)
                } else {
                    device.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE)
                }
            } catch (e: IOException) {
                if (Config.DEBUG) {
                    e.printStackTrace()
                }
                error()
            }
        }

        override fun run() {
            val localSocket = socket ?: return

            // Always cancel discovery because it will slow down a connection
            adapter.cancelDiscovery()

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                if (!isConnected) {
                    localSocket.connect()
                } else {
                    error("already connected one")
                }
            } catch (e: IOException) {
                if (Config.DEBUG) {
                    e.printStackTrace()
                }
                // Close the socket
                try {
                    localSocket.close()
                } catch (e2: IOException) {
                    if (Config.DEBUG) {
                        e2.printStackTrace()
                    }
                }
                error()
                return
            }

            synchronized(this@BlueToothInterceptor) {
                if (!isConnected) {
                    // Start the connected thread
                    connectedWork?.cancel()
                    connectedWork = null
                    connectedWork = ConnectedWork(localSocket, response)
                    connectedWork?.let {
                        SocketExecutors.executeByNow(it)
                    }
                } else {
                    error("already connected one")
                }
            }
        }

        fun cancel() {
            try {
                socket?.close()
            } catch (e: IOException) {
                if (Config.DEBUG) {
                    e.printStackTrace()
                }
            }
        }

        private fun error(msg: String = "") {
            response(
                SocketResponse(
                    SocketResponse.PROTOCOL_BLUETOOTH_CONNECT,
                    msg.toByteArray(),
                    SocketResponse.ERROR
                )
            )
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    inner class ConnectedWork(
        private val socket: BluetoothSocket,
        private val response: (response: SocketResponse) -> Unit
    ) : Runnable {

        /**
         * 协议处理策略
         */
        private val protocolStrategy = ProtocolStrategyFactory.getStrategy()

        private val outputBufferStream = ByteArrayOutputStream()

        private var inStream: InputStream? = null
        private var outStream: OutputStream? = null

        init {
            isConnected = true
            response(
                SocketResponse(
                    SocketResponse.PROTOCOL_BLUETOOTH_CONNECTED,
                    byteArrayOf(),
                    SocketResponse.OK
                )
            )
            // Get the BluetoothSocket input and output streams
            try {
                inStream = socket.inputStream
                outStream = socket.outputStream
            } catch (e: IOException) {
                if (Config.DEBUG) {
                    e.printStackTrace()
                }
            }
        }


        override fun run() {

            val localInputStream = inStream ?: return

            // Keep listening to the InputStream while connected
            while (isConnected) {
                try {
                    // Read from the InputStream
                    protocolStrategy.parseResponseBody(localInputStream) { protocolId, body ->
                        response(SocketResponse(protocolId, body, SocketResponse.OK))
                    }
                } catch (e: IOException) {
                    if (Config.DEBUG) {
                        e.printStackTrace()
                    }
                    break
                }
            }
        }

        /**
         * Write to the connected OutStream.
         *
         * @param request The request to write
         */
        fun write(request: SocketRequest) {
            val localOutStream = outStream ?: return
            if (request.body.isEmpty()) {
                return
            }
            try {
                protocolStrategy.createRequestBody(
                    request.protocolId,
                    request.body,
                    outputBufferStream
                )
                localOutStream.write(outputBufferStream.toByteArray())
                localOutStream.flush()
            } catch (e: Exception) {
                if (Config.DEBUG) {
                    e.printStackTrace()
                }
            } finally {
                outputBufferStream.reset()
            }
        }

        fun cancel() {
            try {
                socket.close()
            } catch (e: IOException) {
                if (Config.DEBUG) {
                    e.printStackTrace()
                }
            } finally {
                isConnected = false
            }
        }

    }

}