package dev.kason.bingo

import javafx.scene.control.RadioButton
import tornadofx.ViewTransition
import tornadofx.runLater
import tornadofx.seconds
import java.awt.Toolkit
import java.awt.datatransfer.*
import java.awt.image.BufferedImage
import java.io.File
import kotlin.concurrent.thread


internal fun generateImages(): List<BufferedImage> {
    return listOf()
}

lateinit var resultFile: File
lateinit var bulkImgTypes: String

var isFolder = true

fun pdfUI() {
    val view = FindFileView("game.pdf")
    typeOfFile = 0
    EditingCardView.replaceWith(view, ViewTransition.Slide(0.5.seconds))
    view.whenFinished = {
        checkFileAndRun {
            replaceWith(FormattingView, ViewTransition.Slide(0.5.seconds))
            FormattingView.action = {
                thread {
                    generateDoc()
                    runLater {
                        ExportCompleted(whenDone = {
                            FormattingView.replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds))
                        }).apply {
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

fun exportTXTText() {
    EditingCardView.replaceWith(FindFileView(string = "game.txt", whenFinished = {
        checkFileAndRun {
            replaceWith(ExportTextView("Export text to file", {
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
                            this@ExportTextView.replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds))
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
            }, "File Location: $resultFile"), ViewTransition.Slide(0.5.seconds))
        }
    }).also {
        curView = it
    }, ViewTransition.Slide(0.5.seconds))
}

fun exportOtherText() {
    EditingCardView.replaceWith(FindFileView(string = "game", whenFinished = {
        checkFileAndRun {
            replaceWith(ExportTextView("Export text to file", {
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
                            this@ExportTextView.replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds))
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
            }, "File Location: $resultFile"), ViewTransition.Slide(0.5.seconds))
        }
    }, implementExtensionCheck = false).also {
        curView = it
    }, ViewTransition.Slide(0.5.seconds))
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
            this@ExportTextView.replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds))
        }, message = "Finished exporting to clipboard!").apply {
            val exportCompleted = this
            openModal(escapeClosesWindow = false)!!.apply {
                setOnCloseRequest {
                    exportCompleted.whenDone()
                    close()
                }
            }
        }
    }), ViewTransition.Slide(0.5.seconds))
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

fun wordUI() {
    val view = FindFileView("game.docx")
    typeOfFile = 1
    EditingCardView.replaceWith(view, ViewTransition.Slide(0.5.seconds))
    view.whenFinished = {
        checkFileAndRun {
            replaceWith(FormattingView, ViewTransition.Slide(0.5.seconds))
            FormattingView.action = {
                thread {
                    generateDoc()
                    runLater {
                        ExportCompleted(whenDone = {
                            FormattingView.replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds))
                        }).apply {
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

private fun generateDoc() {

}

fun exportImageCB() {
    EditingCardView.replaceWith(ExportAsClipBoardView, ViewTransition.Slide(0.5.seconds))
    ExportAsClipBoardView.action = {
        curView = ExportAsClipBoardView
        replaceWith(FormattingView, ViewTransition.Slide(0.5.seconds))
        FormattingView.action = {
            val image = generateViewRes(format = number, startingIndex = value - 1)
            thread {
                val trans = TransferableImage(image)
                val c = Toolkit.getDefaultToolkit().systemClipboard
                c.setContents(trans) { _, _ -> }
                runLater {
                    ExportCompleted(whenDone = {
                        FormattingView.replaceWith(EditingCardView, ViewTransition.Slide(0.5.seconds))
                    }).apply {
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

    override fun getTransferData(flavor: DataFlavor): Any? {
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
    val view = FolderFindFileView()
    typeOfFile = 0
    EditingCardView.replaceWith(view, ViewTransition.Slide(0.5.seconds))
    view.whenFinished = {
        println("sdf")

    }
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