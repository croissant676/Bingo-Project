package dev.kason.bingo.cards.exporting

import dev.kason.bingo.cards.EditingCardView
import tornadofx.ViewTransition
import tornadofx.seconds
import java.awt.image.BufferedImage

internal fun generateImages(): List<BufferedImage> {
    return listOf()
}

fun exportAsPdf() {
    val view = FindFileView("game.pdf")
    typeOfFile = 0
    EditingCardView.replaceWith(view, ViewTransition.Slide(0.5.seconds))
}

fun exportAsWord() {

}



