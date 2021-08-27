package dev.kason.bingo

import javafx.geometry.Pos
import tornadofx.*

class LoadingView : View("Bingo Project > Loading") {
    override val root = vbox {
        button("Hello") {
            addClass(Styles.button)
            hover()
            action {
                style (append = true) {
                    backgroundColor += c("005cb7")
                    borderColor += box(c("005cb7"))
                }
            }
        }
        label("Loading...") {
            addClass(Styles.titleLabel)
        }
        progressindicator {
            addClass(Styles.progressIndicator)
        }
        minHeight = 200.0
        minWidth = 400.0
        alignment = Pos.CENTER
    }
}