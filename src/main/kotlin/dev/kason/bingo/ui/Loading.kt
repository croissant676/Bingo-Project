package dev.kason.bingo.ui

import dev.kason.bingo.control.BingoState
import dev.kason.bingo.control.currentState
import dev.kason.bingo.control.moveToNextState
import javafx.geometry.Pos
import javafx.scene.effect.Reflection
import tornadofx.*
import kotlin.concurrent.thread

var isCurrentlyLoading = true
    private set

private lateinit var currentLoadingView: LoadingView

fun setViewFromLoading(view: View, transition: ViewTransition? = ViewTransition.Slide(0.5.seconds)) {
    if (isCurrentlyLoading) {
        runLater {
            currentLoadingView.replaceWith(view, transition, true, centerOnScreen = true)
        }
    } else {
        throw IllegalArgumentException("Trying to change view from loading view when loading view is not active.")
    }
}

var immediateView: View? = null

fun runImmediately(view: View) {
    immediateView = view
}

@Suppress("MemberVisibilityCanBePrivate")
class LoadingView : View("Bingo Project > Loading") {

    override val root = vbox {
        label("Loading...") {
            addClass(Styles.titleLabel)
        }
        progressindicator {
            effect = Reflection(15.0, 0.75, 0.5, 0.0)
            addClass(Styles.progressIndicator)
        }
        style {
            alignment = Pos.CENTER
            spacing = 10.px
        }
        addClass(Styles.defaultBackground)
        minHeight = 300.0
        minWidth = 500.0
    }

    override fun onDock() {
        isCurrentlyLoading = true
        currentLoadingView = this
        if (immediateView != null) {
            setViewFromLoading(immediateView!!)
        }
        thread {
            FileView.indexFiles()
        }
        if (currentState == BingoState.LOADING) {
            moveToNextState() // Bingo Menu
            setViewFromLoading(BingoMenu)
        }
    }

    override fun onUndock() {
        isCurrentlyLoading = false
    }

}