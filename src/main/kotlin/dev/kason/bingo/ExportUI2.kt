package dev.kason.bingo.cards.exporting

import dev.kason.bingo.currentGame
import dev.kason.bingo.ui.Styles
import dev.kason.bingo.addHoverEffect
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.Pane
import tornadofx.*

object FormattingView : View("Bingo > Export > Format") {

    private lateinit var group: ToggleGroup
    private lateinit var node: Pane

    @Suppress("MemberVisibilityCanBePrivate")
    var number = 4
        private set

    var action: FormattingView.() -> Unit = {}

    private const val optWidth = 436.8
    private const val optHeight = 577.6

    override val root = borderpane {
        center {
            vbox {
                label("Select a format:") {
                    addClass(Styles.titleLabel)
                }
                hbox {
                    vbox {
                        val options = listOf(
                            "1 card per image (1x1)",
                            "4 cards per image (2x2)",
                            "9 cards per image (3x3)",
                            "16 cards per image (4x4)"
                        )
                        group = togglegroup {
                            alignment = Pos.CENTER_LEFT
                        }
                        val size = currentGame.size
                        for (optionIndex in options.indices) {
                            radiobutton(options[optionIndex], group) {
                                if (optionIndex == 1) {
                                    isSelected = true
                                }
                                val format = when (optionIndex) {
                                    0 -> 1
                                    1 -> 4
                                    2 -> 9
                                    3 -> 16
                                    else -> throw IllegalStateException("no")
                                }
                                if (format > size) {
                                    disableProperty().set(true)
                                }
                                action {
                                    number = format
                                    refresh()
                                }
                                isFocusTraversable = false
                            }
                        }
                        paddingLeft = 10.0
                        addClass(Styles.defaultBackground)
                        spacing = 50.0
                        alignment = Pos.CENTER
                    }
                    addClass(Styles.defaultBackground)
                    spacing = 50.0
                    alignment = Pos.CENTER
                }
                paddingTop = 30.0
                addClass(Styles.defaultBackground)
                spacing = 50.0
                alignment = Pos.CENTER
            }
        }
        right {
            node = vbox {
                imageview(SwingFXUtils.toFXImage(generateViewRes(format = number, startingIndex = 0), null)).apply {
                    isPreserveRatio = true
                    fitWidth = optWidth
                    fitHeight = optHeight
                    paddingRight = 30.0
                }
                paddingTop = 50.0
                addClass(Styles.defaultBackground)
                spacing = 50.0
                alignment = Pos.CENTER
            }
        }
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        replaceWith(currentFFView, ViewTransition.Slide(0.5.seconds, ViewTransition.Direction.RIGHT))
                    }
                }
                button("Next >") {
                    addHoverEffect()
                    action {
                        this@FormattingView.action()
                    }
                }
                paddingBottom = 30.0
                paddingTop = 30.0
                addClass(Styles.defaultBackground)
                spacing = 20.0
                alignment = Pos.CENTER
            }
        }
        addClass(Styles.defaultBackground)
    }

    private fun refresh() {
        val newNode = Pane().apply {
            vbox {
                imageview(SwingFXUtils.toFXImage(generateViewRes(format = number, startingIndex = 0), null)).apply {
                    isPreserveRatio = true
                    fitWidth = optWidth
                    fitHeight = optHeight
                }
                paddingTop = 50.0
                paddingRight = 30.0
                addClass(Styles.defaultBackground)
                spacing = 50.0
                alignment = Pos.CENTER
            }
        }
        node.replaceWith(newNode, ViewTransition.Fade(0.15.seconds))
        node = newNode
    }
}
