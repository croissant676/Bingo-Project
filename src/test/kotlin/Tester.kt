import dev.kason.bingo.cards.generateImage
import dev.kason.bingo.cards.generateNumbersWith
import javafx.scene.effect.Reflection
import tornadofx.*
import java.io.File
import javax.imageio.ImageIO
import javafx.scene.paint.Color as JFXColor
import java.awt.Color as SwingColor

fun main() {
    launch<DanApp>()
//    outputNumbers(generateNumbersWith())
//    outputNumbers(generateNumbersWith())
//    outputNumbers(generateNumbersWith())
//    outputNumbers(generateNumbersWith())

//    val pdfDocument = PdfDocument()
//    val document = Document()
//    document.addTitle("hello")
//    pdfDocument.open()
//    val writer = PdfWriter.getInstance(document, FileOutputStream("C:\\Users\\crois\\IdeaProjects\\BingoProject\\src\\main\\resources\\Test.pdf"))
//
//    generateImage()
}

class DanApp: App(DanView::class)

class DanView: View("Dan dans tfx tutorial") {
    override val root = vbox {
        progressindicator {
            effect = Reflection()
        }
    }
}

private fun generate() {
    val image = generateImage(generateNumbersWith())
    val file = File("C:\\Users\\crois\\IdeaProjects\\BingoProject\\src\\main\\resources\\Test.png")
    ImageIO.write(image, file.extension, file)
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