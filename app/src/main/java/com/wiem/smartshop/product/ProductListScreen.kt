package com.wiem.smartshop.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wiem.smartshop.data.local.ProductEntity

@Composable
fun ProductListScreen(
    products: List<ProductEntity>,
    onDelete: (ProductEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(product.name, style = MaterialTheme.typography.titleMedium)
                    Text("Quantité : ${product.quantity}")
                    Text("Prix : ${product.price} DT")

                    Button(
                        onClick = { onDelete(product) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Supprimer")
                    }
                }
            }
        }
    }
}
