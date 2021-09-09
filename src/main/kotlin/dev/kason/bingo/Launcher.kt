package dev.kason.bingo

import dev.kason.bingo.cards.CardView
import dev.kason.bingo.cards.generateNumbersWith
import dev.kason.bingo.ui.LoadingView
import dev.kason.bingo.ui.Styles
import dev.kason.bingo.ui.TestView
import dev.kason.bingo.ui.runImmediately
import dev.kason.bingo.util.startEventLoop
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    launch<BingoApp>(args)
}

class BingoApp : App(LoadingView::class, Styles::class) {
    init {
        runImmediately(CardView(generateNumbersWith()))
        startEventLoop()
        reloadViewsOnFocus()
        reloadStylesheetsOnFocus()
        addStageIcon(Image("Icon.png"))
    }

    override fun start(stage: Stage) {
        stage.onCloseRequest = EventHandler {
            println("On close request received.")
            Platform.exit()
            exitProcess(0)
        }
        super.start(stage)
    }
}