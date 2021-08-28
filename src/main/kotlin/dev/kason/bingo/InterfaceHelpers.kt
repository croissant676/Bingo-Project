@file:Suppress("DuplicatedCode")

package dev.kason.bingo

import javafx.scene.control.Button
import javafx.scene.paint.Color
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
//                println("Optimizations finished in time = $executionTime.")
            }
            sleep((16 - executionTime).toLong())
        }
    }
}

fun initializeLoop() {
    eventLoopThread.start()
}

private fun radialGradient(index: Int, newColor: Color, mainColor: Color): RadialGradient {
    return RadialGradient(
        0.0, 0.0, 0.5, -0.4,
        1.25, true, CycleMethod.NO_CYCLE,
        Stop(0.01, newColor), Stop(index / 30.0, newColor),
        Stop(index / 30.0 + 1 / 1000, mainColor), Stop(1.0, mainColor)
    )
}

fun Button.addHoverEffect(newColor: Color = c("0076ea"), originalColor: Color = c("0483ff")) {
    var state = 0
    runInsideLoop {
        if (isHover && state <= 35) {
            state++
        } else {
            if (state > 0) {
                state -= 2
            }
        }
        runLater {
            style {
                if (state in 1 until 30) {
                    backgroundColor += radialGradient(state, newColor, originalColor)
                } else if (state >= 30) {
                    backgroundColor += newColor
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