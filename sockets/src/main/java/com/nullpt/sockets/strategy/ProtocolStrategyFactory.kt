package com.nullpt.sockets.strategy

/**
 * 协议解析策略工厂
 */
object ProtocolStrategyFactory {

    private val strategys = mutableListOf<ProtocolStrategy>()

    init {
        strategys.add(DefaultProtocolStrategy())
    }

    /**
     * 首位填充策略
     */
    fun addStrategy(strategy: ProtocolStrategy) {
        strategys.add(0, strategy)
    }

    /**
     * 获取策略
     */
    fun getStrategy(): ProtocolStrategy {
        return strategys[0]
    }
}