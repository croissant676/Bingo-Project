package dev.kason.bingo

import javafx.scene.control.RadioButton
import java.util.*
import kotlin.math.floor
import kotlin.random.Random


var days = 0

val balls = ArrayList<Int>(75)
val cardFinished = ArrayList<Int>(currentGame.size)

val dayDraws = ArrayList<Int>()
val ballsDates = ArrayList<String>(75)

var numberOfWinners = 0
var numberOfRoundsTotal = 0
val sortedFinished by lazy {
    cardFinished.sorted()
}

fun createSimulation() {
    numbers()
    for (card in currentGame) {
        var round = 0
        while (round < 4 || !card.checkFinished()) {
            val number = balls[round]
            for (pos in card[(number - 1) / 15]) {
                if (pos.value == number) {
                    pos.crossedOff = true
                    break
                }
            }
            round++
        }
        cardFinished += round
        card.reset()
    }
    println(cardFinished)
    numberOfRoundsTotal = sortedFinished[currentGame.desiredNumberOfWinners - 1]
    println(numberOfWinners)
    println(numberOfRoundsTotal)
    when ((CreationMenuView.toggleGroup.selectedToggle as? RadioButton)?.text ?: return) {
        "be spread out." -> {
            val rem = (numberOfRoundsTotal / days.toDouble()) - floor(numberOfRoundsTotal / days.toDouble())
            var extra = 0.0
            for (count in 0 until days) {
                var posValue = floor(numberOfRoundsTotal / days.toDouble()).toInt()
                extra += rem
                if (extra > 0.99) {
                    extra--
                    posValue++
                }
                dayDraws += posValue
            }
        }
        "pushed towards the front." -> {
            val general = numberOfRoundsTotal / days
            val extra = numberOfRoundsTotal - general * days
            for (count in 0 until days) {
                dayDraws += if (count < extra) {
                    general + 1
                } else {
                    general
                }
            }
        }
        "pushed towards the back." -> {
            val general = numberOfRoundsTotal / days
            val extra = numberOfRoundsTotal - general * days
            for (count in 0 until days) {
                dayDraws += if (count >= days - extra) {
                    general + 1
                } else {
                    general
                }
            }
        }
    }
    val time = Calendar.getInstance()
    for(count in dayDraws) {
        val string = "${time[Calendar.MONTH]}/${time[Calendar.DAY_OF_MONTH]}/${time[Calendar.YEAR]}"
        repeat(count / 2) {
            ballsDates += "$string AM"
        }
        repeat(if(count and 1 == 0) count / 2 else count / 2 + 1) {
            ballsDates += "$string PM"
        }
        time.add(Calendar.DAY_OF_MONTH, 1)
    }
    println("Days should be $dayDraws")
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
