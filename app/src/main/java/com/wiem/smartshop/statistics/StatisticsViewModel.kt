package com.wiem.smartshop.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wiem.smartshop.data.local.AppDatabase
import com.wiem.smartshop.data.local.ProductEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Statistics(
    val totalProducts: Int = 0,
    val totalStockValue: Double = 0.0,
    val productsByCategory: Map<String, Int> = emptyMap(),
    val products: List<ProductEntity> = emptyList()
)

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {

    private val productDao = AppDatabase.getDatabase(application).productDao()

    private val _statistics = MutableStateFlow(Statistics())
    val statistics: StateFlow<Statistics> = _statistics.asStateFlow()

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            productDao.getAllProducts().collect { products ->
                val totalProducts = products.size
                val totalValue = products.sumOf { it.price * it.quantity }

                _statistics.value = Statistics(
                    totalProducts = totalProducts,
                    totalStockValue = totalValue,
                    products = products
                )
            }
        }
    }

    fun refreshStatistics() {
        loadStatistics()
    }
}