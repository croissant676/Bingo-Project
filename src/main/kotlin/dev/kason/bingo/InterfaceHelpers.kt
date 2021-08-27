@file:Suppress("DuplicatedCode")

package dev.kason.bingo

import javafx.scene.control.Button
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.RadialGradient
import javafx.scene.paint.Stop
import tornadofx.c
import tornadofx.runLater
import tornadofx.style

var runEventLoop = true

val eventLoopBlocks: MutableList<EventBlock> = mutableListOf()

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
            sleep(15)
        }
    }
}

fun initializeLoop() {
    eventLoopThread.start()
}

fun Button.hover() {
    var count = 0
    runInsideLoop {
        if (isHover) {
            count += 20
        } else {
            if (count > 0) count -= 40
        }
        runLater {
            style(append = true) {
                if (count in 10..540) {
                    val ratio = count / 1000.0 + 0.18
                    backgroundColor += RadialGradient(
                        0.0, 0.0, 0.5, -0.4,
                        2.0, true, CycleMethod.NO_CYCLE,
                        Stop(0.01, c("0076ea")), Stop(ratio, c("0076ea")),
                        Stop(ratio + 0.001, c("0483ff")), Stop(1.0, c("0483ff"))
                    )
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