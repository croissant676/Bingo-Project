import com.sun.security.auth.module.NTSystem
import tornadofx.*
import javafx.scene.paint.Color as JFXColor
import java.awt.Color as SwingColor

fun main() {
    val ntSystem = NTSystem()
    println(ntSystem.name)
}

private fun generate() {
}

private fun switchColors(color: SwingColor): JFXColor {
    return JFXColor(color.red.toDouble(), color.green.toDouble(), color.blue.toDouble(), 1.0)
}

private fun switchColors(color: JFXColor): SwingColor {
    return SwingColor(color.red.toFloat(), color.green.toFloat(), color.blue.toFloat())
}

private fun generate(string: String): SwingColor {
    return switchColors(c(string))
}