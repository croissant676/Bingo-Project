package dev.kason.bingo.cards

import dev.kason.bingo.ui.Styles
import dev.kason.bingo.util.addHoverEffect
import tornadofx.*

object UseB : View("Bingo > How to play bingo") {
    override val root = vbox {
        label("Playing Bingo") {
            addClass(Styles.titleLabel)
        }
        button("< Back") {
            addHoverEffect()
            action {
                replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
            }
        }
    }
}

object UseS : View("Bingo > How to use statistics") {
    override val root = vbox {
        label("Playing Bingo") {
            addClass(Styles.titleLabel)
        }
        button("< Back") {
            addHoverEffect()
            action {
                replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
            }
        }
    }
}

object UseE : View("Bingo > How to export") {
    override val root = vbox {
        label("Playing Bingo") {
            addClass(Styles.titleLabel)
        }
        // Write text here
        button("< Back") {
            addHoverEffect()
            action {
                replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
            }
        }
    }
}