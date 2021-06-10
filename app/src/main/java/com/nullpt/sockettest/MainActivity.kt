package com.nullpt.sockettest

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.SnapHelper
import com.nullpt.sockets.*
import com.nullpt.sockets.params.SocketRequest
import com.nullpt.sockets.params.SocketResponse
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        //测试仅注册socket消息
//        registerResponse(2) {
//            log { "2 : ${Thread.currentThread().name}\n ${String(it.body)}" }
//        }


        ArrayList<String>()
        Collections.enumeration<String>(mutableListOf("1"))
        //蓝牙测试
        val client = SocketClient.Builder()
            .connection(SocketClient.Connection.BLUETOOTH)
            .build()
        connectSocket(client)

        //连接成功都会回调这里
        registerResponse(SocketResponse.PROTOCOL_BLUETOOTH_CONNECTED) {
            log { "bluetooth connected" }
        }

    }

    override fun onStart() {
        super.onStart()
        //开启蓝牙
        val blAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!blAdapter.isEnabled) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivity(enableIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        //开启蓝牙监听，允许其他设备连接
        val startBlSer =
            SocketRequest(SocketRequest.PROTOCOL_BLUETOOTH_SERVICE, byteArrayOf(), secure = true)
        submitRequest(startBlSer) {
            log { String(it.body) }
        }
    }

    fun click(view: View) {
//        //测试并行socket发送
//        thread {
//            submitRequest(SocketRequest(1, "client 1 ".toByteArray())) {
//                log { "1 : ${Thread.currentThread().name}\n ${String(it.body)}" }
//            }
//        }
//        thread {
//            submitRequest(SocketRequest(2, "client 2 ".toByteArray()))
//        }
//        thread {
//            submitRequest(SocketRequest(3, "client 3 ".toByteArray())) {
//                log { "3 : ${Thread.currentThread().name}\n ${String(it.body)}" }
//            }
//        }
//        //测试线程调度
//        executeRequest(SocketRequest(8, "client 8 ".toByteArray())) {
//            log { "8 : ${Thread.currentThread().name}\n ${String(it.body)}" }
//        }

        //连接目标设备蓝牙
        val blAdapter = BluetoothAdapter.getDefaultAdapter()
        val devices = mutableListOf<BluetoothDevice>()
        blAdapter.bondedDevices.forEach {
            devices.add(it)
        }
        val startBlCon =
            SocketRequest(
                SocketRequest.PROTOCOL_BLUETOOTH_CONNECT,
                byteArrayOf(),
                device = devices[0],//假定连接第一个
                secure = true
            )
        submitRequest(startBlCon) {
            log { String(it.body) }
        }
    }
}