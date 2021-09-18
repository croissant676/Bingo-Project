package dev.kason.bingo.cards.exporting

import dev.kason.bingo.ui.Styles
import dev.kason.bingo.util.addHoverEffect
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ToggleGroup
import tornadofx.*
import javax.print.attribute.IntegerSyntax

object PDFOptionView : View("Bingo > Export as PDF > Options") {
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

object FormattingView : View("Bingo > Export > Format") {

    lateinit var group: ToggleGroup

    override val root = borderpane {
        center {
            hbox {
                vbox {
                    label("Select the format of the images you want")
                    val options = listOf(
                        "1 card per image (1x1)",
                        "4 cards per image (2x2)",
                        "6 cards per image (3x2)",
                        "9 cards per image (3x3)",
                        "16 cards per image (4x4)"
                    )
                    group = togglegroup()
                    for (option in options) {
                        radiobutton(option, group) {
                            action {

                            }
                        }
                    }
                    addClass(Styles.defaultBackground)
                    spacing = 50.0
                    alignment = Pos.CENTER
                }
                gridpane {
                    // Display
                    addClass(Styles.defaultBackground)
                    style(append = true) {
                        borderColor += box(Styles.darkTextFill)
                        borderWidth += box(3.px)
                    }
                }
                addClass(Styles.defaultBackground)
                spacing = 50.0
                alignment = Pos.CENTER
                paddingBottom = 50.0
                paddingTop = 50.0
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
                }
                paddingBottom = 30.0
                addClass(Styles.defaultBackground)
                spacing = 20.0
                alignment = Pos.CENTER
            }
        }
        addClass(Styles.defaultBackground)
    }

    private const val gridpaneWidth = 273.0
    private const val gridpaneHeight = 361
    // Dimensions of the card are proportional to the result

}