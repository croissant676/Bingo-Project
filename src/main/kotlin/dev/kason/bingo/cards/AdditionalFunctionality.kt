package dev.kason.bingo.cards

import dev.kason.bingo.ui.Styles
import dev.kason.bingo.util.addHoverEffect
import javafx.geometry.Pos
import javafx.scene.control.TextField
import tornadofx.*

object HowToPlay : View("Bingo > How to play bingo") {
    override val root = borderpane {
        center {
            vbox {
                label("Playing Bingo") {
                    addClass(Styles.titleLabel)
                }
                label(
                    "To play the game, press the \"Play Game\" button. This will" +
                            "\nallow you to play the game"
                ) {
                    addClass(Styles.regularLabel)
                }
                label(
                    "Each round, a random number will be drawn, and all cards" +
                            "\nthat have this number will mark it off."
                ) {
                    addClass(Styles.regularLabel)
                }
                label(
                    "When a card has 5 marked off tiles in a row, the card will" +
                            "\nhave won."
                ) {
                    addClass(Styles.regularLabel)
                }
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
            }
        }
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds, ViewTransition.Direction.RIGHT))
                    }
                }
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
                paddingBottom = 30.0
            }
        }
    }
}

object HowToStatistics : View("Bingo > How to use statistics") {
    override val root = borderpane {
        center {
            vbox {
                label("Using Statistics") {
                    addClass(Styles.titleLabel)
                }
                label(
                    "To use statistics, click on the statistics item at the top of the menu" +
                            "\nThis can also be done by using the shortcut Alt+S"
                ) {
                    addClass(Styles.regularLabel)
                }
                label(
                    "From there, select what you want to analyze. This can either be a single card," +
                            "\nthe game at a particular moment, or the game as a whole over time."
                ) {
                    addClass(Styles.regularLabel)
                }
                label(
                    "This will take you to a separate view that displays the statistics."
                ) {
                    addClass(Styles.regularLabel)
                }
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
            }
        }
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds, ViewTransition.Direction.RIGHT))
                    }
                }
                paddingBottom = 30.0
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
            }
        }
    }
}

object HowToExport : View("Bingo > How to export") {
    override val root = borderpane {
        center {
            vbox {
                label("Exporting") {
                    addClass(Styles.titleLabel)
                }
                label(
                    "To export a game from the viewing panel, click on the Export item" +
                            "\nat the top of the panel. (Shortcut for this is Alt+E)"
                ) {
                    addClass(Styles.regularLabel)
                }
                label(
                    "From there, select the method that you want to export your game." +
                            "\nAfter that, simply fill in the valid forms."
                ) {
                    addClass(Styles.regularLabel)
                }
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
            }
        }
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds, ViewTransition.Direction.RIGHT))
                    }
                }
                paddingBottom = 30.0
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
            }
        }
    }
}

object SearchView : View("Bingo > Help") {
    private lateinit var box: TextField
    override val root = borderpane {
        center {
            hbox {
                label("Search for:") {
                    addClass(Styles.regularLabel)
                }
                box = textfield()
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
            }
        }
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds, ViewTransition.Direction.RIGHT))
                    }
                }
                button("Search!") {
                    addHoverEffect()
                    action {
                        // Search functionality
                        val string = box.text ?: return@action
                        replaceWith(ResultView(string), ViewTransition.Fade(0.5.seconds))
                    }
                }
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
                paddingBottom = 30.0
            }
        }
    }
}

class ResultView(val string: String) : View("Bingo > Results for \"$string\"") {

    override val root = borderpane {
        val things = generateSearchResults(string)
        if (things.isEmpty()) {
            center {
                label("No search results for \"$string\"") {
                    addClass(Styles.regularLabel)
                }
            }
        } else {
            label("Results for \"$string\" (${things.size} items): ")
            tableview<SearchResult> {

            }
        }
        bottom = hbox {
            button("< Back") {
                addHoverEffect()
                action {
                    replaceWith(SearchView, ViewTransition.Fade(0.5.seconds))
                }
            }
            alignment = Pos.CENTER
            spacing = 20.0
            addClass(Styles.defaultBackground)
            paddingBottom = 30.0
        }
        addClass(Styles.defaultBackground)
    }
}

private fun generateSearchResults(string: String): List<SearchResult> {
    return listOf()
}

internal data class SearchResult(val name: String, val action: () -> Unit)