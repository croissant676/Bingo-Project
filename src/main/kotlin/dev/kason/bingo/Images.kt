package dev.kason.bingo.cards.exporting

import dev.kason.bingo.BingoCard
import dev.kason.bingo.BingoGame
import dev.kason.bingo.currentGame
import dev.kason.bingo.f
import dev.kason.bingo.control.currentAppearance
import dev.kason.bingo.ui.Styles
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.image.WritableImage
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import javafx.stage.Stage
import tornadofx.*
import java.awt.image.BufferedImage


var array: Array<BufferedImage> = arrayOf()

// To reduce memory consumption, generate at last possible moment.
fun generateImageForCard(card: BingoCard): BufferedImage {
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

/*
* format | scale     | result
* 1      = x1, x1    = 546  x 722  px
* 4      = x2, x2    = 1092 x 1444 px
* 6      = x3, x2    = 1638 x 1444 px
* 9      = x3, x3    = 1638 x 2166 px
* 16     = x4, x4    = 2184 x 2888 px
* */
val dimensions = mapOf(
    1 to (1 to 1),
    4 to (2 to 2),
    9 to (3 to 3),
    16 to (4 to 4)
)

fun generateViewRes(bingoGame: BingoGame = currentGame, format: Int, startingIndex: Int): BufferedImage {
    if (format == 1) {
        return generateImageForCard(bingoGame[startingIndex])
    }
    println("Format: $format, startingIndex: $startingIndex")
    val gridPane = GridPane()
    val dimension = dimensions[format]!!
    if (format + startingIndex > bingoGame.size) {
        val shouldCompl = bingoGame.lastIndex - startingIndex
        if (shouldCompl == 0) {
            return BufferedImage(dimension.first * 546, dimension.second * 722, BufferedImage.TYPE_INT_RGB)
        }
        val rowCount = shouldCompl / dimension.first
        val lastColCount = shouldCompl - (rowCount * dimension.first)
        gridPane.apply {
            for (row in 0 until rowCount) {
                for (col in 0 until dimension.second) {
                    val card = bingoGame[row * dimension.second + col + startingIndex]
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
                            for (row1 in 0 until 5) {
                                for (col1 in 0 until 5) {
                                    val value = values[row1][col1]
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
                                    add(label, row1, col1)
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
                    gridPane.add(node, row, col)
                }
            }
            for (col in 0 until lastColCount) {
                val card = bingoGame[rowCount * dimension.second + col + startingIndex]
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
                        for (row1 in 0 until 5) {
                            for (col1 in 0 until 5) {
                                val value = values[row1][col1]
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
                                add(label, row1, col1)
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
                add(node, rowCount, col)
            }
            for (number in lastColCount until dimension.second) {
                val node = Label().apply {
                    prefWidth = 546.0
                    prefHeight = 722.0
                    style {
                        backgroundColor += Color.BLACK
                    }
                }
                add(node, rowCount, number)
            }
            addClass(Styles.defaultBackground)
        }
        val scene = Scene(gridPane)
        val writableImage = scene.snapshot(WritableImage(546, 722))
        return SwingFXUtils.fromFXImage(writableImage, BufferedImage(546, 722, BufferedImage.TYPE_INT_RGB))
    }
    // copied and pasted code so it doesn't really matter
    gridPane.apply {
        for (row in 0 until dimension.first) {
            for (col in 0 until dimension.second) {
                val card = bingoGame[row * dimension.second + col + startingIndex]
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
                        for (row1 in 0 until 5) {
                            for (col1 in 0 until 5) {
                                val value = values[row1][col1]
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
                                add(label, row1, col1)
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
                gridPane.add(node, row, col)
            }
        }
        addClass(Styles.defaultBackground)
    }
    val scene = Scene(gridPane)
    val writableImage = scene.snapshot(WritableImage(dimension.first * 546, dimension.second * 722))
    return SwingFXUtils.fromFXImage(writableImage, BufferedImage(dimension.first * 546, dimension.second * 722, BufferedImage.TYPE_INT_RGB))
}

val dimensionList = dimensions.values.toList()

// Defunct
fun generateViewResult(format: Int): Node = GridPane().apply {
    val dimension = dimensionList[format]
    for (row in 0 until dimension.first) {
        for (col in 0 until dimension.second) {
            val values = Array(5) { row ->
                Array(5) {
                    row * 15 + it + 1
                }
            }
            val node = VBox().apply {
                hbox {
                    alignment = Pos.TOP_RIGHT
                    label("Seed 1, Card 1") {
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
                    for (row1 in 0 until 5) {
                        for (col1 in 0 until 5) {
                            val value = values[row1][col1]
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
                            add(label, row1, col1)
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
            add(node, row, col)
        }
    }
}
