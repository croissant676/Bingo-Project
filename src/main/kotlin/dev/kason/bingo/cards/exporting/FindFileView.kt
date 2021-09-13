package dev.kason.bingo.cards.exporting

import dev.kason.bingo.ui.FileView
import dev.kason.bingo.ui.Styles
import dev.kason.bingo.util.addHoverEffect
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.TextField
import tornadofx.*
import java.io.File

object WrapperFileView : View("Bingo > Wrapper Transition File") {
    override val root = vbox {
        alignment = Pos.CENTER
        addClass(Styles.defaultBackground)
    }

    init {
        replaceWith(FindFileView("string.pdf") {}, ViewTransition.Slide(0.5.seconds))
    }
}

class FindFileView(val string: String, val whenFinished: FileView.() -> Unit) : View("Bingo > Locate File") {
    private lateinit var thing: TextField
    override val root: Parent = borderpane {
        FileView.called = this@FindFileView
        center {
            label("Input the location of the file.") {
                addClass(Styles.titleLabel)
            }
            thing = textfield("C:\\Users\\crois\\Appdata") {

            }
            button("Submit") {
                addHoverEffect()
            }
        }
        val file = File(FindFileView::class.java.protectionDomain.codeSource.location.toURI()).parentFile
        FileView.startingItem.value = file.path
        FileView.currentFile = file
        FileView.indexFiles()
        bottom {
            button("Select file from system.") {
                addHoverEffect()
                action {
                    replaceWith(FileView, ViewTransition.Slide(0.5.seconds))
                }
            }
        }
        addClass(Styles.defaultBackground)
    }

}
