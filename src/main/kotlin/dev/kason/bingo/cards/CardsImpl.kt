package dev.kason.bingo.cards

import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage

class BingoTile(val value: Int) {
    private var crossedOff = false
    override fun toString(): String {
        return "BingoTile~{value:$value,crossedOff:$crossedOff}"
    }
}

data class BingoCard (val numbers: List<List<BingoTile>>) {
    operator fun get(row: Int) = numbers[row]
}

fun generateImage(bingoCard: BingoCard) {
    return
}