package dev.kason.bingo.ui

import javafx.geometry.Pos
import tornadofx.*

class Styles : Stylesheet() {

    companion object {
        // CSS Classes
        val button by cssclass()
        val titleLabel by cssclass()
        val regularLabel by cssclass()
        val progressIndicator by cssclass()
        val defaultBackground by cssclass()

        // Changeable Colors
        @JvmStatic
        var themeColor = c("0483ff")
            internal set

        @JvmStatic
        var secondaryThemeColor = c("0076ea")
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
            borderColor += box(secondaryThemeColor)
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
    }
}