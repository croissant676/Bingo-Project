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

fun generateImage(bingoCard: BingoCard) {
    return
}