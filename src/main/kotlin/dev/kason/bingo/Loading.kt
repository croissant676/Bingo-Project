package dev.kason.bingo

import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.effect.Reflection
import javafx.scene.image.Image
import tornadofx.*

var isCurrentlyLoading = true
    private set

private lateinit var currentLoadingView: LoadingView

fun setViewFromLoading(view: View, transition: ViewTransition? = ViewTransition.Fade(0.5.seconds)) {
    if (isCurrentlyLoading) {
        runLater {
            currentLoadingView.replaceWith(view, transition, true, centerOnScreen = true)
        }
    }
}

var immediateView: View? = null

fun runImmediately(view: View) {
    immediateView = view
}

private var isFirst = true

@Suppress("MemberVisibilityCanBePrivate")
class LoadingView : View("Bingo Project > Loading") {

    override val root = vbox {
        label("Loading...") {
            addClass(Styles.titleLabel)
        }
        progressindicator {
            effect = Reflection(15.0, 0.75, 0.5, 0.0)
            addClass(Styles.progressIndicator)
        }
        style {
            alignment = Pos.CENTER
            spacing = 10.px
        }
        addClass(Styles.defaultBackground)
        minHeight = 300.0
        minWidth = 500.0
    }

    override fun onDock() {
        if(isFirst){
            try {
                val uri = javaClass.classLoader.getResource("bingo.png")!!
                val image = Image(uri.toExternalForm())
                addStageIcon(image)
            } catch (e: Exception) {
                alert(
                    Alert.AlertType.WARNING, "Resource does not exist.", "Cannot gather resource: \"bingo.png\" inside of resources folder:\n" +
                            "Windows will not have an image."
                )
            }
        }
        isCurrentlyLoading = true
        currentLoadingView = this
        if (immediateView != null && isFirst) {
            setViewFromLoading(immediateView!!)
        }
        if(isFirst) {
            setViewFromLoading(BingoMenu)
            isFirst = false
        }
    }

    override fun onUndock() {
        isCurrentlyLoading = false
    }
}