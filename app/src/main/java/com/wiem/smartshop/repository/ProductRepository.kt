package com.wiem.smartshop.repository

import com.wiem.smartshop.data.local.ProductDao
import com.wiem.smartshop.data.local.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val dao: ProductDao,
    private val firestoreRepository: FirestoreRepository = FirestoreRepository()
) {

    val products: Flow<List<ProductEntity>> = dao.getAllProducts()

    suspend fun insert(product: ProductEntity) {
        // UUID est déjà généré dans ProductEntity par défaut
        dao.insertProduct(product)
        firestoreRepository.syncProductToCloud(product)
    }

    suspend fun update(product: ProductEntity) {
        dao.updateProduct(product)
        firestoreRepository.syncProductToCloud(product)
    }

    suspend fun delete(product: ProductEntity) {
        dao.deleteProduct(product)
        firestoreRepository.deleteProductFromCloud(product.id)
    }
}