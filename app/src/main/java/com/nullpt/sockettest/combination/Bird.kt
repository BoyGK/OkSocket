package com.nullpt.sockettest.combination

/**
 *
 */
class Bird1 : Fly, Eat, Run {
    //组合
    private val defaultFly = DefaultFly()

    override fun fly() {
        defaultFly.fly()
    }

    override fun eat() {
        TODO("Not yet implemented")
    }

    override fun run() {
        TODO("Not yet implemented")
    }


}

//委托
class Bird2 : Fly by DefaultFly(), Eat, Run {

    override fun run() {
        TODO("Not yet implemented")
    }


}
