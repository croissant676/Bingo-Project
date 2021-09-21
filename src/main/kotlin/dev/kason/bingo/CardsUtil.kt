package dev.kason.bingo

import kotlin.properties.Delegates
import kotlin.random.Random

var currentRound: Int = 1

fun createGame(seed: Long = 0, count: Int = 1): BingoGame {
    val arrayList = ArrayList<BingoCard>(count)
    val random = Random(seed)
    repeat(count) {
        arrayList += BingoCard(List(5) { row ->
            val existingNumbers = arrayListOf<Int>()
            val list = MutableList(5) { col ->
                if (row == 2 && col == 2) {
                    val tile = BingoTile(-1)
                    tile.crossedOff = true
                    return@MutableList tile
                }
                BingoTile(run {
                    var number by Delegates.notNull<Int>()
                    do {
                        number = random.nextInt(row * 15 + 1, (row + 1) * 15 + 1)
                    } while (number in existingNumbers)
                    existingNumbers += number
                    number
                })
            }
            list
        }, seed, it + 1)
    }
    val game = BingoGame(seed, cards = arrayList)
    currentGame = game
    return game
}


fun outputNumbers(card: BingoCard) {
    val stringBuilders = Array(5) { StringBuilder() }
    for (index in 0 until 5) {
        for (index2 in card[index].indices) {
            stringBuilders[index2].append(
                card[index][index2].optimizedToString()
            ).append('\t')
        }
    }
    for (index in 0 until 5) {
        println(stringBuilders[index])
    }
    println("====".repeat(5))
}

private fun BingoTile.optimizedToString(): String {
    return "$value ${if (crossedOff) "-" else "+"}  "
}

fun emptyBingoCard(): BingoCard {
    return BingoCard(numbers = List(5) { row ->
        val existingNumbers = arrayListOf<Int>()
        val list = MutableList(5) { col ->
            if (row == 2 && col == 2) {
                val tile = BingoTile(-1)
                tile.crossedOff = true
                return@MutableList tile
            }
            BingoTile(run {
                var number by Delegates.notNull<Int>()
                do {
                    number = Random.nextInt(row * 15 + 1, (row + 1) * 15 + 1)
                } while (number in existingNumbers)
                existingNumbers += number
                number
            })
        }
        return@List list
    }, -1, 0)
}

fun quickPrintGame() {
    val game = currentGame
    for (index in game.indices) {
        println("Game: $index")
        val card = game[index]
        val stringBuilders = Array(5) { StringBuilder() }
        for (index1 in 0 until 5) {
            for (index2 in card[index1].indices) {
                stringBuilders[index2].append(card[index1][index2]).append('\t')
            }
        }
        for (index1 in 0 until 5) {
            println(stringBuilders[index1])
        }
        println()
    }
    println("====".repeat(5))
}

fun quickPrintCard() {
    val card = currentlyDisplayedCard.card
    val stringBuilders = Array(5) { StringBuilder() }
    for (index1 in 0 until 5) {
        for (index2 in card[index1].indices) {
            stringBuilders[index2].append(card[index1][index2]).append('\t')
        }
    }
    for (index1 in 0 until 5) {
        println(stringBuilders[index1])
    }
    println()
}

@Suppress("MemberVisibilityCanBePrivate")
class EventLogger(val referenceNum: Int) {
    private val events: ArrayList<Event> = arrayListOf()

    class Event(val round: Int, val r: Byte, val c: Byte) {
        override fun toString(): String {
            return "E[$round: $r, $c]"
        }
    }

    private fun searchThroughEvents(number: Int): Int {
        // Was going to implement, but too lazy to :)
        return events.binarySearch { it.round - number }
    }

    operator fun get(index: Int): Int {
        return events[index].round
    }

    operator fun plus(number: Int): List<Event> {
        return events.subList(0, searchThroughEvents(number))
    }

    operator fun invoke(range: IntRange): List<Event> {
        return events.subList(searchThroughEvents(range.first), searchThroughEvents(range.last))
    }

    operator fun plusAssign(event: Event) {
        events += event
    }

    override fun toString(): String {
        if (events.isEmpty()) return ""
        val stringBuilder = StringBuilder("Card: $referenceNum -- {")
        for (event in events) {
            stringBuilder.append(event).append(',')
        }
        return "${stringBuilder.removePrefix(",")}}"
    }

}

fun ev(r: Byte, c: Byte) = EventLogger.Event(currentRound, r, c)