import dev.kason.bingo.cards.generateNumbersWith
import dev.kason.bingo.cards.outputNumbers
import dev.kason.bingo.control.currentAppearance
import tornadofx.c
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javafx.scene.paint.Color as JFXColor
import java.awt.Color as SwingColor

fun main() {
    outputNumbers(generateNumbersWith())
    outputNumbers(generateNumbersWith())
    outputNumbers(generateNumbersWith())
    outputNumbers(generateNumbersWith())
    /*
    val pdfDocument = PdfDocument()
    val document = Document()
    document.addTitle("hello")
    pdfDocument.open()
    val writer = PdfWriter.getInstance(document, FileOutputStream("C:\\Users\\crois\\IdeaProjects\\BingoProject\\src\\main\\resources\\Test.pdf"))

    generateImage()

     */
}

private fun generateImage() {
    val image = BufferedImage(1000, 1000, 4)
    val graphics = image.createGraphics()
    graphics.color = generate(currentAppearance.themeColor)
    graphics.fillRect(0, 0, 1000, 1000)
    graphics.dispose()
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