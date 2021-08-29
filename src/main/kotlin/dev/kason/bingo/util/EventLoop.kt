package dev.kason.bingo.util

import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

private var runEventLoop = true
val eventLoopBlocks: MutableList<EventBlock> = mutableListOf()

private var executionTime = 0L
private var optimizationTimer = 0

private val eventLoopThread = object : Thread() {

    init {
        isDaemon = true
        name = "Event loop thread"
    }

    override fun run() {
        while (true) {
            while (runEventLoop) {
                if (optimizationTimer >= 256) {
                    optimizationTimer = -1
                    runOptimizations()
                } else {
                    runOnce()
                }
                waitTime()
                optimizationTimer++
            }
        }
    }

    private fun runOnce() {
        var index = 0
        while (index in eventLoopBlocks.indices) {
            if (!eventLoopBlocks[index]()) {
                eventLoopBlocks.removeAt(index)
                index--
            }
            index++
        }
    }

    private fun runOptimizations() {
        Runtime.getRuntime().gc()
        executionTime = measureTimeMillis {
            runOnce()
        }
        println("Optimizations finished in time = $executionTime.")
    }

    private fun waitTime() = sleep(15 - executionTime)

    override fun toString(): String {
        return "Event Loop Thread: state= {alive=$isAlive, inter=$isInterrupted} }"
    }
}

fun initializeLoop() {
    eventLoopThread.start()
}

fun restartLoop() {
    runEventLoop = true
}

fun stopEventLoop() {
    runEventLoop = false
}

class EventBlock(private val block: EventBlock.() -> Unit) {

    private var stayInLoop = true

    fun exitLoop() {
        println("Exiting loop for block.")
        stayInLoop = false
    }

    operator fun invoke(): Boolean {
        block(this)
        return stayInLoop
    }

    override fun toString(): String {
        return "EventBlock{block=EventBlock.() -> Unit}"
    }
}

fun runInsideLoop(block: EventBlock.() -> Unit) {
    eventLoopBlocks += EventBlock(block)
}