package dev.kason.bingo

import dev.kason.bingo.ui.LoadingView
import dev.kason.bingo.ui.Styles
import dev.kason.bingo.util.initializeLoop
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    println(Runtime.getRuntime().maxMemory())
    println(Runtime.getRuntime().freeMemory())
    launch<BingoApp>(args)
}

class BingoApp : App(LoadingView::class, Styles::class) {
    init {
        reloadViewsOnFocus()
        reloadStylesheetsOnFocus()
        initializeLoop()
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