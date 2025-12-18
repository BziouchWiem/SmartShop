package com.wiem.smartshop.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiem.smartshop.data.local.ProductEntity
import com.wiem.smartshop.ui.products.EditProductDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel = viewModel()
) {
    val products by productViewModel.products.collectAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<ProductEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("SmartShop - Produits") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->

        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Aucun produit ajouté")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(
                    items = products,
                    key = { it.id }
                ) { product ->
                    ProductCard(
                        product = product,
                        onDelete = { productViewModel.deleteProduct(product) },
                        onEdit = { productToEdit = product }
                    )
                }
            }
        }
    }

    // ➕ Add dialog
    if (showAddDialog) {
        AddProductDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { name: String, quantity: Int, price: Double ->
                productViewModel.addProduct(name, quantity, price)
                showAddDialog = false
            }
        )
    }

    // ✏️ Edit dialog
    productToEdit?.let { product ->
        EditProductDialog(
            product = product,
            onDismiss = { productToEdit = null },
            onConfirm = { updatedProduct: ProductEntity ->
                productViewModel.updateProduct(updatedProduct)
                productToEdit = null
            }
        )
    }
}