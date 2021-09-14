package dev.kason.bingo.cards.exporting

import dev.kason.bingo.cards.EditingCardView
import tornadofx.ViewTransition
import tornadofx.seconds
import java.awt.image.BufferedImage
import java.io.File

internal fun generateImages(): List<BufferedImage> {
    return listOf()
}

fun pdfUI() {
    val view = FindFileView("game.pdf")
    typeOfFile = 0
    EditingCardView.replaceWith(view, ViewTransition.Slide(0.5.seconds))
    view.whenFinished = {
        println(view.result)
        val file = File(view.result)
        if(!file.exists() || !file.canRead()) {

        }
    }
}

private fun exportPdf() {

}

fun wordUI() {
    val view = FindFileView("game.docx")
    typeOfFile = 0
    EditingCardView.replaceWith(view, ViewTransition.Slide(0.5.seconds))
    view.whenFinished = {
        val file = File(result)
        if(file.exists()) {
            label.text = "$result already exists!"
            label.isVisible = true
        } else {

        }
    }
}



