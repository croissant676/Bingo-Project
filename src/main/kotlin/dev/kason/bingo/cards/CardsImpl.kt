package dev.kason.bingo.cards

class BingoTile(val value: Int) {
    var crossedOff = false
    override fun toString(): String {
        return "BingoTile~{value:$value,crossedOff:$crossedOff}"
    }
}

data class BingoCard(val numbers: List<List<BingoTile>>, val randomSeed: Long = 0, val cardNumber: Int) {
    val totalNumbers = arrayListOf<BingoTile>()
    val remainingNumbers = arrayListOf<BingoTile>()
    operator fun get(row: Int) = numbers[row]
    operator fun invoke(number: Int, relation: Int = number / 15) {

    }
}

class BingoGame(seed: Int) {

}