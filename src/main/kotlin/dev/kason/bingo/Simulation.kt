package dev.kason.bingo

import kotlin.random.Random

var days = 0

val balls = ArrayList<Int>(75)
val cardFinished = ArrayList<Int>(currentGame.size)

var numberOfWinners = 0

fun createSimulation() {
    val seed = currentGame.seed
    numbers()
    balls.forEach {
        println(it)
    }
    for (card in currentGame) {
        var round = 0
        while (round < 4 || !card.checkFinished()) {
            val number = balls[round]
            for (pos in card[(number - 1) / 15]) {
                if (pos.value == number) {
                    pos.crossedOff = true
                }
            }
            round++
        }
        cardFinished += round
        card.reset()
    }
    val new = cardFinished.sorted()
    println(new)
}

private fun numbers() {
    val random = Random(currentGame.seed - 1)
    repeat(75) {
        var number: Int
        do {
            number = random.nextInt(75) + 1
        } while (number in balls)
        balls += number
    }
}
