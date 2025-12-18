package com.wiem.smartshop.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wiem.smartshop.data.local.AppDatabase
import com.wiem.smartshop.data.local.ProductEntity
import com.wiem.smartshop.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository
    val products: Flow<List<ProductEntity>>

    init {
        val dao = AppDatabase.getDatabase(application).productDao()
        repository = ProductRepository(dao)
        products = repository.products
    }

    fun addProduct(name: String, quantity: Int, price: Double) {
        if (price > 0 && quantity >= 0) {
            viewModelScope.launch {
                repository.insert(
                    ProductEntity(
                        name = name,
                        quantity = quantity,
                        price = price
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
            repository.update(product)  // ✅ Correction ici
        }
    }
}