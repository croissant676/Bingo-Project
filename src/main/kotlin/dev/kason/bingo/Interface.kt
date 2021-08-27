package dev.kason.bingo

import javafx.geometry.Pos
import javafx.scene.effect.Reflection
import tornadofx.*

class LoadingView : View("Bingo Project > Loading") {
    override val root = vbox {
        button("Hello") {
            addClass(Styles.button)
            hover()
            action {
                replaceWith(BingoView::class)
            }
        }
        label("Loading...") {
            addClass(Styles.titleLabel)
        }
        progressindicator {
            effect = Reflection(15.0, 0.75, 0.5, 0.0)
            addClass(Styles.progressIndicator)
        }
        minHeight = 300.0
        minWidth = 500.0
        alignment = Pos.CENTER
        spacing = 10.0
        style {
            backgroundColor += c("f5faff")
        }
    }
}

class BingoView : View("Bingo Project > Bingo") {
    override val root = gridpane {
        for (row in 0 until 5) {
            for (col in 0 until 5) {
                add(button {
                    addClass(Styles.button)
                    hover()
                }, row, col)
            }
        }
    }
}