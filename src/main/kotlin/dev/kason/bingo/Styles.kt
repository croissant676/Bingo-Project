package dev.kason.bingo

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val button by cssclass()
        val lightButton by cssclass()
        val titleLabel by cssclass()
        val progressIndicator by cssclass()
    }

    init {
        button {
            textFill = Color.WHITE
            backgroundColor += c("0483ff")
            padding = box(1.px, 12.px)
            borderWidth += box(1.px, 3.px)
            borderRadius += box(0.px)
            backgroundRadius += box(0.px)
            borderColor += box(c("0076ea"))
            fontFamily = "dubai"
            fontSize = 17.px
        }
        lightButton {
            textFill = Color.WHITE
            backgroundColor += c("8bc5ff")
            padding = box(1.px, 12.px)
            borderWidth += box(1.px, 3.px)
            borderRadius += box(0.px)
            backgroundRadius += box(0.px)
            borderColor += box(c("8bbbff"))
            fontFamily = "dubai"
            fontSize = 17.px
        }
        titleLabel {
            textFill = c("151554")
            padding = box(10.px)
            fontFamily = "dubai"
            fontSize = 25.px
        }
        progressIndicator {
            accentColor = c("0483ff")
        }
    }
}