package com.wiem.smartshop.product

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wiem.smartshop.data.local.AppDatabase
import com.wiem.smartshop.data.local.ProductEntity
import com.wiem.smartshop.repository.ExportRepository
import com.wiem.smartshop.repository.FirestoreRepository
import com.wiem.smartshop.repository.ProductRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository
    private val exportRepository: ExportRepository
    val products: Flow<List<ProductEntity>>

    private val _exportStatus = MutableStateFlow<ExportStatus>(ExportStatus.Idle)
    val exportStatus: StateFlow<ExportStatus> = _exportStatus.asStateFlow()

    init {
        val dao = AppDatabase.getDatabase(application).productDao()
        repository = ProductRepository(dao, FirestoreRepository())
        exportRepository = ExportRepository(application)
        products = repository.products
    }

    fun addProduct(
        name: String,
        category: String,
        quantity: Int,
        price: Double,
        description: String
    ) {
        if (price > 0 && quantity >= 0 && name.isNotBlank()) {
            viewModelScope.launch {
                repository.insert(
                    ProductEntity(
                        name = name,
                        category = category,
                        quantity = quantity,
                        price = price,
                        description = description
                    )
                )
            }
        }
    }

    fun deleteProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.delete(product)
        }
    }

    fun updateProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.update(product)
        }
    }

    fun exportToCSV() {
        viewModelScope.launch {
            _exportStatus.value = ExportStatus.Exporting
            try {
                val productList = products.first()
                if (productList.isEmpty()) {
                    _exportStatus.value = ExportStatus.Error("Aucun produit à exporter")
                    return@launch
                }
                val result = exportRepository.exportToCSV(productList)
                if (result.isSuccess) {
                    val file = result.getOrNull()!!
                    val shareIntent = exportRepository.shareFile(file)
                    _exportStatus.value = ExportStatus.Success(file, shareIntent)
                } else {
                    _exportStatus.value = ExportStatus.Error(result.exceptionOrNull()?.message ?: "Erreur")
                }
            } catch (e: Exception) {
                _exportStatus.value = ExportStatus.Error(e.message ?: "Erreur export")
            }
        }
    }

    fun exportToPDF() {
        viewModelScope.launch {
            _exportStatus.value = ExportStatus.Exporting
            try {
                val productList = products.first()
                if (productList.isEmpty()) {
                    _exportStatus.value = ExportStatus.Error("Aucun produit à exporter")
                    return@launch
                }
                val result = exportRepository.exportToPDF(productList)
                if (result.isSuccess) {
                    val file = result.getOrNull()!!
                    val shareIntent = exportRepository.shareFile(file)
                    _exportStatus.value = ExportStatus.Success(file, shareIntent)
                } else {
                    _exportStatus.value = ExportStatus.Error(result.exceptionOrNull()?.message ?: "Erreur")
                }
            } catch (e: Exception) {
                _exportStatus.value = ExportStatus.Error(e.message ?: "Erreur export")
            }
        }
    }

    fun resetExportStatus() {
        _exportStatus.value = ExportStatus.Idle
    }
}

sealed class ExportStatus {
    object Idle : ExportStatus()
    object Exporting : ExportStatus()
    data class Success(val file: File, val shareIntent: Intent) : ExportStatus()
    data class Error(val message: String) : ExportStatus()
}