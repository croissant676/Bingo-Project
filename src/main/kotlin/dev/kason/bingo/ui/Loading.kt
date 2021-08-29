package dev.kason.bingo.ui

import dev.kason.bingo.control.BingoState
import dev.kason.bingo.control.currentState
import javafx.geometry.Pos
import javafx.scene.effect.Reflection
import tornadofx.*

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
        addClass(Styles.defaultBackground)
        style {
            alignment = Pos.CENTER
            spacing = 10.px
        }
        minHeight = 300.0
        minWidth = 500.0
    }

    override fun onDock() {
        isCurrentlyLoading = true
        currentLoadingView = this
        if (currentState == BingoState.LOADING) {
            setViewFromLoading(BingoMenu)
        }
    }

    override fun onUndock() {
        isCurrentlyLoading = false
    }
}