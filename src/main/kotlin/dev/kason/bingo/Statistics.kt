package dev.kason.bingo

import javafx.scene.Parent
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Series
import tornadofx.*


class SingleCardStatistics(val card: BingoCard) : View("Bingo > View Card ${card.cardNumber}") {
    private lateinit var curCView: CardView
    private val listOfTiles = arrayListOf<Int>()
    override val root: Parent = borderpane {
        left {
            hbox { // Wrap
                curCView = CardView(card)
                add(curCView)
            }
        }
        right {
            vbox {
                spinner(0, 75, 1, enableScroll = true) {
                    isEditable = true
                    valueProperty().addListener { _, _, newInt ->
                        refresh(newInt)
                    }
                    editor.textProperty().addListener { _, oldValue, newValue ->
                        if (newValue.length > 7) {
                            editor.text = oldValue
                        } else if (!newValue.matches("\\d*".toRegex())) {
                            editor.text = newValue.replace("[^\\d]".toRegex(), "")
                        }
                    }
                }
                val xAxis = NumberAxis("Round", 0.0, 75.0, 25.0)
                val yAxis = NumberAxis("Number Covered", 0.0, 25.0, 5.0)
                linechart("Number of tiles covered", xAxis, yAxis) {
                    val series = Series<Number, Number>()
                    for(number in 0 until 75) {
                        series.data.add(XYChart.Data(12, 24))
                    }
                    data.add(series)
                }
                spacing = 50.0
            }
        }
    }

    private fun refresh(round: Int) {
        doUntilRound(round)
        val cardView = CardView(card)
        curCView.replaceWith(cardView)
        curCView = cardView
    }

    private fun initializeRoundData() {
        var count = 0
        for(number in 0 until 75) {
            val otherN = balls[number]
            for (pos in card[(otherN - 1) / 15]) {
                if (pos.value == otherN) {
                    count++
                    break
                }
            }
            listOfTiles += count
        }
    }

    private fun doUntilRound(round: Int) {
        card.reset()
        for (number in 0 until round) {
            val otherN = balls[round]
            for (pos in card[(otherN - 1) / 15]) {
                if (pos.value == otherN) {
                    pos.crossedOff = true
                    break
                }
            }
            if (card.checkFinished()) {
                break
            }
        }
    }
}