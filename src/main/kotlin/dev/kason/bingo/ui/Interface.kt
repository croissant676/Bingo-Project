@file:Suppress("DuplicatedCode")

package dev.kason.bingo.ui

import dev.kason.bingo.util.addHoverEffect
import javafx.geometry.Pos
import tornadofx.*

object BingoMenu : View("Bingo Project > Bingo") {
    override val root = borderpane {
        center {
            vbox {
                button("Create Bingo Game") {
                    addClass(Styles.button)
                    addHoverEffect()
                    action {
                        println("Creating Bingo Game")
                    }
                    useMaxSize = true
                }
                button("Settings") {
                    addClass(Styles.button)
                    addHoverEffect()
                    action {
                        println("Pressed Settings")
                    }
                    useMaxSize = true
                }
                button("How to use") {
                    addClass(Styles.button)
                    addHoverEffect()
                    action {
                        println("Pressed HTU")
                    }
                    useMaxSize = true
                }
                spacing = 10.0
                maxWidth = 250.0
                alignment = Pos.CENTER
            }
        }
        addClass(Styles.defaultBackground)
        minHeight = 700.0
        minWidth = 500.0
    }

    override fun onDock() {

    }
}
