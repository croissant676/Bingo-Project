import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage
import org.docx4j.wml.Drawing
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import org.docx4j.wml.R
import tornadofx.c
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.util.zip.ZipOutputStream
import javax.imageio.ImageIO
import javafx.scene.paint.Color as JFXColor
import java.awt.Color as SwingColor
import javafx.scene.control.TextField


fun main() {
    val textfield: TextField
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