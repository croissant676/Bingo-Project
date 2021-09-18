@file:Suppress("UNUSED_VARIABLE")

package dev.kason.bingo

import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import tornadofx.*
import java.util.regex.Pattern
import kotlin.concurrent.thread
import kotlin.math.roundToInt

lateinit var curView: View

var currentGame: BingoGame = generateNumbers()
var currentlyDisplayedCard: CardView = generateCardView(currentGame.first())

val f = Font("dubai", 100.0)
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

val isNumPattern = Pattern.compile("-?\\d+")!!

object EditingCardView : View("Bingo > Cards") {

    private lateinit var pane: Pane
    private lateinit var previousView: CardView
    private lateinit var drawnBalls: FlowPane
    private lateinit var wonCards: FlowPane
    private lateinit var spinner: Spinner<Int>

    private lateinit var drawnBallsLabel: Label
    private lateinit var wonCardsLabel: Label

    private var value: Int = 1

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
                                exportTXTText()
                            }
                        }
                        item("to Text (other)") {
                            accelerator = KeyCodeCombination(KeyCode.O, KeyCombination.ALT_DOWN)
                            action {
                                exportOtherText()
                            }
                        }
                        item("to Clipboard (single image)") {
                            accelerator = KeyCodeCombination(KeyCode.I, KeyCombination.ALT_DOWN)
                            action {
                                exportImageCB()
                            }
                        }
                        item("to Clipboard (as Text)") {
                            accelerator = KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN)
                            action {
                                curView = EditingCardView
                                exportCBText()
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
//                    item("Search for help") {
//                        accelerator = KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)
//                        action {
//                            replaceWith(SearchView, ViewTransition.Slide(0.5.seconds))
//                        }
//                    }
                }
            }
        }
        left {
            pane = pane {
                currentlyDisplayedCard = generateCardView(currentGame.first())
                add(currentlyDisplayedCard)
                addClass(Styles.defaultBackground)
                setOnScroll {
                    val number = (currentlyDisplayedCard.card.cardNumber + it.deltaY / 20).roundToInt()
                    if (number in 1..currentGame.size) {
                        val newCard = generateCardView(currentGame[number - 1])
                        value = number
                        spinner.editor.text = number.toString()
                        currentlyDisplayedCard.replaceWith(newCard)
                        currentlyDisplayedCard = newCard
                    } else if (number <= 1) {
                        val newCard = generateCardView(currentGame[0])
                        value = 1
                        spinner.editor.text = "1"
                        currentlyDisplayedCard.replaceWith(newCard)
                        currentlyDisplayedCard = newCard
                    } else {
                        val newCard = generateCardView(currentGame.last())
                        value = currentGame.size
                        spinner.editor.text = currentGame.size.toString()
                        currentlyDisplayedCard.replaceWith(newCard)
                        currentlyDisplayedCard = newCard
                    }
                }
                setOnMouseClicked {
                    println("W = $width, H = $height")
                }
            }
        }
        center {
            borderpane {
                bottom {
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
                        spinner = spinner(1, currentGame.size) {
                            isEditable = true
                            valueProperty().addListener { _, _, newValue ->
                                EditingCardView.value = newValue
                                refresh()
                            }
                            editor.textProperty().addListener { _, oldValue, newValue ->
                                if (newValue.length > 7) {
                                    editor.text = oldValue
                                } else if (!newValue.matches("\\d*".toRegex())) {
                                    editor.text = newValue.replace("[^\\d]".toRegex(), "")
                                }
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
                                validateNumber()
                                runNumber()
                            }
                        }
                        vbox {
                            drawnBallsLabel = label("Drawn balls:") {
                                addClass(Styles.regularLabel)
                                alignment = Pos.BOTTOM_LEFT
                            }
                            scrollpane(fitToWidth = true) {
                                drawnBalls = flowpane {
                                    vgap = 5.0
                                    hgap = 5.0
                                    alignment = Pos.TOP_LEFT
                                }
                                maxHeight = 190.0
                                prefHeight = 190.0
                            }
                            wonCardsLabel = label("Cards that have won:") {
                                addClass(Styles.regularLabel)
                            }
                            scrollpane(fitToWidth = true) {
                                wonCards = flowpane {
                                    vgap = 5.0
                                    hgap = 5.0
                                    alignment = Pos.TOP_LEFT
                                }
                                maxHeight = 190.0
                                prefHeight = 190.0
                            }
                            spacing = 5.0
                            alignment = Pos.CENTER
                            addClass(Styles.defaultBackground)
                        }
                        button("Play Button") {
                            addHoverEffect()
                        }
                        addClass(Styles.defaultBackground)
                        alignment = Pos.CENTER
                        paddingHorizontal = 10.0
                        spacing = 10.0
                    }
                }
            }
        }
        addClass(Styles.defaultBackground)
    }

    private fun validateNumber() {
        if (!isNumPattern.matcher(spinner.editor.text).matches()) {
            spinner.editor.text = "1"
        } else {
            val number = spinner.editor.text.toIntOrNull()
            if (number == null) {
                spinner.editor.text = "1"
            } else if (number !in 1..currentGame.size) {
                spinner.editor.text = if (number < 1) "1" else currentGame.size.toString()
            }
        }
        value = spinner.editor.text.toInt()
    }

    private fun runNumber() {
        val number = currentGame.generateRandomNumber()
        if (number != -1) {
            currentRound++
            wonCardsLabel.text = "# of cards won: ${currentGame.numberOfCardsWon}"
            drawnBallsLabel.text = "# of balls drawn: ${currentRound - 1}"
            refresh()
            drawnBalls.add(generateLabel(number))
            thread(name = "UpdateThread", priority = 7) {
                val cards = currentGame.check(number)
                if (cards.isEmpty()) return@thread
                for (card in cards) {
                    runLater {
                        wonCards.add(generateCardLabel(card.cardNumber))
                    }
                }
            }
        } else {
            drawnBallsLabel.style(append = true) {
                textFill = c("ff4e6c")
            }
            drawnBallsLabel.text = "All balls drawn!"
        }
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

    private val size = (currentGame.size.toString().length * 20).coerceAtLeast(60)

    private fun generateCardLabel(number: Int): Button {
        val button = Button(number.toString())
        with(button) {
            prefWidth = size.toDouble()
            prefHeight = size.toDouble()
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
            action {
                val newCard = generateCardView(currentGame[number - 1])
                value = number
                spinner.editor.text = number.toString()
                currentlyDisplayedCard.replaceWith(newCard)
                currentlyDisplayedCard = newCard
            }
        }
        return button
    }

    private fun refresh() {
        validateNumber()
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
    bulkImgTypes = when (type) {
        0 -> "jpg"
        1 -> "png"
        2 -> "gif"
        else -> throw IllegalStateException("no")
    }
    isFolder = false
}

fun outputCardsFolder(type: Int) {
    bulkImgTypes = when (type) {
        0 -> "jpg"
        1 -> "png"
        2 -> "gif"
        else -> throw IllegalStateException("no")
    }
    isFolder = true

}
