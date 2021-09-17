package dev.kason.bingo.cards.exporting

import dev.kason.bingo.cards.BingoCard
import dev.kason.bingo.cards.BingoGame
import dev.kason.bingo.cards.currentGame
import dev.kason.bingo.cards.f
import dev.kason.bingo.control.currentAppearance
import dev.kason.bingo.ui.Styles
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.image.WritableImage
import javafx.scene.layout.VBox
import javafx.scene.text.TextAlignment
import tornadofx.*
import java.awt.image.BufferedImage


var array: Array<BufferedImage> = arrayOf()

// To reduce memory consumption, generate at last possible moment.
fun generateImageForCard(card: BingoCard): BufferedImage {
    println("yessr")
    val values = card.values()
    val node = VBox().apply {
        hbox {
            alignment = Pos.TOP_RIGHT
            label("Seed ${card.randomSeed}, Card ${card.cardNumber}") {
                style {
                    fontSize = 20.px
                }
            }
        }
        val x = 0.px
        hbox {
            label("B") {
                font = f
                style {
                    padding = box(x, x, x, 10.px)
                }
            }
            label("I") {
                font = f
                style {
                    padding = box(x, x, x, 40.px)
                }
            }
            label("N") {
                font = f
                style {
                    padding = box(x, x, x, 40.px)
                }
            }
            label("G") {
                font = f
                style {
                    padding = box(x, x, x, 15.px)
                }
            }
            label("O") {
                font = f
                style {
                    padding = box(x, x, x, 10.px)
                }
            }
        }
        gridpane {
            for (row in 0 until 5) {
                for (col in 0 until 5) {
                    val value = values[row][col]
                    val label = Label().apply {
                        if (value == -1) {
                            text = "FREE"
                            style {
                                backgroundColor += c("ff4e6c")
                                textFill = Styles.lightTextColor
                                fontFamily = "dubai"
                                fontSize = 30.px
                                padding = box(2.px)
                            }
                        } else {
                            text = value.toString()
                            style {
                                backgroundColor += Styles.themeColor
                                textFill = Styles.lightTextColor
                                fontFamily = "dubai"
                                fontSize = 30.px
                                padding = box(2.px)
                            }
                        }
                        prefWidth = 80.0
                        prefHeight = 80.0
                        contentDisplay = ContentDisplay.CENTER
                        alignment = Pos.CENTER
                        textAlignment = TextAlignment.CENTER
                    }
                    add(label, row, col)
                }
            }
            vgap = 5.0
            hgap = 5.0
            addClass(Styles.defaultBackground)
        }
        style {
            borderRadius += box(0.px)
            insets(23)
            borderWidth += box(3.px)
            borderColor += box(c(currentAppearance.themeColor))
            padding = box(60.px)
        }
        addClass(Styles.defaultBackground)
        alignment = Pos.CENTER
    }
    val scene = Scene(node)
    val writableImage = scene.snapshot(WritableImage(546, 722))
    return SwingFXUtils.fromFXImage(writableImage, BufferedImage(546, 722, BufferedImage.TYPE_INT_RGB))
}

fun generateImagesForGame(bingoGame: BingoGame = currentGame): List<BufferedImage> {
    val arrayList = arrayListOf<BufferedImage>()
    for (card in bingoGame) {
        arrayList += generateImageForCard(card)
    }
    return arrayList
}

fun generateViewRes(bingoGame: BingoGame, format: Int): BufferedImage {
    for (card in bingoGame) {
        val values = card.values()
        val node = VBox().apply {
            hbox {
                alignment = Pos.TOP_RIGHT
                label("Seed ${card.randomSeed}, Card ${card.cardNumber}") {
                    style {
                        fontSize = 20.px
                    }
                }
            }
            val x = 0.px
            hbox {
                label("B") {
                    font = f
                    style {
                        padding = box(x, x, x, 10.px)
                    }
                }
                label("I") {
                    font = f
                    style {
                        padding = box(x, x, x, 40.px)
                    }
                }
                label("N") {
                    font = f
                    style {
                        padding = box(x, x, x, 40.px)
                    }
                }
                label("G") {
                    font = f
                    style {
                        padding = box(x, x, x, 15.px)
                    }
                }
                label("O") {
                    font = f
                    style {
                        padding = box(x, x, x, 10.px)
                    }
                }
            }
            gridpane {
                for (row in 0 until 5) {
                    for (col in 0 until 5) {
                        val value = values[row][col]
                        val label = Label().apply {
                            if (value == -1) {
                                text = "FREE"
                                style {
                                    backgroundColor += c("ff4e6c")
                                    textFill = Styles.lightTextColor
                                    fontFamily = "dubai"
                                    fontSize = 30.px
                                    padding = box(2.px)
                                }
                            } else {
                                text = value.toString()
                                style {
                                    backgroundColor += Styles.themeColor
                                    textFill = Styles.lightTextColor
                                    fontFamily = "dubai"
                                    fontSize = 30.px
                                    padding = box(2.px)
                                }
                            }
                            prefWidth = 80.0
                            prefHeight = 80.0
                            contentDisplay = ContentDisplay.CENTER
                            alignment = Pos.CENTER
                            textAlignment = TextAlignment.CENTER
                        }
                        add(label, row, col)
                    }
                }
                vgap = 5.0
                hgap = 5.0
                addClass(Styles.defaultBackground)
            }
            style {
                borderRadius += box(0.px)
                insets(23)
                borderWidth += box(3.px)
                borderColor += box(c(currentAppearance.themeColor))
                padding = box(60.px)
            }
            addClass(Styles.defaultBackground)
            alignment = Pos.CENTER
        }
        val scene = Scene(node)
        val width = 546 / format
        val height = 722 / format
        val writableImage = scene.snapshot(WritableImage(width, height))
        return SwingFXUtils.fromFXImage(writableImage, BufferedImage(width, height, BufferedImage.TYPE_INT_RGB))
    }
    return BufferedImage(format, format, BufferedImage.TYPE_INT_RGB)
}