package com.wiem.smartshop.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    onBackClick: () -> Unit,
    statisticsViewModel: StatisticsViewModel = viewModel()
) {
    val statistics by statisticsViewModel.statistics.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.BarChart,
                            contentDescription = null,
                            tint = Color(0xFF3E2522),
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            "Statistiques",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color(0xFF3E2522)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color(0xFF3E2522)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { statisticsViewModel.refreshStatistics() }) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE3F2FD)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "Actualiser",
                                tint = Color(0xFF2196F3),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFF2DF),
                    titleContentColor = Color(0xFF3E2522)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF2DF))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // CARTE D'EN-TÊTE
            HeaderCard()

            // STATISTIQUES PRINCIPALES
            MainStatsCard(
                totalProducts = statistics.totalProducts,
                totalStockValue = statistics.totalStockValue
            )

            // GRAPHIQUE CIRCULAIRE
            if (statistics.products.isNotEmpty()) {
                PieChartCard(products = statistics.products)

                // LISTE DÉTAILLÉE DES PRODUITS
                ProductsDetailCard(products = statistics.products)
            } else {
                EmptyStateCard()
            }
        }
    }
}

@Composable
fun HeaderCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFE3F2FD),
                            Color(0xFFBBDEFB)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.TrendingUp,
                            contentDescription = null,
                            tint = Color(0xFF1976D2),
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            "Analyse",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF3E2522)
                        )
                    }
                    Text(
                        "Vue d'ensemble de votre boutique",
                        fontSize = 14.sp,
                        color = Color(0xFF8C6E63),
                        lineHeight = 20.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Analytics,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color(0xFF1976D2)
                    )
                }
            }
        }
    }
}

@Composable
fun MainStatsCard(
    totalProducts: Int,
    totalStockValue: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Assessment,
                        contentDescription = null,
                        tint = Color(0xFF3E2522),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Indicateurs Clés",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3E2522)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFFFE0B2))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatIndicator(
                    icon = Icons.Default.Inventory2,
                    value = totalProducts.toString(),
                    label = "Produits",
                    color = Color(0xFFFFCDD2),
                    iconTint = Color(0xFFD32F2F)
                )

                StatIndicator(
                    icon = Icons.Default.AttachMoney,
                    value = DecimalFormat("#,##0").format(totalStockValue),
                    label = "DT Stock",
                    color = Color(0xFFC8E6C9),
                    iconTint = Color(0xFF388E3C)
                )
            }
        }
    }
}

@Composable
fun StatIndicator(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    color: Color,
    iconTint: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(32.dp)
            )
        }

        Text(
            value,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2522)
        )

        Text(
            label,
            fontSize = 12.sp,
            color = Color(0xFF8C6E63)
        )
    }
}

@Composable
fun PieChartCard(products: List<com.wiem.smartshop.data.local.ProductEntity>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.PieChart,
                        contentDescription = null,
                        tint = Color(0xFF3E2522),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Répartition par Produit",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3E2522)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFFFE0B2))

            PieChartView(products = products)
        }
    }
}

@Composable
fun ProductsDetailCard(products: List<com.wiem.smartshop.data.local.ProductEntity>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.ListAlt,
                        contentDescription = null,
                        tint = Color(0xFF3E2522),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Détails des Produits",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3E2522)
                    )
                }

                Text(
                    "${products.size}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            }

            HorizontalDivider(color = Color(0xFFFFE0B2))

            products.forEachIndexed { index, product ->
                ProductDetailCard(
                    name = product.name,
                    quantity = product.quantity,
                    price = product.price,
                    totalValue = product.quantity * product.price
                )

                if (index < products.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ProductDetailCard(
    name: String,
    quantity: Int,
    price: Double,
    totalValue: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8E1)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFE0B2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.ShoppingBag,
                        contentDescription = null,
                        tint = Color(0xFF3E2522),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF3E2522)
                    )
                    Text(
                        text = "Qté: $quantity | Prix: ${DecimalFormat("#,##0.00").format(price)} DT",
                        fontSize = 12.sp,
                        color = Color(0xFF8C6E63)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${DecimalFormat("#,##0.00").format(totalValue)} DT",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF388E3C)
                )
                Text(
                    text = "Total",
                    fontSize = 11.sp,
                    color = Color(0xFF8C6E63)
                )
            }
        }
    }
}

@Composable
fun EmptyStateCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFE0B2)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Inbox,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    tint = Color(0xFF3E2522)
                )
            }

            Text(
                text = "Aucun produit disponible",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E2522)
            )

            Text(
                text = "Ajoutez des produits pour voir les statistiques",
                fontSize = 14.sp,
                color = Color(0xFF8C6E63)
            )
        }
    }
}

@Composable
fun PieChartView(products: List<com.wiem.smartshop.data.local.ProductEntity>) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                isRotationEnabled = true
                setUsePercentValues(true)
                setEntryLabelColor(android.graphics.Color.parseColor("#3E2522"))
                setEntryLabelTextSize(12f)
                legend.isEnabled = true
                legend.textColor = android.graphics.Color.parseColor("#3E2522")

                val entries = products.map { product ->
                    PieEntry(
                        (product.quantity * product.price).toFloat(),
                        product.name
                    )
                }

                val dataSet = PieDataSet(entries, "").apply {
                    colors = listOf(
                        android.graphics.Color.parseColor("#7A1008"), // Bordeaux foncé
                        android.graphics.Color.parseColor("#E22413"), // Rouge sac
                        android.graphics.Color.parseColor("#E2D7AC"), // Beige cuir
                        android.graphics.Color.parseColor("#3A8232"), // Vert foncé
                        android.graphics.Color.parseColor("#2F5323")  // Vert olive
                    )
                    valueTextSize = 14f
                    valueTextColor = android.graphics.Color.WHITE
                }

                val pieData = PieData(dataSet)
                data = pieData

                animateY(1000)
                invalidate()
            }
        }
    )
}