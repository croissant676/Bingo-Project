package dev.kason.bingo.util

import dev.kason.bingo.ui.Styles
import javafx.scene.control.Button
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.RadialGradient
import javafx.scene.paint.Stop
import tornadofx.runLater
import tornadofx.style


private fun radialGradient(state: Int): RadialGradient {
    return RadialGradient(
        0.0, 0.0, 0.5, -0.4,
        1.5, true, CycleMethod.NO_CYCLE,
        Stop(0.01, Styles.secondaryThemeColor), Stop(state / 30.0, Styles.secondaryThemeColor),
        Stop(state / 30.0, Styles.themeColor), Stop(1.0, Styles.themeColor)
    )
}

fun Button.addHoverEffect() {
    var stateOfHover = 0
    runInsideLoop {
        if (!isVisible) {
            exitLoop()
        }
        if (isHover && stateOfHover <= 30) {
            stateOfHover++
        } else if (stateOfHover > 0) {
            stateOfHover -= 2
        }
        if (stateOfHover >= 1) {
            runLater {
                style {
                    if (stateOfHover in 1 until 30) {
                        backgroundColor += radialGradient(stateOfHover)
                    } else if (stateOfHover >= 30) {
                        backgroundColor += Styles.secondaryThemeColor
                    }
                }
            }
        }
    }
}


