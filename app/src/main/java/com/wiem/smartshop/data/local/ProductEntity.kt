package com.wiem.smartshop.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val category: String = "Dior",  // Marque (Dior, Gucci, etc.)
    val quantity: Int,
    val price: Double,
    val description: String = "",   // Description du produit
    val imageUrl: String = ""       // URL image (optionnel)
)