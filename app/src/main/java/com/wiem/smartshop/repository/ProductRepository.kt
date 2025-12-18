package com.wiem.smartshop.repository

import com.wiem.smartshop.data.local.ProductDao
import com.wiem.smartshop.data.local.ProductEntity

class ProductRepository(private val dao: ProductDao) {

    val products = dao.getAllProducts()

    suspend fun insert(product: ProductEntity) = dao.insertProduct(product)
    suspend fun update(product: ProductEntity) = dao.updateProduct(product)
    suspend fun delete(product: ProductEntity) = dao.deleteProduct(product)
}
