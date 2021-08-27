package dev.kason.bingo

import javafx.geometry.Pos
import javafx.scene.effect.Reflection
import javafx.scene.paint.Color
import tornadofx.*

class LoadingView : View("Bingo Project > Loading") {
    override val root = vbox {
        button("Hello") {
            addClass(Styles.button)
            hover()
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
        style {
            backgroundColor += c("f5faff")
        }
    }
}