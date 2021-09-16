package dev.kason.bingo.cards

import kotlin.random.Random

class BingoTile(val value: Int) {

    var crossedOff = false

    override fun toString(): String {
        return "BingoTile~{value:$value,crossedOff:$crossedOff}"
    }
}

data class BingoCard(val numbers: List<List<BingoTile>>, val randomSeed: Long = 0, val cardNumber: Int) {

    var isFinished = -1
        private set
    private val eventLogger = EventLogger(cardNumber)
    val totalNumbers = arrayListOf<BingoTile>()
    val remainingNumbers = arrayListOf<BingoTile>()

    operator fun get(row: Int) = numbers[row]

    operator fun invoke(number: Int, relation: Int = (number - 1) / 15) {
        println(eventLogger)
        val list = numbers[relation]
        for (value in 0 until 5) {
            if (list[value].value == number) {
                val tile = list[value]
                tile.crossedOff = true
                eventLogger += ev(number.toByte(), value.toByte())
            }
        }
    }

    fun checkFinished() {
        if (isFinished != -1) return
        if (checkHorizontal() || checkVertical() || checkDiagonals()) {
            isFinished = currentRound
            // May switch to new implementation soon
            // Where it keeps track of longer lines
            // and then detects any changes in their size
            // If the size is 5, return true
            // Problem with that is, I'm too lazy to implement
            // that, and it seems like a lot of work :)
        }
    }

    private fun checkHorizontal(): Boolean {
        for (row in 0 until 5) {
            var count = 0
            for (col in 0 until 5) {
                if (numbers[row][col].crossedOff) {
                    count++
                }
            }
            if (count == 5) {
                return true
            }
        }
        return false
    }

    private fun checkVertical(): Boolean {
        for (row in 0 until 5) {
            var count = 0
            for (col in 0 until 5) {
                if (numbers[col][row].crossedOff) {
                    count++
                }
            }
            if (count == 5) {
                return true
            }
        }
        return false
    }

    private fun checkDiagonals(): Boolean {
        var count = 0
        for (number in 0 until 5) {
            if (numbers[number][number].crossedOff) {
                count++
            }
        }
        if (count == 5) {
            return true
        }
        count = 0
        for (number in 0 until 5) {
            if (numbers[number][4 - number].crossedOff) {
                count++
            }
        }
        if (count == 5) {
            return true
        }
        return false
    }

}

class BingoGame(val seed: Long, var desiredNumberOfWinners: Int = -1, val cards: MutableList<BingoCard>) : List<BingoCard> {

    private val random = Random(seed - 1)

    var currentRoundNumber = 0

    override val size: Int
        get() = cards.size

    override fun contains(element: BingoCard): Boolean {
        return cards.contains(element)
    }

    override fun containsAll(elements: Collection<BingoCard>): Boolean {
        return cards.containsAll(elements)
    }

    override operator fun get(index: Int): BingoCard {
        return cards[index]
    }

    override fun indexOf(element: BingoCard): Int {
        return cards.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return cards.isEmpty()
    }

    override fun iterator(): Iterator<BingoCard> {
        return cards.iterator()
    }

    override fun lastIndexOf(element: BingoCard): Int {
        return cards.lastIndexOf(element)
    }

    override fun listIterator(): ListIterator<BingoCard> {
        return cards.listIterator()
    }

    override fun listIterator(index: Int): ListIterator<BingoCard> {
        return cards.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<BingoCard> {
        return cards.subList(fromIndex, toIndex)
    }

    override fun toString(): String {
        return cards.toString()
    }

    fun check(number: Int) {
        if (number == -1) return
        alreadyExistingNumbers += number
        val relation = (number - 1) / 15 // So we can easily check the columns
        for (card in cards) {
            card(number, relation)
        }
    }

    var viewMap = hashMapOf<Int, CardView>()

    private val alreadyExistingNumbers = arrayListOf<Int>()

    fun generateRandomNumber(): Int {
        if (alreadyExistingNumbers.size == 75) return -1
        var number: Int
        do {
            number = random.nextInt(75) + 1
        } while (number in alreadyExistingNumbers)
        return number
    }

}
