package dev.kason.bingo

import dev.kason.bingo.cards.CardView
import dev.kason.bingo.cards.generateNumbers
import dev.kason.bingo.ui.LoadingView
import dev.kason.bingo.ui.Styles
import dev.kason.bingo.ui.runImmediately
import dev.kason.bingo.util.startEventLoop
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.stage.Stage
import tornadofx.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    launch<BingoApp>(args)
}

class BingoApp : App(LoadingView::class, Styles::class) {
    init {
        runImmediately(CardView(generateNumbers().first()))
        startEventLoop()
        reloadViewsOnFocus()
        reloadStylesheetsOnFocus()
    }

    override fun start(stage: Stage) {
        stage.onCloseRequest = EventHandler {
            println("On close request received.")
            Platform.exit()
            exitProcess(0)
        }
        stage.isResizable = false
        // Set the size so the views don't resize it later
//        stage.minWidth = 800.0
//        stage.minHeight = 600.0
//        stage.maxWidth = 800.0
//        stage.maxHeight = 600.0
        super.start(stage)
    }
}

lateinit var scene: Scene
    private set