@file:Suppress("SpellCheckingInspection")

package dev.kason.bingo

import dev.kason.bingo.cards.CardView
import dev.kason.bingo.cards.EditingCardView
import dev.kason.bingo.cards.currentGame
import dev.kason.bingo.cards.exporting.ExportCompleted
import dev.kason.bingo.cards.exporting.generateString
import dev.kason.bingo.cards.generateNumbers
import dev.kason.bingo.ui.LoadingView
import dev.kason.bingo.ui.Styles
import dev.kason.bingo.ui.runImmediately
import dev.kason.bingo.util.startEventLoop
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch
import tornadofx.reloadStylesheetsOnFocus
import tornadofx.reloadViewsOnFocus
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    startEventLoop()
    launch<BingoApp>(args)
}

class BingoApp : App(LoadingView::class, Styles::class) {
    init {
//        runImmediately(CardView(generateNumbers().first()))
        generateNumbers(100, 10)
        println(generateString(currentGame))
        runImmediately(EditingCardView)
//        runImmediately(ExportCompleted("sdf", {}))
        reloadViewsOnFocus()
        reloadStylesheetsOnFocus()
    }

    override fun start(stage: Stage) {
        stage.onCloseRequest = EventHandler {
            println("On close request received.")
            Platform.exit()
            exitProcess(0)
        }
        stage.isResizable = true
        // Set the size so the views don't resize it later
//        stage.minWidth = 800.0
//        stage.minHeight = 600.0
//        stage.maxWidth = 800.0
//        stage.maxHeight = 600.0
        super.start(stage)
    }
}

// lateinit var scene: Scene
//    private set