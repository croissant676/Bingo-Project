@file:Suppress("DuplicatedCode")

package dev.kason.bingo.ui

import dev.kason.bingo.cards.exporting.FindFileView
import dev.kason.bingo.cards.exporting.currentFFView
import dev.kason.bingo.control.Appearance
import dev.kason.bingo.control.BingoState
import dev.kason.bingo.control.currentState
import dev.kason.bingo.util.addHoverEffect
import dev.kason.bingo.util.addHoverEffectLight
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableView
import javafx.scene.control.TreeView
import javafx.scene.control.skin.TextFieldSkin
import javafx.scene.paint.Color
import tornadofx.*
import java.io.File


object BingoMenu : View("Bingo > Menu") {
    override val root = borderpane {
        center {
            vbox {
                label("Bingo!") {
                    addClass(Styles.titleLabel)
                    padding = insets(15)
                }
                alignment = Pos.CENTER
                button("Create Bingo Game") {
                    addClass(Styles.button)
                    addHoverEffect()
                    action {
                        currentState = BingoState.CREATION_MENU
                        replaceWith(CreationMenuView, ViewTransition.Fade(0.5.seconds))
                    }
                    useMaxSize = true
                }
//                button("Settings") {
//                    addClass(Styles.button)
//                    addHoverEffect()
//                    useMaxSize = true
//                    action {
//                        currentState = BingoState.SETTINGS
//                        replaceWith(SettingsView, ViewTransition.Fade(0.5.seconds))
//                    }
//                }
                button("How to use") {
                    addClass(Styles.button)
                    addHoverEffect()
                    action {
                        currentState = BingoState.HOW_TO_USE
                        replaceWith(HowToUseView, ViewTransition.Fade(0.5.seconds))
                    }
                    useMaxSize = true
                }
                spacing = 10.0
                maxWidth = 250.0
                alignment = Pos.CENTER
                addClass(Styles.defaultBackground)
            }
            addClass(Styles.defaultBackground)
        }
        bottom {
            hbox {
                hbox {
                    alignment = Pos.BOTTOM_LEFT
                    button("Appearances") {
                        style {
//                        Styles.button reference
                            textFill = Styles.lightTextColor // Make it invisible
                            backgroundColor += Styles.themeBackgroundColor
                            padding = box(1.px, 12.px)
                            borderWidth += box(1.px, 3.px)
                            borderRadius += box(0.px)
                            backgroundRadius += box(0.px)
                            borderColor += box(Color.TRANSPARENT)
                            fontFamily = "dubai"
                            fontSize = Styles.sizeOfText
                        }
                        addHoverEffectLight() // Just for this case
                        action {
                            replaceWith(MinorSettings, ViewTransition.Fade(0.5.seconds))
                        }
                    }
                    addClass(Styles.defaultBackground)
                }
            }
            addClass(Styles.defaultBackground)
        }
        top {
            hbox { // Dino Game!!!!!!!!!
                button("Play dino game!") {
                    style {
                        textFill = Styles.lightTextColor
                        backgroundColor += Styles.themeBackgroundColor
                        padding = box(1.px, 12.px)
                        borderWidth += box(1.px, 3.px)
                        borderRadius += box(0.px)
                        backgroundRadius += box(0.px)
                        borderColor += box(Color.TRANSPARENT)
                        fontFamily = "dubai"
                        fontSize = Styles.sizeOfText
                    }
                    addHoverEffectLight()
                }
                addClass(Styles.defaultBackground)
            }
        }
        addClass(Styles.defaultBackground)
        minHeight = 700.0
        minWidth = 1000.0
    }
}

