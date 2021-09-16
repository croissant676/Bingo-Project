package dev.kason.bingo.cards

import dev.kason.bingo.cards.exporting.ExportTextView
import dev.kason.bingo.cards.exporting.pdfUI
import dev.kason.bingo.cards.exporting.wordUI
import dev.kason.bingo.control.currentAppearance
import dev.kason.bingo.ui.Styles
import dev.kason.bingo.util.addHoverEffect
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import tornadofx.*


var currentGame: BingoGame = generateNumbers()
var currentlyDisplayedCard: CardView = generateCardView(currentGame.first())

var f = Font("dubai", 100.0)
val columnColors = mutableListOf<Paint>()

class CardView(val card: BingoCard) : View("Bingo > Card: ") {
    override var root = vbox {
//        button ("Generate Image of this and store"){
////            val image = WritableImage(700, 700)
////            scene.snapshot(image)
////            val file = File("C:\\Users\\crois\\IdeaProjects\\BingoProject\\src\\main\\resources\\Test.png")
////            ImageIO.write(image, file.extension, file)
//            addHoverEffect()
//        }
        hbox {
            alignment = Pos.TOP_RIGHT
            label("Seed ${card.randomSeed}, Card ${card.cardNumber}") {
                style {
                    fontSize = 20.px
                }
            }
        }
        // Create the word BINGO
        val x = 0.px
        hbox {
            label("B") {
                font = f
                // No left padding
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
                    val card = card[row][col]
                    val value = card.value
                    val label = Label(if (value == -1) "FREE" else value.toString()).apply {
                        prefWidth = 80.0
                        prefHeight = 80.0
                        style {
                            backgroundColor += Styles.themeColor
                            textFill = Styles.lightTextColor
                            fontFamily = "dubai"
                            fontSize = 30.px
                            padding = box(2.px)
                        }
                        contentDisplay = ContentDisplay.CENTER
                        alignment = Pos.CENTER
                        textAlignment = TextAlignment.CENTER
                    }
                    if (card.crossedOff) {
                        label.style(append = true) {
                            backgroundColor += c("ff4e6c")
                        }
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
            borderWidth += box(3.px)
            borderColor += box(c(currentAppearance.themeColor))
            padding = box(60.px)
        }
        addClass(Styles.defaultBackground)
        alignment = Pos.CENTER
    }
}

object EditingCardView : View("Bingo > Cards") {
    private lateinit var pane: Pane
    private lateinit var previousView: CardView
    override val root: Parent = borderpane {
        top {
            menubar {
                menu("Exporting") {
                    menu("Export as Wrapper") {
                        item("to PDF (.pdf)") {
                            accelerator = KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN)
                            action {
                                pdfUI()
                            }
                        }
                        item("to Word (.docx)") {
                            accelerator = KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN)
                            action {
                                wordUI()
                            }
                        }
                        item("to Powerpoint (.pptx)") {
                            accelerator = KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN)
                        }
                    }
                    menu("Export as Photos") {
                        menu("to Folder") {
                            item("as JPG (.jpg)") {
                                accelerator = KeyCodeCombination(KeyCode.J, KeyCombination.ALT_DOWN)
                                action {
                                    outputCardsFolder(0)
                                }
                            }
                            item("as PNG (.png)") {
                                accelerator = KeyCodeCombination(KeyCode.U, KeyCombination.ALT_DOWN)
                                action {
                                    outputCardsFolder(1)
                                }
                            }
                            item("as GIP (.gif)") {
                                accelerator = KeyCodeCombination(KeyCode.G, KeyCombination.ALT_DOWN)
                                action {
                                    outputCardsFolder(2)
                                }
                            }
                        }
                        menu("to ZIP container (.zip)") {
                            item("as JPG (.jpg)") {
                                accelerator = KeyCodeCombination(KeyCode.K, KeyCombination.ALT_DOWN)
                                action {
                                    outputCardsFolder(0)
                                }
                            }
                            item("as PNG (.png)") {
                                accelerator = KeyCodeCombination(KeyCode.P, KeyCombination.ALT_DOWN)
                                action {
                                    outputCardsFolder(1)
                                }
                            }
                            item("as GIP (.gif)") {
                                accelerator = KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN)
                                action {
                                    outputCardsFolder(2)
                                }
                            }
                        }
                    }
                    menu("Export as Other") {
                        item("to Text (.txt)") {
                            accelerator = KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN)
                            action {
                                println()
                            }
                        }
                        item("to Text (other)") {
                            accelerator = KeyCodeCombination(KeyCode.O, KeyCombination.ALT_DOWN)
                            action {
                                println()
                            }
                        }
                        item("to Clipboard (single image)") {
                            accelerator = KeyCodeCombination(KeyCode.I, KeyCombination.ALT_DOWN)
                            action {
                                println()
                            }
                        }
                        item("to Clipboard (as Text)") {
                            accelerator = KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN)
                            action {
                                this@EditingCardView.replaceWith(ExportTextView)
                            }
                        }
                    }
                }
                menu("Analytics") {
                    item("View Single Card") {
                        accelerator = KeyCodeCombination(KeyCode.V, KeyCombination.SHIFT_DOWN)
                    }
                    item("View Game at instant") {
                        accelerator = KeyCodeCombination(KeyCode.I, KeyCombination.SHIFT_DOWN)
                    }
                    item("View Game over time") {
                        accelerator = KeyCodeCombination(KeyCode.T, KeyCombination.SHIFT_DOWN)
                    }
                }
                menu("Help") {
                    item("How to play bingo") {
                        accelerator = KeyCodeCombination(KeyCode.H, KeyCombination.SHIFT_DOWN)
                        action {
                            replaceWith(HowToPlay, ViewTransition.Slide(0.5.seconds))
                        }
                    }
                    item("How to export game") {
                        accelerator = KeyCodeCombination(KeyCode.E, KeyCombination.SHIFT_DOWN)
                        action {
                            replaceWith(HowToExport, ViewTransition.Slide(0.5.seconds))
                        }
                    }
                    item("How to use statistics") {
                        accelerator = KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN)
                        action {
                            replaceWith(HowToStatistics, ViewTransition.Slide(0.5.seconds))
                        }
                    }
                    item("Search for help") {
                        accelerator = KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)
                        action {
                            replaceWith(SearchView, ViewTransition.Slide(0.5.seconds))
                        }
                    }
                }
            }
        }
        left {
            pane = pane {
                currentlyDisplayedCard = generateCardView(currentGame.first())
                add(currentlyDisplayedCard)
                addClass(Styles.defaultBackground)
                setOnMouseClicked {
                    println(value)
                    quickPrintCard()
                    if (currentlyDisplayedCard.card.cardNumber != value) { // Prevent duplicate children error
                        val card = generateCardView(currentGame[value - 1])
                        currentlyDisplayedCard.replaceWith(card, ViewTransition.Fade(0.3.seconds))
                        currentlyDisplayedCard = card
                    }
                }
            }
        }
        center {
            borderpane {
                bottom {
                    label {

                    }
//                    button("Print View") {
//                        action {
//                            println(drawnBalls.height)
//                        }
//                        addHoverEffect()
//                    }
                }
                top {
                    hbox {
                        paddingTop = 30.0
                        paddingLeft = 10.0
                        label("Card: ") {
                            addClass(Styles.regularLabel)
                            style(append = true) {
                                fontSize = 25.px
                            }
                        }
                        spinner(1, currentGame.size) {
                            valueProperty().addListener { _, _, newValue ->
                                this@EditingCardView.value = newValue
                                refresh()
                            }
                        }
                        alignment = Pos.CENTER
                        spacing = 10.0
                        addClass(Styles.defaultBackground)
                    }
                }
                center {
                    vbox {
                        button("Draw number") {
                            addHoverEffect()
                            action {
                                runNumber()
                            }
                        }
                        vbox {
                            scrollpane(fitToWidth = true) {
                                drawnBalls = flowpane {
                                    vgap = 5.0
                                    hgap = 5.0
                                    alignment = Pos.TOP_LEFT
                                    addClass(Styles.defaultBackground)
                                }
                                maxHeight = 255.0
                                prefHeight = 255.0
                                addClass(Styles.defaultBackground)
                            }
                            scrollpane {
                                wonCards = flowpane {
                                    vgap = 5.0
                                    hgap = 5.0
                                    alignment = Pos.TOP_LEFT
                                    addClass(Styles.defaultBackground)
                                }
                                maxHeight = 255.0
                                prefHeight = 255.0
                                addClass(Styles.defaultBackground)
                            }
                            spacing = 10.0
                            alignment = Pos.CENTER
                            addClass(Styles.defaultBackground)
                        }
                        addClass(Styles.defaultBackground)
                        alignment = Pos.CENTER
                        paddingHorizontal = 10.0
                        spacing = 20.0
                    }
                }
            }
        }
        addClass(Styles.defaultBackground)
    }

    private lateinit var drawnBalls: FlowPane
    private lateinit var wonCards: FlowPane

    private fun runNumber() {
        val number = currentGame.generateRandomNumber()
        if (number != -1) {
            currentRound++
            currentGame.check(number)
            refresh()
            drawnBalls.add(generateLabel(number))
        } else {
            // To stuff here
        }
        // Do some more things
    }

    private fun generateLabel(number: Int): Label {
        val label = Label(number.toString())
        with(label) {
            prefWidth = 60.0
            prefHeight = 60.0
            style {
                backgroundColor += c("ff4e6c")
                textFill = Styles.lightTextColor
                fontFamily = "dubai"
                fontSize = 30.px
                padding = box(2.px)
            }
            contentDisplay = ContentDisplay.CENTER
            alignment = Pos.CENTER
            textAlignment = TextAlignment.CENTER
        }
        return label
    }

    private var value: Int = 1

    private fun refresh() {
        // Prevent duplicate children error
        val card = generateCardView(currentGame[value - 1])
        currentlyDisplayedCard.replaceWith(card)
        currentlyDisplayedCard = card

    }

    private fun updateToMatch() {
        pane.clear()
        pane.add(currentlyDisplayedCard)
    }
//    init {
//        if(isRunning) {
//            println("Event Loop is active right now!")
//            runInsideLoop (5) {
//
//            }
//        }
//    }
}


fun generateCardView(card: BingoCard): CardView {
    return CardView(card)
//    val posView = currentGame.viewMap[card.cardNumber]
//    if (posView == null) {
//        val view = CardView(card)
//        currentGame.viewMap[card.cardNumber] = view
//        return view
//    }
//    return posView
}

fun outputCardsZip(type: Int) {
    println("Zip as type: $type")
    var string = when (type) {
        0 -> "jpg"
        1 -> "png"
        2 -> "gif"
        else -> throw IllegalStateException("no")
    }
}

fun outputCardsFolder(type: Int) {
    println("Folder as type: $type")
    var string = when (type) {
        0 -> "jpg"
        1 -> "png"
        2 -> "gif"
        else -> throw IllegalStateException("no")
    }
}
