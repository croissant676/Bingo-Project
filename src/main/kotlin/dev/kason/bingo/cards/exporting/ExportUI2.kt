package dev.kason.bingo.cards.exporting

import dev.kason.bingo.ui.Styles
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Button
import tornadofx.*
import kotlin.math.sqrt

object PDFOptionView: View("Bingo > Export as PDF > Options") {
    // Possible option
    //  - Change color?
    //  - Formatting
    override val root = borderpane {
        left {

        }
        right {

        }
    }
}

object FormattingView: View("Bingo > Export > Format") {
    override val root = hbox {
        vbox {
            togglegroup {

            }
        }
        gridpane {

        }
        spacing = 30.0
        addClass(Styles.defaultBackground)
        alignment = Pos.CENTER
    }

    private const val gridpaneWidth = 273.0
    private const val gridpaneHeight = 361
    // Dimensions of the card are proportional to the result

    fun generateViewWith(sqrtToPage: Int): Node {
        val nodeWidth = gridpaneWidth / sqrtToPage
        val nodeHeight = gridpaneHeight / sqrtToPage
        return Button()
        // TODO: 9/17/2021  
    }

}