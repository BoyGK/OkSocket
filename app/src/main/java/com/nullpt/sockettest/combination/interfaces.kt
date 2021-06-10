package com.nullpt.sockettest.combination

interface Fly {

    fun fly()
}

interface Eat {

    fun eat() {
        println("default eat")
    }
}

interface Run {

    fun run()
}

class DefaultFly : Fly {
    override fun fly() {
        println("default fly")
    }

}