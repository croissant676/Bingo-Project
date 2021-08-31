package dev.kason.bingo.control

import dev.kason.bingo.ui.Styles.Companion.sizeOfText
import dev.kason.bingo.ui.registerTheme
import dev.kason.bingo.util.addHoverEffect
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.*

var currentAppearance = Appearance.DEFAULT

@Suppress("SpellCheckingInspection")
enum class Appearance(
    val themeColor: String,
    val secondaryThemeColor: String,
    val tertiaryThemeColor: String,
    val darkTextFill: String,
    val lightTextFill: String,
    val themeBackgroundColor: String
) {
    DEFAULT("0483ff", "0076ea", "0069d1", "151554", "f5faff", "f5faff"),
    PURPLE("7447d1", "6533cc", "5b2eb8", "150c29", "f5f0fe", "f5f0fe"),
    MONO("767676", "595959", "3c3c3c", "000000", "f3f3f3", "ffffff"),
    RED("ff6767", "ea0000", "b70000", "1a0000", "ffdede", "fff2f2"),

    ;

    fun display(): Node {
        val properName = name.toLowerCase().capitalize()
        return VBox(Button("Select").apply {
            style {
                textFill = c(lightTextFill)
                backgroundColor += c(themeColor)
                padding = box(1.px, 12.px)
                borderWidth += box(1.px, 3.px)
                borderRadius += box(0.px)
                backgroundRadius += box(0.px)
                fontFamily = "dubai"
                fontSize = sizeOfText
            }
            action {
                registerTheme(this@Appearance)
            }
            addHoverEffect(c(themeColor), c(secondaryThemeColor))
        }, Label(properName).apply {
            style {
                textFill = c(darkTextFill)
                padding = box(1.px)
                fontFamily = "dubai"
                fontSize = sizeOfText
            }
        }).apply {
            alignment = Pos.CENTER
            style {
                backgroundColor += c(themeBackgroundColor)
                borderWidth += box(2.px)
                borderColor += box(c(tertiaryThemeColor))
                padding = box(5.px, 10.px)
            }
            maxWidth = 110.0
        }
    }
}