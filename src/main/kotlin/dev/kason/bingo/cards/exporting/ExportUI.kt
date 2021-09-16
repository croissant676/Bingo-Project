package dev.kason.bingo.cards.exporting

import dev.kason.bingo.cards.EditingCardView
import dev.kason.bingo.cards.currentGame
import dev.kason.bingo.control.Appearance
import dev.kason.bingo.ui.FileView
import dev.kason.bingo.ui.Styles
import dev.kason.bingo.util.addHoverEffect
import dev.kason.bingo.util.addHoverEffectAppearance
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.paint.Color
import tornadofx.*
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.io.File
import kotlin.properties.Delegates


object WrapperFileView : View("Bingo > Wrapper Transition File") {
    override val root = vbox {
        alignment = Pos.CENTER
        addClass(Styles.defaultBackground)
    }

    init {
        replaceWith(FindFileView("string.pdf") {
            println("Wrapper completed!")
        }, ViewTransition.Slide(0.5.seconds))
    }
}

lateinit var currentFFView: FindFileView

/**
 * Types of file
 *
 *  0 = pdf
 *
 * 1 = word
 *
 * 2 = pptx
 *
 * 3 = jpg (folder)
 *
 * 4 = png (folder)
 *
 * 5 = gif (folder)
 *
 * 6 = jpg (folder)
 *
 * 7 = png (folder)
 *
 * 8 = gif (folder)
 *
 * 9 = txt
 *
 * 10 = other text options
 * */
var typeOfFile by Delegates.notNull<Int>()

class FindFileView(var string: String, var whenFinished: FindFileView.() -> Unit = {}) : View("Bingo > Locate File") {
    private lateinit var filePath: TextField
    private lateinit var fileName: TextField

    var result: String = ""
        private set

    lateinit var label: Label

    override val root: Parent = borderpane {
        val file = File(FindFileView::class.java.protectionDomain.codeSource.location.toURI()).parentFile.parentFile.parentFile
        currentFFView = this@FindFileView
        FileView.called = this@FindFileView
        center {
            vbox {
                label("Input the location of the file.") {
                    addClass(Styles.titleLabel)
                }
                vbox {
                    fileName = textfield(string) {
                        alignment = Pos.CENTER
                    }
                    label("Name of file") {
                        addClass(Styles.regularLabel)
                        style(append = true) {
                            fontSize = 10.px
                        }
                    }
                    alignment = Pos.TOP_LEFT
                }
                vbox {
                    filePath = textfield(file.path) {
                        alignment = Pos.CENTER
                    }
                    label("Location of file") {
                        addClass(Styles.regularLabel)
                        style(append = true) {
                            fontSize = 10.px
                        }
                    }
                    alignment = Pos.TOP_LEFT
                }
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
            }
        }
        left {
            region {
                style {
                    backgroundColor += Styles.themeBackgroundColor
                }
                minWidth = 70.0
            }
        }
        right {
            region {
                style {
                    backgroundColor += Styles.themeBackgroundColor
                }
                minWidth = 70.0
            }
        }
        top {
            hbox {
                label = label("Area where the label appears") {
                    addClass(Styles.regularLabel)
                    style {
                        textFill = c("ff4e6c")
                    }
                    isVisible = false
                }
                paddingTop = 30.0
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
            }
        }
        FileView.startingLocation = file.path
        FileView.indexFiles()
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds, ViewTransition.Direction.RIGHT))
                    }
                }
                button("Select file from system") {
                    addHoverEffect()
                    action {
                        replaceWith(FileView, ViewTransition.Fade(0.5.seconds))
                    }
                }
                button("Proceed to next step >") {
                    addHoverEffect()
                    action {
                        if (fileName.text == null || !fileName.text.endsWith(string.substringAfterLast('.')) || filePath.text == null) {
                            if (fileName.text == null) {
                                label.text = "Must enter a file name!"
                            } else if (!fileName.text.endsWith(string.substringAfterLast('.'))) {
                                label.text = "File extension must be \"${string.substringAfterLast('.')}\"!"
                            } else {
                                label.text = "Must enter a file path!"
                            }
                            label.isVisible = true
                        } else {
                            result = if (!filePath.text.endsWith('\\')) {
                                "${filePath.text}\\${fileName.text}"
                            } else {
                                "${filePath.text}${fileName.text}"
                            }
                            whenFinished(this@FindFileView)
                        }
                    }
                }
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
                paddingBottom = 30.0
            }
        }
        addClass(Styles.defaultBackground)
    }
}

object ExportTextView : View("Bingo > Export text") {

    private lateinit var toggleGroup: ToggleGroup
    private lateinit var spinner: Spinner<Int>

    override val root: Parent = borderpane {
        center {
            vbox {
                label("Exporting Text to Clipboard") {
                    addClass(Styles.titleLabel)
                }
                toggleGroup = togglegroup {
                    radiobutton("Export all cards")
                    radiobutton("Export a single card")
                }
                spinner = spinner(1, currentGame.size)
                alignment = Pos.CENTER
                spacing = 10.0
                addClass(Styles.defaultBackground)
            }
        }
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        this@ExportTextView.replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds, ViewTransition.Direction.RIGHT))
                    }
                }
                button("Next >") {
                    addHoverEffect()
                    action {
                        if ((toggleGroup.selectedToggle as? RadioButton)?.text!!.contains("all")) {
                            val selection = StringSelection(generateString(currentGame))
                            val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
                            clipboard.setContents(selection, selection)
                        } else {
                            val selection = StringSelection(generateString(currentGame[spinner.value]))
                            val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
                            clipboard.setContents(selection, selection)
                        }
                        this@ExportTextView.replaceWith(ExportCompleted("Export Complete", {
                            replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                        }, "Finished copying game into clipboard"), ViewTransition.Slide(0.5.seconds))
                    }
                }
                paddingBottom = 30.0
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
            }
        }
        addClass(Styles.defaultBackground)
    }

}

class ExportCompleted(val string: String = "Exporting", val action: ExportCompleted.() -> Unit = {}, private val message: String = string) :
    View("Bingo > $string") {
    override val root = borderpane {
        center {
            label(message) {
                addClass(Styles.regularLabel)
                style(append = true) {
                    textFill = c(Appearance.GREEN.darkTextFill)
                }
            }
        }
        bottom {
            hbox {
                button("Next >") {
                    addHoverEffectAppearance(Appearance.GREEN)
                    fire()
                    action {
                        action(this@ExportCompleted)
                    }
                    style(append = true) {
                        textFill = c(Appearance.GREEN.lightTextFill)
                        padding = box(1.px, 12.px)
                        borderWidth += box(1.px, 3.px)
                        borderRadius += box(0.px)
                        backgroundRadius += box(0.px)
                        borderColor += box(Color.TRANSPARENT)
                        fontFamily = "dubai"
                        fontSize = Styles.sizeOfText
                        backgroundColor += c(Appearance.GREEN.themeBackgroundColor)
                    }
                }
                paddingBottom = 30.0
                alignment = Pos.CENTER
            }
        }
        style {
            backgroundColor += c(Appearance.GREEN.themeBackgroundColor)
        }
    }
}