@Suppress("All")
object SettingsView : View("Bingo > Settings") {
    override val root = borderpane {

        center {
            hbox {
                tabpane {
                    tab("General") {
                        isClosable = false
                        addClass(Styles.tab)
                    }
                    tab("Formatting") {
                        isClosable = false
                        addClass(Styles.tab)
                    }
                    tab("Performance") {
                        content = vbox {
                            hbox {
                                val heap = label("${Runtime.getRuntime().totalMemory() / 1_000_000} MB") {
                                    addClass(Styles.regularLabel)
                                }
                                val free = label("${Runtime.getRuntime().freeMemory() / 1_000_000} MB") {
                                    addClass(Styles.regularLabel)
                                }
                                val max = label("${Runtime.getRuntime().maxMemory() / 1_000_000} MB") {
                                    addClass(Styles.regularLabel)
                                }
                                button("Refresh") {
                                    addHoverEffect()
                                    action {
                                        val mbFree = Runtime.getRuntime().freeMemory() / 1_000_000
                                        heap.text = "${Runtime.getRuntime().totalMemory() / 1_000_000} MB"
                                        free.text = "${if (mbFree < 10) "0$mbFree" else mbFree} MB"
                                        max.text = "${Runtime.getRuntime().maxMemory() / 1_000_000} MB"
                                    }
                                    style {
                                        padding = box(0.px, 0.px, 0.px, 100.px)
                                    }
                                }
                                spacing = 150.0
                            }
                            hbox {
                                label("Total Memory (in MB)") {
                                    addClass(Styles.regularLabel)
                                }
                                label("Free Memory (in MB)") {
                                    addClass(Styles.regularLabel)
                                }
                                label("Max Memory (in MB)") {
                                    addClass(Styles.regularLabel)
                                }
                                spacing = 35.0
                            }
                            spacing = 5.0

                        }
                        isClosable = false
                        addClass(Styles.tab)
                    }
                    tab("Appearance") {
                        isClosable = false
                        addClass(Styles.tab)
                        val appearances = Appearance.values()
                        content = hbox {
                            for (appearance in appearances) {
                                this += appearance.display()
                            }
                            spacing = 10.0
                            style {
                                padding = box(10.px)
                            }
                            addClass(Styles.defaultBackground)
                        }
                    }
                    addClass(Styles.tabPane)
                    useMaxWidth = true
                }
            }
        }
        right {
            button("Exit") {
                addHoverEffect(c("ff4e6c"), c("eb0028"))
                addClass(Styles.redButton)
                action {
                    currentState = BingoState.MENU
                    replaceWith(BingoMenu, ViewTransition.Fade(0.5.seconds))
                    BingoMenu.onDock()
                }
                style(append = true) {
                    paddingTop = 1
                }
            }
            addClass(Styles.defaultBackground)
        }

    }
}

object MinorSettings : View("Bingo > Settings") {
    override val root: Parent = vbox {
        val appearances = Appearance.values()
        hbox {
            for (appearance in appearances) {
                this += appearance.display()
            }
            spacing = 10.0
            style {
                padding = box(10.px)
            }
            addClass(Styles.defaultBackground) // Nah mate
        }
        // Store the button
        hbox {
            button("Exit") {
                addHoverEffect(c("ff4e6c"), c("eb0028"))
                addClass(Styles.redButton)
                action {
                    currentState = BingoState.MENU
                    replaceWith(BingoMenu, ViewTransition.Fade(0.5.seconds))
                    BingoMenu.onDock()
                }
                style(append = true) {
                    paddingTop = 1
                }
            }
            alignment = Pos.CENTER
            addClass(Styles.defaultBackground)
        }
        addClass(Styles.defaultBackground)
        useMaxSize = true
    }
}

object CreationMenuView : View("Bingo > Create Bingo Game") {

    private var dayProperty = SimpleIntegerProperty(0)

    override val root = vbox {
        label {
            addClass("")
        }

        hbox {

            label("Input the number of days to play the game:") {
                addClass(Styles.regularLabel)
            }
            spinner<Number>(0, 100, 5, 1, false, dayProperty, true) {
                addClass(Styles.defaultSpinner)
                onKeyTyped = EventHandler {
                    this.promptTextProperty().set("Hello")
                }
            }
            label("Or choose the day you want it to end.") {
                addClass(Styles.regularLabel)
            }
            datepicker {
                addClass(Styles.defaultPicker)
                editor.skin = TextFieldSkin(editor).apply {
                    scaleX = 0.7
                    scaleY = 0.7
                }
            }
        }
        hbox {
            button("< Back") {
                addClass(Styles.button)
                addHoverEffect()
                action {
                    assert(currentState == BingoState.CREATION_MENU) {
                        "State not linked"
                    }
                    currentState = BingoState.MENU
                    replaceWith(BingoMenu, ViewTransition.Fade(0.5.seconds))
                }
            }
            button("Next > ") {
                addClass(Styles.button)
                addHoverEffect()
                action {
                    assert(currentState == BingoState.CREATION_MENU) {
                        "State not linked"
                    }
                    replaceWith(CreationMenu2, ViewTransition.Fade(0.5.seconds))
                }
            }
            alignment = Pos.CENTER
            spacing = 10.0
        }
        addClass(Styles.defaultBackground)
    }
}

