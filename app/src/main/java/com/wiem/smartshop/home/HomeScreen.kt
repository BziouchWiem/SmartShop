package com.wiem.smartshop.home

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiem.smartshop.product.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGoToProducts: () -> Unit,
    onGoToStatistics: () -> Unit,
    onGoToNotifications: () -> Unit,
    onGoToProfile: () -> Unit,
    onGoToSettings: () -> Unit,
    onLogout: () -> Unit,
    productViewModel: ProductViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val products by productViewModel.products.collectAsState(emptyList())

    // Statistiques rapides
    val totalProducts = products.size
    val totalStock = products.sumOf { it.quantity }
    val totalValue = products.sumOf { it.price * it.quantity }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onProductsClick = {
                    scope.launch { drawerState.close() }
                    onGoToProducts()
                },
                onStatisticsClick = {
                    scope.launch { drawerState.close() }
                    onGoToStatistics()
                },
                onNotificationsClick = {
                    scope.launch { drawerState.close() }
                    onGoToNotifications()
                },
                onProfileClick = {
                    scope.launch { drawerState.close() }
                    onGoToProfile()
                },
                onSettingsClick = {
                    scope.launch { drawerState.close() }
                    onGoToSettings()
                },
                onLogout = {
                    scope.launch { drawerState.close() }
                    onLogout()
                }
            )
        }
    ) {
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
                            Column {
                                Text(
                                    "LuxeBag",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                                Text(
                                    "Boutique Intelligente",
                                    fontSize = 12.sp,
                                    color = Color(0xFF8C6E63)
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFFFF2DF),
                        titleContentColor = Color(0xFF3E2522)
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFF2DF))
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // CARTE DE BIENVENUE
                WelcomeCard()

                // DASHBOARD - STATISTIQUES RAPIDES
                DashboardCard(
                    totalProducts = totalProducts,
                    totalStock = totalStock,
                    totalValue = totalValue
                )
            }
        }
    }
}

@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
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
                            Color(0xFFFFE0B2),
                            Color(0xFFFFECB3)
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
                            Icons.Default.WavingHand,
                            contentDescription = null,
                            tint = Color(0xFF3E2522),
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            "Bienvenue",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF3E2522)
                        )
                    }
                    Text(
                        "Gérez votre boutique avec élégance",
                        fontSize = 14.sp,
                        color = Color(0xFF8C6E63),
                        lineHeight = 20.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Storefront,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color(0xFF3E2522)
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardCard(
    totalProducts: Int,
    totalStock: Int,
    totalValue: Double
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
            // Titre
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
                        Icons.Default.BarChart,
                        contentDescription = null,
                        tint = Color(0xFF3E2522),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Aperçu Rapide",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3E2522)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE3F2FD)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFFFE0B2))

            // Stats Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Default.Inventory2,
                    value = totalProducts.toString(),
                    label = "Produits",
                    color = Color(0xFFFFCDD2),
                    iconTint = Color(0xFFD32F2F)
                )

                StatItem(
                    icon = Icons.Default.Inventory,
                    value = totalStock.toString(),
                    label = "Stock Total",
                    color = Color(0xFFE1BEE7),
                    iconTint = Color(0xFF9C27B0)
                )

                StatItem(
                    icon = Icons.Default.AttachMoney,
                    value = String.format("%.0f", totalValue),
                    label = "DT Stock",
                    color = Color(0xFFC8E6C9),
                    iconTint = Color(0xFF388E3C)
                )
            }
        }
    }
}

@Composable
fun StatItem(
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
fun DrawerContent(
    onProductsClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onLogout: () -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = Color(0xFFFFF2DF)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header du drawer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFE0B2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFF3E2522)
                        )
                    }

                    Text(
                        "LuxeBag",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3E2522)
                    )

                    Text(
                        "Boutique Intelligente",
                        fontSize = 12.sp,
                        color = Color(0xFF8C6E63)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFFFE0B2))

            Spacer(modifier = Modifier.height(8.dp))

            // Menu Items
            DrawerMenuItem(
                icon = Icons.Default.Inventory2,
                title = "Gestion Produits",
                subtitle = "Ajouter, modifier, supprimer",
                onClick = onProductsClick
            )

            DrawerMenuItem(
                icon = Icons.Default.BarChart,
                title = "Statistiques",
                subtitle = "Analyses et graphiques",
                onClick = onStatisticsClick
            )

            DrawerMenuItem(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                subtitle = "Alertes et rappels",
                onClick = onNotificationsClick
            )

            DrawerMenuItem(
                icon = Icons.Default.Person,
                title = "Profil",
                subtitle = "Votre compte",
                onClick = onProfileClick
            )

            DrawerMenuItem(
                icon = Icons.Default.Settings,
                title = "Paramètres",
                subtitle = "Préférences",
                onClick = onSettingsClick
            )

            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider(color = Color(0xFFFFE0B2))

            // Logout
            DrawerMenuItem(
                icon = Icons.Default.ExitToApp,
                title = "Déconnexion",
                subtitle = "Quitter l'application",
                onClick = onLogout,
                isDestructive = true
            )
        }
    }
}

@Composable
fun DrawerMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDestructive)
                Color(0xFFFFCDD2).copy(alpha = 0.3f)
            else
                Color.White
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        if (isDestructive)
                            Color(0xFFFFCDD2)
                        else
                            Color(0xFFFFE0B2).copy(alpha = 0.5f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = if (isDestructive) Color(0xFFD32F2F) else Color(0xFF3E2522),
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDestructive) Color(0xFFD32F2F) else Color(0xFF3E2522)
                )
                Text(
                    subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF8C6E63)
                )
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF8C6E63)
            )
        }
    }
}