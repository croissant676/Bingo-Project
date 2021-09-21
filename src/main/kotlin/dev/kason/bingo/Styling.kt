package dev.kason.bingo

import javafx.scene.effect.Reflection
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

fun registerTheme(theme: Appearance) {
    currentAppearance = theme
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
        val defaultSpinner by cssclass()
        val defaultPicker by cssclass()
        val defaultTreeView by cssclass()
        val redTile by cssclass()
        val textField by cssclass()

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
        spinner {
            incrementArrowButton {
                backgroundColor += themeColor
                and(hover) {
                    backgroundColor += secondaryThemeColor
                }
            }
            incrementArrow {
                backgroundColor += lightTextColor
            }
            decrementArrowButton {
                backgroundColor += themeColor
                and(hover) {
                    backgroundColor += secondaryThemeColor
                }
            }
            decrementArrow {
                backgroundColor += lightTextColor
            }
            accentColor = themeColor
            borderWidth += box(0.px)
            borderRadius += box(0.px)
            backgroundRadius += box(0.px)
            effect = Reflection()
        }
        defaultPicker {
            textInput {
                backgroundColor += themeBackgroundColor
                padding = box(3.0.px)
                borderRadius += box(0.px)
                padding = box(3.px, 9.px)
                fontFamily = "dubai"
                fontSize = sizeOfText
                textFill = darkTextFill
            }
            arrowButton {
                backgroundColor += themeColor
                padding = box(1.px, 12.px)
                borderRadius += box(0.px)
                backgroundRadius += box(0.px)
                borderColor += box(Color.TRANSPARENT)
            }
            datePickerPopup {
                backgroundColor += themeColor
                padding = box(3.0.px)
                borderRadius += box(0.px)
                backgroundRadius += box(0.px)
                borderColor += box(Color.TRANSPARENT)
                button {
                    backgroundColor += themeColor
                    and(hover) {
                        backgroundColor += secondaryThemeColor
                    }
                }
                label {
                    // Regular label
                    textFill = lightTextColor
                    padding = box(1.px)
                    fontFamily = "dubai"
                    fontSize = sizeOfText
                }
            }
            monthYearPane {
                backgroundColor += themeColor
                padding = box(1.px, 12.px)
                borderWidth += box(1.px, 3.px)
                borderRadius += box(0.px)
                backgroundRadius += box(0.px)
                borderColor += box(Color.TRANSPARENT)
            }
            weekNumberCell {
                textFill = darkTextFill
                fontSize = sizeOfText
                fontFamily = "dubai"
                backgroundColor += themeBackgroundColor
            }
            dayCell {
                textFill = darkTextFill
                fontSize = sizeOfText
                fontFamily = "dubai"
                backgroundColor += themeBackgroundColor
                and(hover) {
                    backgroundColor += themeColor
                    textFill = lightTextColor
                }
            }
            dayNameCell {
                textFill = darkTextFill
                fontSize = sizeOfText
                fontFamily = "dubai"
                backgroundColor += themeBackgroundColor
            }
        }
        defaultTreeView {
            backgroundColor += themeBackgroundColor
            treeCell {
                textFill = darkTextFill
                padding = box(1.px)
                fontFamily = "dubai"
                fontSize = sizeOfText
                backgroundColor += themeBackgroundColor
//                and(hover) {
//                    textFill = lightTextColor
//                    backgroundColor += tertiaryThemeColor
//                }
                and(selected) {
                    textFill = lightTextColor
                    backgroundColor += themeColor
                    fontWeight = FontWeight.BOLD
                    arrow {
                        backgroundColor += darkTextFill
                        borderColor += box(tertiaryThemeColor)
                        borderWidth += box(0.5.px)
                    }
                }
                arrow {
                    backgroundColor += darkTextFill
                    borderColor += box(themeBackgroundColor)
                    borderWidth += box(1.px)
                }
                cellHeight = 20.px
            }
        }
        redTile {
            borderRadius += box(0.px)
            backgroundRadius += box(0.px)
            backgroundColor += c("ff4e6c")
            textFill = lightTextColor
            fontFamily = "dubai"
            fontSize = 30.px
            padding = box(2.px)
        }
        menuBar {
            backgroundColor += themeBackgroundColor
            menu {
                fontFamily = "dubai"
                fontSize = sizeOfText
                backgroundColor += themeBackgroundColor
                textFill = darkTextFill
                and(hover) {
                    backgroundColor += themeColor
                    label {
                        textFill = lightTextColor
                    }
                }
                arrow {
                    backgroundColor += darkTextFill
                }
                label {
                    textFill = darkTextFill
                }
                menuItem {
                    backgroundColor += themeBackgroundColor
                    fontFamily = "dubai"
                    fontSize = sizeOfText
                    backgroundColor += themeBackgroundColor
                    textFill = darkTextFill
                    and(hover) {
                        backgroundColor += themeColor
                        label {
                            textFill = lightTextColor
                        }
                    }
                    label {
                        textFill = darkTextFill
                    }
                }
                menuButton {
                    fontFamily = "dubai"
                    fontSize = sizeOfText
                    backgroundColor += themeBackgroundColor
                    textFill = darkTextFill
                    and(hover) {
                        backgroundColor += themeColor
                        label {
                            textFill = lightTextColor
                        }
                    }
                    label {
                        textFill = darkTextFill
                    }
                }
            }
        }
        textField {
            backgroundColor += themeBackgroundColor
            borderColor += box(darkTextFill)
            borderWidth += box(0.px, 0.px, 2.px, 0.px)
//            effect = Reflection()
            fontFamily = "dubai"
            fontSize = sizeOfText
            accentColor = themeColor
            textFill = darkTextFill
        }
        radioButton {
            accentColor = darkTextFill
            backgroundColor += themeBackgroundColor
            fontFamily = "dubai"
            fontSize = sizeOfText
            radio {
                borderRadius += box(15.px)
                borderColor += box(darkTextFill)
                and(hover) {
                    borderColor += box(themeColor)
                }
                and(selected) {
                    borderColor += box(lightTextColor)
                    accentColor = darkTextFill
                    backgroundColor += darkTextFill
                }
                backgroundColor += themeBackgroundColor
            }
            dot {
                backgroundRadius += box(sizeOfText)
                backgroundColor += themeBackgroundColor
            }
            and(selected) {
                dot {
                    backgroundColor += darkTextFill
                }
            }
            textFill = darkTextFill
        }
        scrollPane {
            scrollBar {
                backgroundColor += Color.TRANSPARENT
                borderColor += box(Color.SLATEGREY)
                borderWidth += box(0.px, 0.px, 0.px, 2.px)
                thumb {
                    backgroundColor += c(Appearance.MONO.themeColor)
                    backgroundRadius += box(123.px)
                }
            }
        }
        textArea {
            borderWidth += box(1.px)
            backgroundColor += themeBackgroundColor
            borderColor += box(themeColor)
            and(hover) {
                borderWidth += box(3.px)
            }
        }
    }
}