object CreationMenu2 : View("Bingo > Create Bingo Game") {

    override val root = hbox {
        button("< Back") {
            addHoverEffect()
            action {
                assert(currentState == BingoState.CREATION_MENU) {
                    "State not linked"
                }
                replaceWith(CreationMenuView, ViewTransition.Fade(0.5.seconds))
            }
        }
        button("Next > ") {
            addHoverEffect()
            action {
                assert(currentState == BingoState.CREATION_MENU) {
                    "State not linked"
                }
                replaceWith(FileView, ViewTransition.Fade(0.5.seconds))
            }
        }
        alignment = Pos.CENTER
        addClass(Styles.defaultBackground)
        spacing = 20.0
    }
}

object FileView : View("Bingo > Find File") {

    var startingLocation: String
        get() = startingItem.value
        set(value) {
            currentFile = File(value).absoluteFile
            startingItem.value = value
        }

    lateinit var called: FindFileView
    private var currentFile = File("")
    private val startingItem = TreeItem(currentFile.path)
    private val stringProperty = SimpleStringProperty("")
    val string: String get() = stringProperty.get()

    @Suppress("SpellCheckingInspection")
    private lateinit var tview: TreeView<String>

    override val root = borderpane {
        center {
            vbox {
                label("Input location of the file.") {

                    addClass(Styles.titleLabel)
                }
                textfield(currentFFView.string) {
                    isEditable = false
                    stringProperty.value = "Hello"
                }
                tview = treeview(startingItem) {
                    addClass(Styles.defaultTreeView)

                }
                addClass(Styles.defaultBackground)
                alignment = Pos.CENTER
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
        tview.selectFirst()
        bottom {
            hbox {
                button("< Back") {
                    addHoverEffect()
                    action {
                        replaceWith(called, ViewTransition.Fade(0.5.seconds))
                    }
                }
                vbox {
//                    val label = label("You must select a file before continuing") {
//                        addClass(Styles.regularLabel)
//                        style {
//                            textFill = c("ff4e6c")
//                        }
//                        isVisible = false
//                    }
                    button("Proceed to next step >") {
                        addHoverEffect()
                        action {
                            val result = tview.selectedValue
                            if (result == null) {
                                // This section will never run
//                                label.isVisible = true
                            } else {
                                called.whenFinished(called)
                            }
                        }
                    }
                }
                spacing = 10.0
                addClass(Styles.defaultBackground)
                alignment = Pos.CENTER
                paddingBottom = 30.0
            }
        }
    }

    private var numberOfFiles = 0

    private fun recursivelyUpdateItem(item: TreeItem<String>): Boolean {
        numberOfFiles++
        if (currentFile.isHidden) return false
        if (currentFile.name.startsWith('.')) return false
        val children = currentFile.listFiles()
        if (children != null) {
            for (child in children) {
                currentFile = child
                val newItem = TreeItem(child.name)
                if (recursivelyUpdateItem(newItem)) {
                    item += newItem
                }
            }
            return true
        }
        return false
    }

    fun indexFiles() {
//        replaceWith(LoadingView(), ViewTransition.Fade(0.5.seconds))
        recursivelyUpdateItem(startingItem)
        setViewFromLoading(this)
    }
}

object HowToUseView : View("Bingo > How To Use") {
    override val root = vbox {
        label("How to use") {
            addClass(Styles.titleLabel)
        }
        label("First do stuff:") {
            addClass(Styles.regularLabel)
        }
        label("Next do more stuff") {
            addClass(Styles.regularLabel)
        }
        button("< Back") {
            addHoverEffect()
            action {
                currentState = BingoState.MENU
                replaceWith(BingoMenu, ViewTransition.Fade(0.5.seconds))
            }
        }
        alignment = Pos.CENTER
        addClass(Styles.defaultBackground)
    }
}

object AdView: View("Bingo > Buy ") {
    override val root = vbox {
        button {

        }
    }
}