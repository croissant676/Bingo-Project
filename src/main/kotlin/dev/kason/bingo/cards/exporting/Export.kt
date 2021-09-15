package dev.kason.bingo.cards.exporting

import dev.kason.bingo.cards.BingoCard
import dev.kason.bingo.cards.BingoGame
import dev.kason.bingo.cards.EditingCardView
import dev.kason.bingo.util.runInsideLoop
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
        println(file.exists())
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
        if (file.exists()) {
            label.text = "$result already exists!"
            label.isVisible = true
        } else {

        }
    }
}

fun generateString(game: BingoGame): String {
    val stringBuffer = StringBuffer("Bingo Game\n\n")
    stringBuffer.append("Seed -- ${game.seed}\n")
    stringBuffer.append("# of cards: ${game.size}\n")
    stringBuffer.append("# of winners: ${game.desiredNumberOfWinners}\n\n")
    stringBuffer.append("=======================\n")
    for(card in game) {
        stringBuffer.append(generateString(card)).append('\n')
    }
    return stringBuffer.toString()
}

fun generateString(card: BingoCard): String {
    val separator = "+- --- --- --- --- -+\n"
    val builder = StringBuilder(separator)
    val cardNumber = card.cardNumber.toString()
    builder.append("|Card: $cardNumber ${" ".repeat(12 - cardNumber.length)}|\n")
        .append("+- -+- -+- -+- -+- -+\n")
        .append("| B | I | N | G | O |\n")
        .append("+- -+- -+- -+- -+- -+\n")
    for (col in 0 until 5) {
        for (row in 0 until 5) {
            if (row == 2 && col == 2) {
                builder.append("| F ")
                continue
            }
            val string = card[row][col].value.toString()
            if (row == 0) {
                builder.append("| $string" + if (string.length == 1) " " else "")
            } else {
                builder.append("| $string")
            }
        }
        builder.append("|\n+- -+- -+- -+- -+- -+\n")
    }
    return builder.toString()
}