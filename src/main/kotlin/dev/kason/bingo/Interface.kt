package dev.kason.bingo

import javafx.geometry.Pos
import javafx.scene.effect.Reflection
import tornadofx.*

private lateinit var loadingView: LoadingView

fun setViewFromLoading(view: View, transition: ViewTransition = ViewTransition.Slide(0.3.seconds)) {
    loadingView.replaceWith(view, transition, true, centerOnScreen = true)
}

class LoadingView : View("Bingo Project > Loading") {

    override val root = vbox {
        label("Loading...") {
            addClass(Styles.titleLabel)
        }
        progressindicator {
            effect = Reflection(15.0, 0.75, 0.5, 0.0)
            addClass(Styles.progressIndicator)
        }
        alignment = Pos.CENTER
        spacing = 10.0
        style {
            backgroundColor += c("f5faff")
        }
        minHeight = 300.0
        minWidth= 500.0
    }

    init {
        loadingView = this
    }

}

class BingoView : View("Bingo Project > Bingo") {
    override val root = vbox {
        this += button("Go Back") {
            addClass(Styles.button)
            addHoverEffect()
            action {
                replaceWith(loadingView, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.RIGHT))
            }
        }
        alignment = Pos.CENTER
        spacing = 10.0
        style {
            backgroundColor += c("f5faff")
        }
        minHeight = 300.0
        minWidth= 500.0
    }
}