package dev.kason.bingo

import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.paint.Color
import tornadofx.*
import java.awt.Desktop
import java.io.File
import kotlin.properties.Delegates


object WrapperFileView : View("Bingo > Wrapper Transition File") {
    override val root = vbox {
        alignment = Pos.CENTER
        addClass(Styles.defaultBackground)
    }

    init {
        replaceWith(FindFileView("string.pdf", whenFinished = {
            println("Wrapper completed!")
        }), ViewTransition.Slide(0.5.seconds))
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
 * 6 = jpg (zip)
 *
 * 7 = png (zip)
 *
 * 8 = gif (zip)
 *
 * 9 = txt
 *
 * 10 = other text options
 * */
var typeOfFile by Delegates.notNull<Int>()

fun fileExt(): String {
    return when (typeOfFile) {
        0 -> "pdf"
        1 -> "docx"
        2 -> "pptx"
        3 -> "jpg:f"
        4 -> "png:f"
        5 -> "gif:f"
        6 -> "jpg:z"
        7 -> "png:z"
        8 -> "gif:z"
        9 -> "txt"
        else -> throw IllegalStateException("no?")
    }
}

open class FindFileView(var string: String, var whenFinished: FindFileView.() -> Unit = {}, val implementExtensionCheck: Boolean = true) :
    View("Bingo > Locate File") {
    protected lateinit var filePath: TextField
    private lateinit var fileName: TextField

    var result: String = ""
        private set

    lateinit var label: Label

    override val root: Parent = borderpane {
        val file = File(FindFileView::class.java.protectionDomain.codeSource.location.toURI()).parentFile.parentFile.parentFile
        currentFFView = this@FindFileView
        FileView.called = this@FindFileView
        FileView.showFileTextField = true
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
                        replaceWith(curView, ViewTransition.Slide(0.5.seconds, ViewTransition.Direction.RIGHT))
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
                        if (implementExtensionCheck) {
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
                        } else {
                            if (fileName.text == null || filePath.text == null) {
                                if (fileName.text == null) {
                                    label.text = "Must enter a file name!"
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

class FolderFindFileView(whenFinished: FindFileView.() -> Unit = {}) : FindFileView("yes", whenFinished) {
    override val root: Parent = borderpane {
        val file = File(FindFileView::class.java.protectionDomain.codeSource.location.toURI()).parentFile.parentFile.parentFile
        currentFFView = this@FolderFindFileView
        FileView.called = this@FolderFindFileView
        FileView.showFileTextField = false
        center {
            vbox {
                label("Input the location of the file.") {
                    addClass(Styles.titleLabel)
                }
                vbox {
                    filePath = textfield(file.path) {
                        alignment = Pos.CENTER
                    }
                    label("Location of files") {
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

class ExportTextView(
    private val text: String = "Export text to Clipboard",
    val whenFinished: ExportTextView.() -> Unit,
    private val additionalText: String = ""
) : View("Bingo > Export text") {

    lateinit var toggleGroup: ToggleGroup
    lateinit var spinner: Spinner<Int>

    override val root: Parent = borderpane {
        center {
            vbox {
                label(text) {
                    addClass(Styles.titleLabel)
                }
                vbox {
                    toggleGroup = togglegroup {
                        radiobutton("Export all cards") {
                            isSelected = true
                            runInsideLoop(5) {
                                if(this@ExportTextView::spinner.isInitialized) {
                                    spinner.isDisable = isSelected
                                }
                            }
                        }
                        radiobutton("Export a single card")
                        alignment = Pos.CENTER_LEFT
                    }
                    paddingLeft = 300.0
                }
                spinner = spinner(1, currentGame.size) {
                    isEditable = true
                    editor.textProperty().addListener { _, oldValue, newValue ->
                        if (newValue.length > 7) {
                            editor.text = oldValue
                        } else if (!newValue.matches("\\d*".toRegex())) {
                            editor.text = newValue.replace("[^\\d]".toRegex(), "")
                        }
                    }
                }
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
                        this@ExportTextView.replaceWith(curView, ViewTransition.Slide(0.5.seconds, ViewTransition.Direction.RIGHT))
                    }
                }
                button("Next >") {
                    addHoverEffect()
                    action {
                        whenFinished()
                    }
                }
                paddingBottom = 30.0
                alignment = Pos.CENTER
                spacing = 20.0
                addClass(Styles.defaultBackground)
            }
        }
        top {
            hbox {
                label(additionalText) {
                    addClass(Styles.regularLabel)
                }
                paddingTop = 30.0
                alignment = Pos.CENTER
            }
        }
        addClass(Styles.defaultBackground)
    }
}

open class ExportCompleted(
    eTitle: String = "Exporting",
    val message: String = "Exporting has been completed!",
    val whenDone: ExportCompleted.() -> Unit = {}
) :
    View("Bingo > $eTitle") {
    override val root = borderpane {
        center {
            hbox {
                label(message) {
                    addClass(Styles.titleLabel)
                    style(append = true) {
                        textFill = c(Appearance.GREEN.darkTextFill)
                    }
                }
                alignment = Pos.CENTER
                paddingLeft = 30.0
                paddingRight = 30.0
            }
        }
        bottom {
            hbox {
                button("Next >") {
                    addHoverEffectAppearance(Appearance.GREEN)
                    action {
                        close()
                        whenDone()
                    }
                    style {
                        textFill = c(Appearance.GREEN.lightTextFill)
                        padding = box(1.px, 12.px)
                        borderWidth += box(1.px, 3.px)
                        borderRadius += box(0.px)
                        backgroundRadius += box(0.px)
                        borderColor += box(Color.TRANSPARENT)
                        fontFamily = "dubai"
                        fontSize = Styles.sizeOfText
                        backgroundColor += c(Appearance.GREEN.themeColor)
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

private var curTitle = ""
private var curMes = ""
private var curWhenDone: ExportCompleted.() -> Unit = {}
private lateinit var curFile: File

class ExportLocationCompleted(
    eTitle: String = "Exporting",
    message: String = "Exporting has been completed!",
    whenDone: ExportCompleted.() -> Unit = {},
    val file: File
) : ExportCompleted(eTitle, message, whenDone) {

    init {
        curTitle = eTitle
        curMes = message
        curWhenDone = whenDone
        curFile = file
    }

    constructor() : this(curTitle, curMes, curWhenDone, curFile)

    override val root = borderpane {
        center {
            hbox {
                label(message) {
                    addClass(Styles.titleLabel)
                    style(append = true) {
                        textFill = c(Appearance.GREEN.darkTextFill)
                    }
                }
                alignment = Pos.CENTER
                paddingLeft = 30.0
                paddingRight = 30.0
            }
        }
        bottom {
            hbox {
                button("Open file") {
                    addHoverEffectAppearance(Appearance.GREEN)
                    action {
                        Desktop.getDesktop().open(file)
                    }
                    style {
                        textFill = c(Appearance.GREEN.lightTextFill)
                        padding = box(1.px, 12.px)
                        borderWidth += box(1.px, 3.px)
                        borderRadius += box(0.px)
                        backgroundRadius += box(0.px)
                        borderColor += box(Color.TRANSPARENT)
                        fontFamily = "dubai"
                        fontSize = Styles.sizeOfText
                        backgroundColor += c(Appearance.GREEN.themeColor)
                    }
                }
                button("Open location") {
                    addHoverEffectAppearance(Appearance.GREEN)
                    action {
                        Runtime.getRuntime().exec("explorer.exe /select,${file.absolutePath}")
                    }
                    style {
                        textFill = c(Appearance.GREEN.lightTextFill)
                        padding = box(1.px, 12.px)
                        borderWidth += box(1.px, 3.px)
                        borderRadius += box(0.px)
                        backgroundRadius += box(0.px)
                        borderColor += box(Color.TRANSPARENT)
                        fontFamily = "dubai"
                        fontSize = Styles.sizeOfText
                        backgroundColor += c(Appearance.GREEN.themeColor)
                    }
                }
                button("Next >") {
                    addHoverEffectAppearance(Appearance.GREEN)
                    action {
                        close()
                        whenDone()
                    }
                    style {
                        textFill = c(Appearance.GREEN.lightTextFill)
                        padding = box(1.px, 12.px)
                        borderWidth += box(1.px, 3.px)
                        borderRadius += box(0.px)
                        backgroundRadius += box(0.px)
                        borderColor += box(Color.TRANSPARENT)
                        fontFamily = "dubai"
                        fontSize = Styles.sizeOfText
                        backgroundColor += c(Appearance.GREEN.themeColor)
                    }
                }
                spacing = 30.0
                paddingBottom = 30.0
                alignment = Pos.CENTER
            }
        }
        style {
            backgroundColor += c(Appearance.GREEN.themeBackgroundColor)
        }
    }
}

