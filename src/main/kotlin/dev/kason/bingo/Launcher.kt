@file:Suppress("SpellCheckingInspection")

package dev.kason.bingo

import dev.kason.bingo.cards.EditingCardView
import dev.kason.bingo.cards.currentGame
import dev.kason.bingo.cards.exporting.generateImageForCard
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
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    startEventLoop()
    launch<BingoApp>(args)
}

class BingoApp : App(LoadingView::class, Styles::class) {
    init {
        generateNumbers(12332, 100)
        runImmediately(EditingCardView)
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
        super.start(stage)
    }
}