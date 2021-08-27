@file:Suppress("DuplicatedCode")

package dev.kason.bingo

import javafx.scene.control.Button
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.RadialGradient
import javafx.scene.paint.Stop
import tornadofx.c
import tornadofx.runLater
import tornadofx.style
import kotlin.system.measureTimeMillis

var runEventLoop = true
val eventLoopBlocks: MutableList<EventBlock> = mutableListOf()

private var executionTime = 0
private var executionTimer = 0

private val eventLoopThread = object : Thread() {
    override fun run() {
        while (runEventLoop) {
            var index = 0
            while (index in eventLoopBlocks.indices) {
                if (!eventLoopBlocks[index]()) {
                    eventLoopBlocks.removeAt(index)
                    index--
                }
                index++
            }
            executionTimer++
            if (executionTimer >= 60) {
                sleep((16 - executionTime).toLong())
                Runtime.getRuntime().gc()
                index = 0 // Reset the timer
                executionTime = measureTimeMillis {
                    while (index in eventLoopBlocks.indices) {
                        if (!eventLoopBlocks[index]()) {
                            eventLoopBlocks.removeAt(index)
                            index--
                        }
                        index++
                    }
                }.toInt()
            }
            sleep((16 - executionTime).toLong())
        }
    }
}

fun initializeLoop() {
    eventLoopThread.start()
}

val gradients = List(30) { ratio ->
    RadialGradient(
        0.0, 0.0, 0.5, -0.4,
        2.0, true, CycleMethod.NO_CYCLE,
        Stop(0.01, c("0076ea")), Stop(ratio / 30.0, c("0076ea")),
        Stop(ratio / 30.0 + 1 / 1000, c("0483ff")), Stop(1.0, c("0483ff"))
    )
}

fun Button.hover() {
    var count = 0
    runInsideLoop {
        if (isHover && count <= 35) {
            count++
        } else {
            if (count > 0) {
                count -= 2
            }
        }
        runLater {
            style {
                backgroundColor += when (count) {
                    in 1 until 30 -> gradients[count]
                    in 30..40 ->  c("0076ea")
                    else -> c("0483ff")
                }
            }
        }
        if (!isVisible) {
            exitLoop()
        }
    }
}


class EventBlock(private val block: EventBlock.() -> Unit) {
    private var stayInLoop = true

    fun exitLoop() {
        stayInLoop = false
    }

    operator fun invoke(): Boolean {
        block(this)
        return stayInLoop
    }
}

fun runInsideLoop(block: EventBlock.() -> Unit) {
    eventLoopBlocks += EventBlock(block)
}

inline fun runOnceInsideLoop(crossinline block: () -> Unit) {
    eventLoopBlocks += EventBlock {
        block()
        exitLoop()
    }
}