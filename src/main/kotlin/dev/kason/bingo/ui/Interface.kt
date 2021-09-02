@file:Suppress("DuplicatedCode")

package dev.kason.bingo.ui

import dev.kason.bingo.control.Appearance
import dev.kason.bingo.control.BingoState
import dev.kason.bingo.control.currentState
import dev.kason.bingo.util.addHoverEffect
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.event.EventType
import javafx.geometry.Pos
import javafx.scene.control.TreeItem
import javafx.scene.control.skin.TextFieldSkin
import javafx.scene.input.MouseEvent
import tornadofx.*
import java.io.File

object BingoMenu : View("Bingo > Bingo") {
    override val root = borderpane {
        center {
            vbox {
                label("Top Text") {
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
                button("Settings") {
                    addClass(Styles.button)
                    addHoverEffect()
                    useMaxSize = true
                    action {
                        currentState = BingoState.SETTINGS
                        replaceWith(SettingsView, ViewTransition.Fade(0.5.seconds))
                    }
                }
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
            }
        }
        addClass(Styles.defaultBackground)
        minHeight = 700.0
        minWidth = 1000.0
    }
}

object SettingsView : View("Bingo > Settings") {
    override val root = hbox {
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

object CreationMenuView : View("Bingo > Create Bingo Game") {

    private var dayProperty = SimpleIntegerProperty(0)

    override val root = form {
        fieldset("Game mechanics") {
            hbox {
                field("Input the number of days to play the game:") {
                    spinner<Number>(0, 100, 5, 1, false, dayProperty, true) {
                        addClass(Styles.defaultSpinner)
                        onKeyTyped = EventHandler {
                            this.promptTextProperty().set("Hello")
                        }
                    }
                    with(label) {
                        addClass(Styles.regularLabel)
                    }
                }
                field("Or choose the day you want it to end.") {
                    datepicker {
                        addClass(Styles.defaultPicker)
                        editor.skin = TextFieldSkin(editor).apply {
                            scaleX = 0.7
                            scaleY = 0.7
                        }
                    }
                    with(label) {
                        addClass(Styles.regularLabel)
                    }
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
            spacing = 10.0
        }
        addClass(Styles.defaultBackground)
    }
}

object CreationMenu2 : View("Bingo > Create Bingo Game") {

    var stringFileLocation = ""

    override val root = vbox {
        button("Test") {
            action {
                FileView.indexFiles()
                replaceWith(FileView, ViewTransition.Fade(0.5.seconds))
            }
        }
    }
}

object FileView : View("Bingo > Find File") {

    private val startingItem = TreeItem("C:\\Users")
    private var currentFile = File(startingItem.value)
    private val stringProperty = SimpleStringProperty("")
    val string: String get() = stringProperty.get()

    override val root = vbox {
        textfield(stringProperty) {
            isEditable = false
        }
        treeview(startingItem) {

        }
    }

    private fun recursivelyUpdateItem(item: TreeItem<String>): Boolean {
        println(currentFile)
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
                newItem.addEventHandler (MouseEvent.MOUSE_PRESSED) {
                    print("clock")
                }
            }
            return true
        }
        return false
    }

    fun indexFiles() {
        recursivelyUpdateItem(startingItem)
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
                replaceWith(BingoMenu)
            }
        }
        alignment = Pos.CENTER
        addClass(Styles.defaultBackground)
    }
}