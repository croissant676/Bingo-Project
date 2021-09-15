package dev.kason.bingo.cards

import com.ibm.jvm.format.TraceArgs.override
import kotlin.random.Random

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
        val list = numbers[relation]
        val tile = list.singleOrNull { !it.crossedOff && it.value == number } ?: return
        tile.crossedOff = true
    }

}

class BingoGame(val seed: Long, var desiredNumberOfWinners: Int = -1, val cards: MutableList<BingoCard>) : List<BingoCard> {

    val game = Random(seed - 1)

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
        val relation = number / 15 // So we can easily check the columns
        for(card in cards) {
            card(number, relation)
        }
    }

    var viewMap = hashMapOf<Int, CardView>()

}

