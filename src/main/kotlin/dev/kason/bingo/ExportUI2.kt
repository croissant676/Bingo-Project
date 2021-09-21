package dev.kason.bingo

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

    var whenFinished: FormattingView.() -> Unit = {}

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
                imageview(SwingFXUtils.toFXImage(generateImage(format = number, startingIndex = 0), null)).apply {
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
                        curView = EditingCardView
                        replaceWith(curView, ViewTransition.Fade(0.5.seconds))
                    }
                }
                button("Next >") {
                    addHoverEffect()
                    action {
                        this@FormattingView.whenFinished()
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
                imageview(SwingFXUtils.toFXImage(generateImage(format = number, startingIndex = 0), null)).apply {
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

object ExportAsClipBoardView : View("Bingo > Export As Clipboard") {

    var value: Int = 1
        private set
    var action: ExportAsClipBoardView.() -> Unit = {}

    override val root = borderpane {
        center {
            vbox {
                label("Select the card to export") {
                    addClass(Styles.titleLabel)
                }
                label(
                    "Because clipboard can only handle 1 image, you have to select the card that\n" +
                            "you want saved into your clipboard. If you want multiple cards in 1 (see \"Format\"\n" +
                            "for some context), this will act as your first card."
                ) {
                    addClass(Styles.regularLabel)
                }
                label ("For example, if you want to save cards 1 - 4 into your image, you would select\n" +
                        "1 as your card, and select 4 as the format."){
                    addClass(Styles.regularLabel)
                }
                spinner(1, currentGame.size) {
                    isEditable = true
                    valueProperty().addListener { _, _, newValue ->
                        this@ExportAsClipBoardView.value = newValue
                    }
                    editor.textProperty().addListener { _, oldValue, newValue ->
                        if (newValue.length > 7) {
                            editor.text = oldValue
                        } else if (!newValue.matches("\\d*".toRegex())) {
                            editor.text = newValue.replace("[^\\d]".toRegex(), "")
                        }
                    }
                }
                spacing = 30.0
                alignment = Pos.CENTER
            }
        }
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                    }
                }
                button("Next > ") {
                    addHoverEffect()
                    action {
                        action()
                    }
                }
                paddingBottom = 30.0
                spacing = 20.0
                alignment = Pos.CENTER
            }
        }
        addClass(Styles.defaultBackground)
    }
}