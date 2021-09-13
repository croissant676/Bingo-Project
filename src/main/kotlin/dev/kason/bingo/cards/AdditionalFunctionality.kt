package dev.kason.bingo.cards

import dev.kason.bingo.ui.Styles
import dev.kason.bingo.util.addHoverEffect
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.TextField
import tornadofx.*

object UseB : View("Bingo > How to play bingo") {
    override val root = vbox {
        label("Playing Bingo") {
            addClass(Styles.titleLabel)
        }
        label(
            "To play the game, press the [Play Game] button." +
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
        button("< Back") {
            addHoverEffect()
            action {
                replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
            }
        }
        alignment = Pos.CENTER
        spacing = 20.0
        addClass(Styles.defaultBackground)
    }
}

object UseS : View("Bingo > How to use statistics") {
    override val root = vbox {
        label("Using Statistics") {
            addClass(Styles.titleLabel)
        }
        button("< Back") {
            addHoverEffect()
            action {
                replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
            }
        }
        alignment = Pos.CENTER
        spacing = 20.0
        addClass(Styles.defaultBackground)
    }
}

object UseE : View("Bingo > How to export") {
    override val root = vbox {
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
        button("< Back") {
            addHoverEffect()
            action {
                replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
            }
        }
        alignment = Pos.CENTER
        spacing = 20.0
        addClass(Styles.defaultBackground)
    }
}

object SearchView : View("Bingo > Help") {
    private lateinit var box: TextField
    override val root = vbox {
        hbox {
            label("Enter what you want to search here!") {
                addClass(Styles.regularLabel)
            }
            box = textfield()
        }
        hbox {
            button("< Back") {
                addHoverEffect()
                action {
                    replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                }
            }
            button("Search!") {
                addHoverEffect()
                action {
                    // Search functionality
                    val string = box.text ?: return@action
                    replaceWith(ResultView(string), ViewTransition.Slide(0.3.seconds))
                }
            }
        }
        alignment = Pos.CENTER
        spacing = 20.0
        addClass(Styles.defaultBackground)
    }
}

class ResultView(val string: String) : View("Bingo > Results for \"$string\"") {

    override val root = vbox {
        val things = generateSearchResults(string)
        if (things.isEmpty()) {
            label("No search results for \"$string\"") {
                addClass(Styles.regularLabel)
            }
            button ("< Back") {
                addHoverEffect()
                action {
                    replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                }
            }
        } else {
            label("Results for \"$string\" (${things.size} items): ")
            tableview<SearchResult> {

            }
        }
        alignment = Pos.CENTER
        spacing = 20.0
        addClass(Styles.defaultBackground)
    }
}

private fun generateSearchResults(string: String): List<SearchResult> {
    return listOf()
}

internal data class SearchResult(val name: String, val action: () -> Unit)