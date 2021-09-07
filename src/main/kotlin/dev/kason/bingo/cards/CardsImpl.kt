package dev.kason.bingo.cards

import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage

data class BingoTile(val value: Int) {
    var crossedOff = false
}

data class BingoCard (val numbers: List<List<BingoTile>>) {
    operator fun get(row: Int) = numbers[row]
}

fun generateImage(bingoCard: BingoCard): BufferedImage {
    val image = BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()

    val width = 442
    val height = 499
    // We'll repaint a lot, and using a boolean
    // We can find whether we have painted before
    // If we have, there's no need to repaint over and over again.
    graphics.color = Color(37, 118, 179)
    graphics.fillRect(0, 0, width, height)
    graphics.color = Color(166, 188, 218)
    graphics.fillRect(10, 10, width - 20, height - 20)
    graphics.fillRect(0, height - 5, width, 67)

    graphics.color = Color(240, 245, 173)
    graphics.fillRect(25, 25, width - 50, 100)

    graphics.color = Color.WHITE
    graphics.fillRect(25, 135, width - 50, 290)
    graphics.color = Color(250, 250, 250)
    graphics.fillRect(7, 443, width - 150, 50)

    graphics.color = Color.BLACK
    // Do horizontal stuff
    // Do horizontal stuff
    for (count in 0..5) {
        graphics.drawLine(45, 145 + count * 54, width - 50, 145 + count * 54)
        graphics.drawLine(45 + count * 81, 145, 45 + count * 81, 415)
    }
    graphics.color = Color(240, 245, 173)
    graphics.fillRect(208, 254, 80, 53)

    graphics.color = Color.BLACK
    graphics.font = Font("Verdana", Font.PLAIN, 80)
    graphics.drawString("B I N G O", 60, 105)
    graphics.font = Font("Verdana", Font.PLAIN, 25)
    return image
}