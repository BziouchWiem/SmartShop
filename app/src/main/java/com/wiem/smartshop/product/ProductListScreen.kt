package com.wiem.smartshop.product

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiem.smartshop.data.local.ProductEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onProductClick: (ProductEntity) -> Unit = {}
) {
    val products by productViewModel.products.collectAsState(emptyList())
    val exportStatus by productViewModel.exportStatus.collectAsState()
    val context = LocalContext.current

    var showAddDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedBrand by remember { mutableStateOf("All") }
    var showExportMenu by remember { mutableStateOf(false) }

    val brands = listOf("All", "Dior", "Gucci", "Prada", "Louis Vuitton", "Chanel", "Hermès", "Versace")

    // Filtrer les produits
    val filteredProducts = products.filter { product ->
        val matchesBrand = selectedBrand == "All" || product.category == selectedBrand
        val matchesSearch = searchQuery.isEmpty() ||
                product.name.contains(searchQuery, ignoreCase = true)
        matchesBrand && matchesSearch
    }

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
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Storefront,
                            contentDescription = null,
                            tint = Color(0xFF3E2522),
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            "Mes Produits",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Retour")
                    }
                },
                actions = {
                    // Bouton Export
                    IconButton(onClick = { showExportMenu = true }) {
                        Icon(Icons.Default.Download, "Export", tint = Color(0xFF3E2522))
                    }

                    // Menu Export
                    DropdownMenu(
                        expanded = showExportMenu,
                        onDismissRequest = { showExportMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Description,
                                        contentDescription = null,
                                        tint = Color(0xFF3E2522)
                                    )
                                    Text("Exporter en CSV")
                                }
                            },
                            onClick = {
                                showExportMenu = false
                                productViewModel.exportToCSV()
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.PictureAsPdf,
                                        contentDescription = null,
                                        tint = Color(0xFFD32F2F)
                                    )
                                    Text("Exporter en PDF")
                                }
                            },
                            onClick = {
                                showExportMenu = false
                                productViewModel.exportToPDF()
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFF2DF),
                    titleContentColor = Color(0xFF3E2522)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFF3E2522),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, "Ajouter", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF2DF))
                .padding(padding)
        ) {
            // Message d'export
            when (val status = exportStatus) {
                is ExportStatus.Exporting -> {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF3E2522),
                        trackColor = Color(0xFFFFE0B2)
                    )
                }
                is ExportStatus.Error -> {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFFFCDD2),
                        tonalElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.ErrorOutline,
                                    contentDescription = null,
                                    tint = Color(0xFFD32F2F)
                                )
                                Text(
                                    status.message,
                                    fontSize = 13.sp,
                                    color = Color(0xFFD32F2F)
                                )
                            }
                            TextButton(onClick = { productViewModel.resetExportStatus() }) {
                                Text("OK", color = Color(0xFFD32F2F))
                            }
                        }
                    }
                }
                else -> {}
            }

            // Barre de recherche
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Rechercher un produit...") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            null,
                            tint = Color(0xFF3E2522)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    Icons.Default.Close,
                                    "Effacer",
                                    tint = Color.Gray
                                )
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF3E2522),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Filtres marques (scrollable horizontalement)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(brands) { brand ->
                    FilterChip(
                        selected = selectedBrand == brand,
                        onClick = { selectedBrand = brand },
                        label = { Text(brand, fontSize = 13.sp, fontWeight = FontWeight.Medium) },
                        leadingIcon = if (selectedBrand == brand) {
                            {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF3E2522),
                            selectedLabelColor = Color.White,
                            selectedLeadingIconColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Liste des produits
            if (filteredProducts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ShoppingBag,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = Color.Gray.copy(alpha = 0.5f)
                            )
                        }
                        Text(
                            if (searchQuery.isNotEmpty()) "Aucun produit trouvé"
                            else "Votre boutique est vide",
                            fontSize = 18.sp,
                            color = Color(0xFF3E2522),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            if (searchQuery.isNotEmpty()) "Essayez une autre recherche"
                            else "Appuyez sur + pour ajouter un produit",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredProducts, key = { it.id }) { product ->
                        ModernProductCard(
                            product = product,
                            onClick = { onProductClick(product) }
                        )
                    }
                }
            }
        }
    }

    // Dialog Ajout
    if (showAddDialog) {
        AddProductDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { name, brand, quantity, price, description ->
                productViewModel.addProduct(name, brand, quantity, price, description)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun ModernProductCard(
    product: ProductEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image placeholder avec icône
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFE0B2).copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ShoppingBag,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color(0xFF3E2522)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Infos produit
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color(0xFF3E2522),
                    maxLines = 1
                )

                // Badge marque
                Surface(
                    color = Color(0xFF3E2522).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Label,
                            contentDescription = null,
                            tint = Color(0xFF3E2522),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            product.category,
                            fontSize = 12.sp,
                            color = Color(0xFF3E2522),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Prix et Stock
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.AttachMoney,
                            contentDescription = null,
                            tint = Color(0xFF388E3C),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            "${product.price} DT",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF388E3C)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Inventory,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "${product.quantity}",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Flèche
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "Voir détails",
                tint = Color(0xFF3E2522),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}