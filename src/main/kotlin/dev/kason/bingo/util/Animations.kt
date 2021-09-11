package dev.kason.bingo.util

import dev.kason.bingo.control.Appearance
import dev.kason.bingo.ui.Styles
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.RadialGradient
import javafx.scene.paint.Stop
import tornadofx.c
import tornadofx.runLater
import tornadofx.style

fun Button.addHoverEffect() {
    var stateOfHover = 0
    runInsideLoop {
        if (!isVisible) {
            exitLoop()
        } else {
            if (isHover && stateOfHover <= 30) {
                stateOfHover++
            } else if (stateOfHover > 0) {
                stateOfHover -= 2
            }
            if (stateOfHover >= 1) {
                runLater {
                    style {
                        if (stateOfHover in 1 until 30) {
                            backgroundColor += RadialGradient(
                                0.0,
                                0.0,
                                0.5,
                                -0.4,
                                1.5,
                                true,
                                CycleMethod.NO_CYCLE,
                                Stop(0.01, Styles.secondaryThemeColor),
                                Stop(stateOfHover / 30.0, Styles.secondaryThemeColor),
                                Stop(stateOfHover / 30.0, Styles.themeColor),
                                Stop(1.0, Styles.themeColor)
                            )
                        } else if (stateOfHover >= 30) {
                            backgroundColor += Styles.secondaryThemeColor
                        }
                    }
                }
            } else {
                runLater {
                    style {
                        backgroundColor += Styles.themeColor
                    }
                }
            }
        }
    }
}

fun Button.addHoverEffect(defaultColor: Color, newColor: Color) {
    var stateOfHover = 0
    runInsideLoop {
        if (!isVisible) {
            exitLoop()
        } else {
            if (isHover && stateOfHover <= 30) {
                stateOfHover++
            } else if (stateOfHover > 0) {
                stateOfHover -= 2
            }
            if (stateOfHover >= 1) {
                runLater {
                    style {
                        if (stateOfHover in 1 until 30) {
                            backgroundColor += RadialGradient(
                                0.0, 0.0, 0.5, -0.4,
                                1.5, true, CycleMethod.NO_CYCLE,
                                Stop(0.01, newColor), Stop(stateOfHover / 30.0, newColor),
                                Stop(stateOfHover / 30.0, defaultColor), Stop(1.0, defaultColor)
                            )
                        } else if (stateOfHover >= 30) {
                            backgroundColor += newColor
                        }
                    }
                }
            }
        }
    }
}

fun Button.addHoverEffectAppearance(appearance: Appearance) {
    var stateOfHover = 0
    val defaultColor = c(appearance.themeColor)
    val newColor = c(appearance.secondaryThemeColor)
    val textF = c(appearance.lightTextFill)
    runInsideLoop {
        if (!isVisible) {
            exitLoop()
        } else {
            if (isHover && stateOfHover <= 30) {
                stateOfHover++
            } else if (stateOfHover > 0) {
                stateOfHover -= 2
            }
            if (stateOfHover >= 1) {
                runLater {
                    style {
                        textFill = textF
                        if (stateOfHover in 1 until 30) {
                            backgroundColor += RadialGradient(
                                0.0, 0.0, 0.5, -0.4,
                                1.5, true, CycleMethod.NO_CYCLE,
                                Stop(0.01, newColor), Stop(stateOfHover / 30.0, newColor),
                                Stop(stateOfHover / 30.0, defaultColor), Stop(1.0, defaultColor)
                            )
                        } else if (stateOfHover >= 30) {
                            backgroundColor += newColor
                        }
                    }
                }
            }
        }
    }
}