package dev.kason.bingo.cards

import tornadofx.View
import tornadofx.gridpane
import tornadofx.label
import tornadofx.vbox

class CardView(val card: BingoCard) : View("Bingo > Card: ") {
    override val root = vbox {
        label("Bingo") {

        }
        gridpane {

        }
    }
}