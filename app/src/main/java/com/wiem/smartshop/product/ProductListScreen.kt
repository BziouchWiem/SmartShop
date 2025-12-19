package com.wiem.smartshop.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.MoreVert
import android.content.Intent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val exportStatus by productViewModel.exportStatus.collectAsState()
    val context = LocalContext.current

    var showAddDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<ProductEntity?>(null) }
    var showExportMenu by remember { mutableStateOf(false) }

    // Gérer le partage de fichier
    LaunchedEffect(exportStatus) {
        if (exportStatus is ExportStatus.Success) {
            val success = exportStatus as ExportStatus.Success
            context.startActivity(Intent.createChooser(success.shareIntent, "Partager via"))
            productViewModel.resetExportStatus()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SmartShop - Produits") },
                actions = {
                    // Bouton Export
                    IconButton(onClick = { showExportMenu = true }) {
                        Icon(Icons.Default.FileDownload, contentDescription = "Export")
                    }

                    // Menu déroulant Export
                    DropdownMenu(
                        expanded = showExportMenu,
                        onDismissRequest = { showExportMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("📄 Exporter en CSV") },
                            onClick = {
                                showExportMenu = false
                                productViewModel.exportToCSV()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("📕 Exporter en PDF") },
                            onClick = {
                                showExportMenu = false
                                productViewModel.exportToPDF()
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {

            // Message d'export
            when (val status = exportStatus) {
                is ExportStatus.Exporting -> {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                    )
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "📤 Export en cours...",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                is ExportStatus.Error -> {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.errorContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⚠️ ${status.message}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            TextButton(onClick = { productViewModel.resetExportStatus() }) {
                                Text("OK")
                            }
                        }
                    }
                }
                else -> {}
            }

            // Liste des produits
            if (products.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Aucun produit ajouté")
                        Text(
                            "Appuyez sur + pour ajouter un produit",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
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