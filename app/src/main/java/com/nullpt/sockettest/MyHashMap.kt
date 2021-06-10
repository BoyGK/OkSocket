package com.nullpt.sockettest

import java.io.Closeable
import java.io.Serializable

class MyHashMap<K, V> : Map<K, V> by HashMap<K, V>(), Closeable, Serializable {

    override fun close() {

    }

}