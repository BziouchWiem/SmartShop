package com.wiem.smartshop.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val category: String = "Dior",
    val quantity: Int,
    val price: Double,
    val description: String = "",
    val imageUrl: String = ""
)