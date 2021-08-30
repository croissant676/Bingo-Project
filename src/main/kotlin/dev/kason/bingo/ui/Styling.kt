package dev.kason.bingo.ui

import dev.kason.bingo.control.Appearance
import dev.kason.bingo.util.restartLoop
import javafx.scene.paint.Color
import tornadofx.*

fun registerTheme(theme: Appearance) {
    with(Styles.Companion) {
        themeColor = c(theme.themeColor)
        secondaryThemeColor = c(theme.secondaryThemeColor)
        tertiaryThemeColor = c(theme.tertiaryThemeColor)
        darkTextFill = c(theme.darkTextFill)
        lightTextColor = c(theme.lightTextFill)
        themeBackgroundColor = c(theme.themeBackgroundColor)
    }
    removeStylesheet<Styles>()
    importStylesheet<Styles>()
}

class Styles : Stylesheet() {

    companion object {
        // CSS Classes
        val button by cssclass()
        val titleLabel by cssclass()
        val regularLabel by cssclass()
        val progressIndicator by cssclass()
        val defaultBackground by cssclass()
        val tab by cssclass()
        val tabPane by cssclass()
        val redButton by cssclass()
        val themeButton by cssclass()

        // Changeable Colors
        @JvmStatic
        var themeColor = c("0483ff")
            internal set

        @JvmStatic
        var secondaryThemeColor = c("0076ea")
            internal set

        @JvmStatic
        var tertiaryThemeColor = c("0069d1")
            internal set

        @JvmStatic
        var darkTextFill = c("151554")
            internal set

        @JvmStatic
        var lightTextColor = c("f5faff")
            internal set

        @JvmStatic
        var themeBackgroundColor = c("f5faff")
            internal set

        @JvmStatic
        val sizeOfTitle = 36.px

        @JvmStatic
        val sizeOfText = 18.px
    }

    init {
        button {
            textFill = lightTextColor
            backgroundColor += themeColor
            padding = box(1.px, 12.px)
            borderWidth += box(1.px, 3.px)
            borderRadius += box(0.px)
            backgroundRadius += box(0.px)
            borderColor += box(Color.TRANSPARENT)
            fontFamily = "dubai"
            fontSize = sizeOfText
        }
        titleLabel {
            textFill = darkTextFill
            padding = box(10.px)
            fontFamily = "dubai"
            fontSize = sizeOfTitle
        }
        regularLabel {
            textFill = darkTextFill
            padding = box(1.px)
            fontFamily = "dubai"
            fontSize = sizeOfText
        }
        progressIndicator {
            accentColor = themeColor
        }
        defaultBackground {
            backgroundColor += themeBackgroundColor
        }
        tab {
            borderWidth += box(1.px, 3.px)
            borderRadius += box(0.px)
            focusIndicator {
                borderColor += box(Color.TRANSPARENT)
            }
            backgroundRadius += box(0.px)
            backgroundColor += themeColor
            padding = box(5.px, 8.px)
            tabLabel {
                textFill = lightTextColor
                padding = box((-5).px, 10.px)
                fontFamily = "dubai"
                fontSize = sizeOfText
            }
            and(hover) {
                backgroundColor += secondaryThemeColor
            }
            and(selected) {
                backgroundColor += tertiaryThemeColor
            }
        }
        redButton {
            textFill = lightTextColor
            backgroundColor += c("ff4e6c")
            padding = box((-1).px, 12.px)
            borderWidth += box(1.px, 1.px)
            borderRadius += box(0.px)
            backgroundRadius += box(0.px)
            fontFamily = "dubai"
            fontSize = sizeOfText
        }
        tabPane {
            backgroundColor += themeBackgroundColor
            tabHeaderBackground {
                opacity = 0.0
            }
        }
    }
}