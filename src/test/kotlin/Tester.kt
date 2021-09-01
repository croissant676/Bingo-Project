import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfDocument
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream


fun main() {

    val dest = "C:\\Users\\crois\\Downloads\\jwxyz.docx"


    /* // This is for iText
    val file = Document()
    val dest = "C:\\Users\\crois\\Downloads\\jwxyz.pdf"
    val writer = PdfWriter.getInstance(file, FileOutputStream(dest))!!
    file.open()
    file.add(Paragraph("Hello World!"))
    file.close()

     */
}