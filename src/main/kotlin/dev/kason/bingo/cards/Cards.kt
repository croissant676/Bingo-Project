package dev.kason.bingo.cards

import dev.kason.bingo.control.currentAppearance
import dev.kason.bingo.ui.Styles
import dev.kason.bingo.ui.runImmediately
import dev.kason.bingo.util.isRunning
import dev.kason.bingo.util.runInsideLoop
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import tornadofx.*

var currentGame = BingoGame(1, cards = arrayListOf())
lateinit var currentlyDisplayedC: CardView

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
                    val button = Label(if (value == -1) "FREE" else value.toString()).apply {
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
                    add(button, row, col)
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

//    fun refreshView() {
//        root = vbox {
//
//        }
//    }
}

object EditingCardView : View("Bingo > Cards") {
    override val root: Parent = borderpane {
        left {
            add(generateCardView(currentGame.first()))
            addClass(Styles.defaultBackground)
        }
        center {
            currentGame.check(12)
        }
        addClass(Styles.defaultBackground)
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
    val posView = currentGame.viewMap[card.cardNumber]
    if (posView == null) {
        val view = CardView(card)
        currentGame.viewMap[card.cardNumber] = view
        return view
    }
    return posView
}