package dev.kason.bingo

import tornadofx.App
import tornadofx.launch
import tornadofx.reloadStylesheetsOnFocus
import tornadofx.reloadViewsOnFocus
import java.io.OutputStream
import java.io.PrintStream

fun main(args: Array<String>) {
    launch<BingoApp>(args)
}

class BingoApp: App(LoadingView::class, Styles::class) {
    init {
        initializeLoop()
        reloadViewsOnFocus()
        reloadStylesheetsOnFocus()
    }
}