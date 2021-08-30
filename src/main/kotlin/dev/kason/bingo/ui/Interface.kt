@file:Suppress("DuplicatedCode")

package dev.kason.bingo.ui

import dev.kason.bingo.control.Appearance
import dev.kason.bingo.control.moveToNextState
import dev.kason.bingo.control.moveToPreviousState
import dev.kason.bingo.util.addHoverEffect
import javafx.geometry.Pos
import tornadofx.*

object BingoMenu : View("Bingo > Bingo") {
    override val root = borderpane {
        center {
            vbox {
                label("Top Text") {
                    addClass(Styles.titleLabel)
                    padding = insets(15)
                }
                alignment = Pos.CENTER
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
                    useMaxSize = true
                    action {
                        moveToNextState()
                        replaceWith(SettingsView, ViewTransition.Fade(0.5.seconds))
                    }

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
        minWidth = 1000.0
    }
}

object SettingsView : View("Bingo > Settings") {
    override val root = hbox {
        tabpane {
            tab("General") {
                isClosable = false
                addClass(Styles.tab)
            }
            tab("Formatting") {
                isClosable = false
                addClass(Styles.tab)
            }
            tab("Performance") {
                isClosable = false
                addClass(Styles.tab)
            }
            tab("Appearance") {
                isClosable = false
                addClass(Styles.tab)
                val appearances = Appearance.values()
                content = hbox {
                    for (appearance in appearances) {
                        this += appearance.display()
                    }
                    spacing = 10.0
                    style {
                        padding = box(10.px)
                    }
                    addClass(Styles.defaultBackground)
                }
            }
            addClass(Styles.tabPane)
            useMaxWidth = true
            style(append = true) {
                paddingRight = 450.0
            }
        }
        button("Exit") {
            addHoverEffect(c("ff4e6c"), c("eb0028"))
            addClass(Styles.redButton)
            action {
                moveToPreviousState()
                replaceWith(BingoMenu, ViewTransition.Fade(0.5.seconds))
                BingoMenu.onDock()
            }
            style {
                paddingTop = 1
            }
        }
        addClass(Styles.defaultBackground)
    }
}