package dev.kason.bingo

import tornadofx.App
import tornadofx.launch

fun main(args: Array<String>) {
    launch<BingoApp>(args)
}

class BingoApp: App(LoadingView::class, Styles::class) {
    init {
        initializeLoop()
    }
}