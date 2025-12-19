package com.wiem.smartshop.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.wiem.smartshop.data.local.ProductEntity
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ExportRepository(private val context: Context) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
    private val priceFormat = DecimalFormat("#,##0.00")

    /**
     * Exporter les produits en CSV
     */
    fun exportToCSV(products: List<ProductEntity>): Result<File> {
        return try {
            val fileName = "smartshop_products_${dateFormat.format(Date())}.csv"
            val file = File(context.getExternalFilesDir(null), fileName)

            file.bufferedWriter().use { writer ->
                // En-têtes
                writer.write("ID,Nom,Quantité,Prix (DT),Valeur Totale (DT)\n")

                // Données
                products.forEach { product ->
                    val totalValue = product.quantity * product.price
                    writer.write(
                        "${product.id}," +
                                "\"${product.name}\"," +
                                "${product.quantity}," +
                                "${priceFormat.format(product.price)}," +
                                "${priceFormat.format(totalValue)}\n"
                    )
                }

                // Ligne de totaux
                val totalProducts = products.size
                val totalValue = products.sumOf { it.quantity * it.price }
                writer.write("\n")
                writer.write("TOTAL,$totalProducts produits,,${priceFormat.format(totalValue)}\n")
            }

            Log.d("ExportRepository", "✅ CSV exporté: ${file.absolutePath}")
            Result.success(file)
        } catch (e: Exception) {
            Log.e("ExportRepository", "❌ Erreur export CSV: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Exporter les produits en PDF
     */
    fun exportToPDF(products: List<ProductEntity>): Result<File> {
        return try {
            val fileName = "smartshop_products_${dateFormat.format(Date())}.pdf"
            val file = File(context.getExternalFilesDir(null), fileName)

            val pdfWriter = PdfWriter(file)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)

            // Titre
            document.add(
                Paragraph("SmartShop - Liste des Produits")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20f)
                    .setBold()
            )

            document.add(Paragraph("Date: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())}"))
            document.add(Paragraph("\n"))

            // Tableau
            val table = Table(floatArrayOf(1f, 3f, 2f, 2f, 2f))
            table.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100f))

            // En-têtes
            table.addHeaderCell(Cell().add(Paragraph("ID").setBold()))
            table.addHeaderCell(Cell().add(Paragraph("Nom").setBold()))
            table.addHeaderCell(Cell().add(Paragraph("Quantité").setBold()))
            table.addHeaderCell(Cell().add(Paragraph("Prix (DT)").setBold()))
            table.addHeaderCell(Cell().add(Paragraph("Total (DT)").setBold()))

            // Données
            products.forEach { product ->
                val totalValue = product.quantity * product.price
                table.addCell(Cell().add(Paragraph(product.id.take(8))))
                table.addCell(Cell().add(Paragraph(product.name)))
                table.addCell(Cell().add(Paragraph(product.quantity.toString())))
                table.addCell(Cell().add(Paragraph(priceFormat.format(product.price))))
                table.addCell(Cell().add(Paragraph(priceFormat.format(totalValue))))
            }

            document.add(table)
            document.add(Paragraph("\n"))

            // Statistiques
            val totalProducts = products.size
            val totalValue = products.sumOf { it.quantity * it.price }

            document.add(Paragraph("STATISTIQUES").setBold().setFontSize(14f))
            document.add(Paragraph("Nombre total de produits: $totalProducts"))
            document.add(Paragraph("Valeur totale du stock: ${priceFormat.format(totalValue)} DT"))

            document.close()

            Log.d("ExportRepository", "✅ PDF exporté: ${file.absolutePath}")
            Result.success(file)
        } catch (e: Exception) {
            Log.e("ExportRepository", "❌ Erreur export PDF: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Partager un fichier via Intent
     */
    fun shareFile(file: File): Intent {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val mimeType = when (file.extension.lowercase()) {
            "csv" -> "text/csv"
            "pdf" -> "application/pdf"
            else -> "*/*"
        }

        return Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
}