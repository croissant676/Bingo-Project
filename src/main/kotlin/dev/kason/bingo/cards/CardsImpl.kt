package dev.kason.bingo.cards

class BingoTile(val value: Int) {
    var crossedOff = false
    override fun toString(): String {
        return "BingoTile~{value:$value,crossedOff:$crossedOff}"
    }
}

data class BingoCard(val numbers: List<List<BingoTile>>) {
    val totalNumbers = arrayListOf<BingoTile>()
    val remainingNumbers = arrayListOf<BingoTile>()
    operator fun get(row: Int) = numbers[row]
}
