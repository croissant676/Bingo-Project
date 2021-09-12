package dev.kason.bingo.cards

import kotlin.jvm.internal.Intrinsics
import kotlin.properties.Delegates
import kotlin.random.Random

fun generateNumbers(seed: Long = 0, count: Int = 1): List<BingoCard> {
    val arrayList = ArrayList<BingoCard>(count)
    val random = Random(seed)
    repeat(count) {
        arrayList += BingoCard(List(5) { row ->
            val existingNumbers = arrayListOf<Int>()
            val list = MutableList(5) { col ->
                BingoTile(run {
                    if (row == 2 && col == 2) {
                        return@run -1
                    }
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
    return arrayList
}


fun outputNumbers(card: BingoCard) {
    val stringBuilders = Array(5) { StringBuilder() }
    for (index in 0 until 5) {
        for (index2 in card[index].indices) {
            stringBuilders[index2].append(card[index][index2]).append('\t')
        }
    }
    for (index in 0 until 5) {
        println(stringBuilders[index])
    }
    println("====".repeat(5))
}