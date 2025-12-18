package com.wiem.smartshop.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wiem.smartshop.data.local.ProductEntity
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun getUserProductsCollection() =
        firestore.collection("users")
            .document(auth.currentUser?.uid ?: "anonymous")
            .collection("products")

    suspend fun syncProductToCloud(product: ProductEntity) {
        try {
            val productMap = hashMapOf(
                "id" to product.id,
                "name" to product.name,
                "quantity" to product.quantity,
                "price" to product.price
            )

            getUserProductsCollection()
                .document(product.id)
                .set(productMap)
                .await()

            Log.d("Firestore", "✅ Produit ${product.name} synchronisé")
        } catch (e: Exception) {
            Log.e("Firestore", "❌ Erreur: ${e.message}")
        }
    }

    suspend fun deleteProductFromCloud(productId: String) {
        try {
            getUserProductsCollection()
                .document(productId)
                .delete()
                .await()

            Log.d("Firestore", "✅ Produit supprimé du cloud")
        } catch (e: Exception) {
            Log.e("Firestore", "❌ Erreur: ${e.message}")
        }
    }
}