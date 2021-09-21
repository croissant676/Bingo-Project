package dev.kason.bingo

import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfWriter
import javafx.scene.control.RadioButton
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage
import org.docx4j.wml.Drawing
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import org.docx4j.wml.R
import tornadofx.ViewTransition
import tornadofx.runLater
import tornadofx.seconds
import java.awt.Toolkit
import java.awt.datatransfer.*
import java.awt.image.BufferedImage
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.imageio.ImageIO
import kotlin.concurrent.thread


internal fun generateImages(): List<BufferedImage> {
    return listOf()
}

lateinit var resultFile: File
lateinit var bulkImgType: String

var isFolder = true

fun pdfUI() {
    val view = FindFileView("game.pdf")
    curView = EditingCardView
    typeOfFile = 0
    EditingCardView.replaceWith(view, ViewTransition.Fade(0.5.seconds))
    view.whenFinished = {
        checkFileAndRun {
            curView = this
            replaceWith(FormattingView, ViewTransition.Fade(0.5.seconds))
            FormattingView.whenFinished = {
                val loadingView = LoadingView()
                askForExtra(FormattingView, loadingView, resultFile.parentFile) {
                    thread {
                        val doc = Document(Rectangle(610.0F, 787.0F))
                        PdfWriter.getInstance(doc, FileOutputStream(resultFile))
                        doc.open()
                        for (index in currentGame.indices step number) {
                            var image: BufferedImage? = null
                            runLater {
                                image = generateImage(currentGame, number, index)
                            }
                            while (image == null) {
                                Thread.sleep(10)
                            }
                            val stream = ByteArrayOutputStream()
                            ImageIO.write(image, "png", stream)
                            val pdfImage = Image.getInstance(stream.toByteArray())!!
                            pdfImage.scaleToFit(546.0F, 722.0F)
                            doc.add(pdfImage)
                        }
                        doc.close()
                        runLater {
                            ExportLocationCompleted(eTitle = "Exporting to PDF", whenDone = {
                                loadingView.replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                            }, message = "Finished Exporting to PDF", file = resultFile).apply {
                                val exportCompleted = this
                                openModal(escapeClosesWindow = false)!!.apply {
                                    setOnCloseRequest {
                                        exportCompleted.whenDone()
                                        close()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun exportTXTText() {
    EditingCardView.replaceWith(FindFileView(string = "game.txt", whenFinished = {
        checkFileAndRun {
            curView = this
            replaceWith(ExportTextView("Export text to file", {
                askForExtra(FormattingView, null, resultFile.parentFile) {
                    thread(start = true, name = "Text Runner", priority = 9) {
                        val text = if ((toggleGroup.selectedToggle as RadioButton).text == "Export all cards") generateString(currentGame)
                        else generateString(currentGame[spinner.value - 1])
                        val fileWriter = resultFile.bufferedWriter()
                        fileWriter.write(text)
                        fileWriter.close()
                        runLater {
                            ExportLocationCompleted("Exporting to file", whenDone = {
                                this@ExportTextView.replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                            }, message = "Finished exporting to file!", file = resultFile).apply {
                                val exportCompleted = this
                                openModal(escapeClosesWindow = false)!!.apply {
                                    setOnCloseRequest {
                                        exportCompleted.whenDone()
                                        close()
                                    }
                                }
                            }
                        }
                    }
                }
            }, "File Location: $resultFile"), ViewTransition.Fade(0.5.seconds))
        }
    }).also {
        curView = EditingCardView
    }, ViewTransition.Fade(0.5.seconds))
}

fun exportOtherText() {
    EditingCardView.replaceWith(FindFileView(string = "game.any", whenFinished = {
        checkFileAndRun {
            curView = this
            replaceWith(ExportTextView("Export text to file", {
                askForExtra(FormattingView, null, resultFile.parentFile) {
                    thread(start = true, name = "Text Runner", priority = 9) {
                        val text =
                            if ((toggleGroup.selectedToggle as RadioButton).text == "Export all cards") generateString(
                                currentGame
                            )
                            else generateString(currentGame[spinner.value - 1])
                        val fileWriter = resultFile.bufferedWriter()
                        fileWriter.write(text)
                        fileWriter.close()
                        runLater {
                            ExportLocationCompleted("Exporting to file", whenDone = {
                                this@ExportTextView.replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                            }, message = "Finished exporting to file!", file = resultFile).apply {
                                val exportCompleted = this
                                openModal(escapeClosesWindow = false)!!.apply {
                                    setOnCloseRequest {
                                        exportCompleted.whenDone()
                                        close()
                                    }
                                }
                            }
                        }
                    }
                }
            }, "File Location: $resultFile"), ViewTransition.Fade(0.5.seconds))
        }
    }, implementExtensionCheck = false).also {
        curView = EditingCardView
    }, ViewTransition.Fade(0.5.seconds))
}

fun exportCBText() {
    EditingCardView.replaceWith(ExportTextView(whenFinished = {
        if ((toggleGroup.selectedToggle as? RadioButton)?.text!!.contains("all")) {
            val selection = StringSelection(generateString(currentGame))
            val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
            clipboard.setContents(selection, selection)
        } else {
            val selection = StringSelection(generateString(currentGame[spinner.value - 1]))
            val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
            clipboard.setContents(selection, selection)
        }
        ExportCompleted("Exporting to clipboard", whenDone = {
            this@ExportTextView.replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
        }, message = "Finished exporting to clipboard!").apply {
            val exportCompleted = this
            openModal(escapeClosesWindow = false)!!.apply {
                setOnCloseRequest {
                    exportCompleted.whenDone()
                    close()
                }
            }
        }
    }), ViewTransition.Fade(0.5.seconds))
}

fun FindFileView.checkFileAndRun(action: FindFileView.() -> Unit) {
    val file = File(result)
    if (file.exists()) {
        label.text = "$result already exists!"
        label.isVisible = true
    } else {
        resultFile = file
        action(this)
    }
}

// Size of the image on disk
internal const val pathHint = 188416

fun wordUI() {
    val view = FindFileView("game.docx")
    typeOfFile = 1
    EditingCardView.replaceWith(view, ViewTransition.Fade(0.5.seconds))
    view.whenFinished = {
        checkFileAndRun {
            curView = this
            replaceWith(FormattingView, ViewTransition.Fade(0.5.seconds))
            FormattingView.whenFinished = {
                val loadingView = LoadingView()
                askForExtra(FormattingView, loadingView, resultFile.parentFile) {
                    thread {
                        val wordPackage = WordprocessingMLPackage.createPackage()
                        val mainDocumentPart = wordPackage.mainDocumentPart
                        for (index in currentGame.indices step number) {
                            println("$index:$number")
                            val stream = ByteArrayOutputStream()
                            var image: BufferedImage? = null
                            runLater {
                                image = generateImage(currentGame, number, index)
                            }
                            while (image == null) {
                                Thread.sleep(10)
                            }
                            ImageIO.write(image!!, "png", stream)
                            val fileContent = stream.toByteArray()
                            val imagePart = BinaryPartAbstractImage
                                .createImagePart(wordPackage, fileContent)
                            val inline = imagePart.createImageInline("Bingo Project", "Alternate Text :)", 1, 2, false)
                            val factory = ObjectFactory()
                            val p: P = factory.createP()
                            val r: R = factory.createR()
                            val drawing: Drawing = factory.createDrawing()
                            p.content.add(r)
                            r.content.add(drawing)
                            drawing.anchorOrInline.add(inline)
                            mainDocumentPart.content.add(p)
                        }
                        wordPackage.save(resultFile)
                        runLater {
                            ExportLocationCompleted(message = "Finished Exporting DOCX File", whenDone = {
                                loadingView.replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                            }, file = resultFile).apply {
                                val exportCompleted = this
                                openModal(escapeClosesWindow = false)!!.apply {
                                    setOnCloseRequest {
                                        exportCompleted.whenDone()
                                        close()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun exportImageCB() {
    EditingCardView.replaceWith(ExportAsClipBoardView, ViewTransition.Fade(0.5.seconds))
    ExportAsClipBoardView.action = {
        curView = ExportAsClipBoardView
        replaceWith(FormattingView, ViewTransition.Fade(0.5.seconds))
        FormattingView.whenFinished = {
            val image = generateImage(format = number, startingIndex = value - 1)
            thread {
                val trans = TransferableImage(image)
                val c = Toolkit.getDefaultToolkit().systemClipboard
                c.setContents(trans) { _, _ -> }
                runLater {
                    ExportCompleted(eTitle = "Finished Exporting Image into Clipboard", whenDone = {
                        FormattingView.replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                    }, message = "Finished exporting image into clipboard!").apply {
                        val exportCompleted = this
                        openModal(escapeClosesWindow = false)!!.apply {
                            setOnCloseRequest {
                                exportCompleted.whenDone()
                                close()
                            }
                        }
                    }
                }
            }
        }
    }
}

private class TransferableImage(val i: BufferedImage) : Transferable {

    override fun getTransferData(flavor: DataFlavor): Any {
        if (flavor == DataFlavor.imageFlavor) {
            return i
        } else {
            throw UnsupportedFlavorException(flavor)
        }
    }

    override fun getTransferDataFlavors(): Array<DataFlavor?> {
        val flavors = arrayOfNulls<DataFlavor>(1)
        flavors[0] = DataFlavor.imageFlavor
        return flavors
    }

    override fun isDataFlavorSupported(flavor: DataFlavor): Boolean {
        val flavors = transferDataFlavors
        for (i in flavors.indices) {
            if (flavor.equals(flavors[i])) {
                return true
            }
        }
        return false
    }
}

fun generateImagesInFile() {
    val view = FolderFindFileView {
        resultFile = File(result)
        if (!resultFile.isDirectory) {
            label.text = "$resultFile must be a directory in order to be used."
            label.isVisible = true
        } else if (resultFile.listFiles()!!.isNotEmpty()) {
            label.text = "$resultFile must be empty in order to be used."
            label.isVisible = true
        } else {
            curView = this
            replaceWith(FormattingView, ViewTransition.Fade(0.5.seconds))
            FormattingView.whenFinished = {
                val resultFilePath = resultFile.path + '\\'
                val view = LoadingView()
                askForExtra(FormattingView, view, resultFile) {
                    thread {
                        for (index in currentGame.indices step number) {
                            var image: BufferedImage? = null
                            runLater {
                                image = generateImage(currentGame, number, index)
                            }
                            while (image == null) {
                                Thread.sleep(10)
                            }
                            image!!
                            val path = if (number == 1) {
                                "${resultFilePath}BingoCard_$index.$bulkImgType"
                            } else {
                                "${resultFilePath}BingoCard_${index + 1}-${(index + number).coerceAtMost(currentGame.size)}.$bulkImgType"
                            }
                            ImageIO.write(image, bulkImgType, File(path))
                        }
                        runLater {
                            ExportLocationCompletedWithoutRunner(
                                eTitle = "Finished Exporting Image", whenDone = {
                                    view.replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                                }, message = "Finished exporting image into folder!", file = resultFile
                            ).apply {
                                val exportCompleted = this
                                openModal(escapeClosesWindow = false)!!.apply {
                                    setOnCloseRequest {
                                        exportCompleted.whenDone()
                                        close()
                                    }
                                }
                            }
                        }
                    }
                }
                // C:\Users\crois\Downloads\OutputFolder
            }
        }
    }
    curView = EditingCardView
    EditingCardView.replaceWith(view, ViewTransition.Fade(0.5.seconds))
}

fun generateImagesInZip() {
    val view = FindFileView (string = "game.zip", implementExtensionCheck = true, whenFinished = {
        checkFileAndRun {
            curView = this
            replaceWith(FormattingView, ViewTransition.Fade(0.5.seconds))
            FormattingView.whenFinished = {
                val view = LoadingView()
                askForExtra(FormattingView, view, resultFile.parentFile) {
                    replaceWith(view, ViewTransition.Fade(0.5.seconds))
                    thread {
                        val bufferedOutputStream = BufferedOutputStream(FileOutputStream(resultFile))
                        val zipOutputStream = ZipOutputStream(bufferedOutputStream)
                        for (index in currentGame.indices step number) {
                            var image: BufferedImage? = null
                            runLater {
                                image = generateImage(currentGame, number, index)
                            }
                            while (image == null) {
                                Thread.sleep(10)
                            }
                            zipOutputStream.putNextEntry(
                                ZipEntry(
                                    if (number == 1) {
                                        "BingoCard_$index.$bulkImgType"
                                    } else {
                                        "BingoCard_${index + 1}-${(index + number).coerceAtMost(currentGame.size)}.$bulkImgType"
                                    }
                                )
                            )
                            ImageIO.write(image!!, "png", zipOutputStream)
                            zipOutputStream.closeEntry()
                        }
                        zipOutputStream.close()
                        runLater {
                            ExportLocationCompletedWithoutRunner(
                                eTitle = "Finished Exporting Images", whenDone = {
                                    view.replaceWith(EditingCardView, ViewTransition.Fade(0.5.seconds))
                                }, message = "Finished exporting image into zip!", file = resultFile
                            ).apply {
                                val exportCompleted = this
                                openModal(escapeClosesWindow = false)!!.apply {
                                    setOnCloseRequest {
                                        exportCompleted.whenDone()
                                        close()
                                    }
                                }
                            }
                        }
                    }
                }
                // C:\Users\crois\Downloads\OutputFolder
            }
        }
    })
    curView = EditingCardView
    EditingCardView.replaceWith(view, ViewTransition.Fade(0.5.seconds))
}


fun generateString(game: BingoGame): String {
    val stringBuffer = StringBuffer("Bingo Game\n\n")
    stringBuffer.append("Seed -- ${game.seed}\n")
    stringBuffer.append("# of cards: ${game.size}\n")
    stringBuffer.append("# of winners: ${game.desiredNumberOfWinners}\n\n")
    stringBuffer.append("=======================\n")
    for (card in game) {
        stringBuffer.append(generateString(card)).append('\n')
    }
    return stringBuffer.toString()
}

fun generateString(card: BingoCard): String {
    val separator = "+- --- --- --- --- -+\n"
    val builder = StringBuilder(separator)
    val cardNumber = card.cardNumber.toString()
    builder.append("|Card: $cardNumber ${" ".repeat(12 - cardNumber.length)}|\n")
        .append("+- -+- -+- -+- -+- -+\n")
        .append("| B | I | N | G | O |\n")
        .append("+- -+- -+- -+- -+- -+\n")
    for (col in 0 until 5) {
        for (row in 0 until 5) {
            if (row == 2 && col == 2) {
                builder.append("| F ")
                continue
            }
            val string = card[row][col].value.toString()
            if (row == 0) {
                builder.append("| $string" + if (string.length == 1) " " else "")
            } else {
                builder.append("| $string")
            }
        }
        builder.append("|\n+- -+- -+- -+- -+- -+\n")
    }
    return builder.toString()
}

fun generateExportExtras(file: File) {
    assert(file.isDirectory) {
        "$file is not a directory!"
    }
    val firstFile = File("${file.absolutePath}\\balls.log")
    val secondFile = File("${file.absolutePath}\\winners.log")
    if(!firstFile.exists()) {
        firstFile.createNewFile()
    }
    if(!secondFile.exists()) {
        secondFile.createNewFile()
    }
}