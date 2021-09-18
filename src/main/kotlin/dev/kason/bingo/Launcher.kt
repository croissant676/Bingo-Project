@file:Suppress("SpellCheckingInspection")

package dev.kason.bingo

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    startEventLoop()
    launch<BingoApp>(args)
}

const val maxCardsSmooth = 50000

class BingoApp : App(LoadingView::class, Styles::class) {
    init {
        generateNumbers(12332, 1000)
        runImmediately(EditingCardView)
    }

    override fun start(stage: Stage) {
        stage.onCloseRequest = EventHandler {
            println("On close request received.")
            Platform.exit()
            exitProcess(0)
        }
        stage.isResizable = true
        super.start(stage)
    }
}