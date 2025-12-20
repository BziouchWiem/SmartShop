// DashboardScreen.kt - Version complète et corrigée
package com.wiem.smartshop.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiem.smartshop.ui.theme.SmartShopColors


data class DashboardItem(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String,
    val color: Color,
    val iconTint: Color = Color.White
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onProductsClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onLogout: () -> Unit
) {
    val dashboardItems = listOf(
        DashboardItem(
            title = "📦 Gestion Produits",
            description = "Ajouter, modifier, exporter",
            icon = Icons.Default.Inventory2,
            route = "products",
            color = SmartShopColors.PinkPastel,
            iconTint = SmartShopColors.DarkBrown
        ),
        DashboardItem(
            title = "📊 Statistiques",
            description = "Analyses et graphiques",
            icon = Icons.Default.BarChart,
            route = "statistics",
            color = SmartShopColors.BabyBlue,
            iconTint = SmartShopColors.DarkBrown
        ),
        DashboardItem(
            title = "🔔 Notifications",
            description = "Alertes et rappels",
            icon = Icons.Default.Notifications,
            route = "notifications",
            color = SmartShopColors.Lavender,
            iconTint = SmartShopColors.DarkBrown
        ),
        DashboardItem(
            title = "👤 Profil",
            description = "Votre compte",
            icon = Icons.Default.Person,
            route = "profile",
            color = SmartShopColors.Peach,
            iconTint = SmartShopColors.DarkBrown
        ),
        DashboardItem(
            title = "⚙️ Paramètres",
            description = "Préférences",
            icon = Icons.Default.Settings,
            route = "settings",
            color = SmartShopColors.MintGreen,
            iconTint = SmartShopColors.DarkBrown
        ),
        DashboardItem(
            title = "🚪 Déconnexion",
            description = "Quitter l'application",
            icon = Icons.Default.ExitToApp,
            route = "logout",
            color = Color(0xFFFFCCCB),
            iconTint = Color(0xFFD32F2F)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "SmartShop 👜",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = SmartShopColors.DarkBrown
                        )
                        Text(
                            "Boutique Intelligente",
                            style = MaterialTheme.typography.bodySmall,
                            color = SmartShopColors.MediumBrown
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SmartShopColors.White,
                    titleContentColor = SmartShopColors.DarkBrown
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(SmartShopColors.Cream)
        ) {
            // Carte de bienvenue avec dégradé cute
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(140.dp),
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    SmartShopColors.PinkPastel,
                                    SmartShopColors.Lavender
                                )
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Bienvenue 👋",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = SmartShopColors.DarkBrown,
                                fontSize = 24.sp
                            )
                            Text(
                                "Gérez votre boutique avec élégance",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SmartShopColors.DarkBrown.copy(alpha = 0.8f)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Storefront,
                                contentDescription = "Boutique",
                                tint = SmartShopColors.DarkBrown,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }

            // Grid des fonctionnalités avec design cute
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(dashboardItems) { item ->
                    CuteDashboardCard(
                        item = item,
                        onClick = {
                            when (item.route) {
                                "products" -> onProductsClick()
                                "statistics" -> onStatisticsClick()
                                "notifications" -> onNotificationsClick()
                                "profile" -> onProfileClick()
                                "settings" -> onSettingsClick()
                                "logout" -> onLogout()
                            }
                        }
                    )
                }
            }

            // Statistiques rapides en bas avec design cute
            QuickStatsCuteCard()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CuteDashboardCard(
    item: DashboardItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = item.color.copy(alpha = 0.3f)
        ),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            item.color.copy(alpha = 0.4f),
                            item.color.copy(alpha = 0.1f)
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(item.color.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            item.icon,
                            contentDescription = item.title,
                            tint = item.iconTint,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Column {
                    Text(
                        item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SmartShopColors.DarkBrown,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = SmartShopColors.DarkBrown.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun QuickStatsCuteCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = SmartShopColors.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "📈 Aperçu Rapide",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = SmartShopColors.DarkBrown
                )

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SmartShopColors.BabyBlue.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.TrendingUp,
                        contentDescription = "Tendance",
                        tint = SmartShopColors.BabyBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CuteStatItem(
                    value = "24",
                    label = "Produits",
                    color = SmartShopColors.PinkPastel,
                    icon = "📦"
                )
                CuteStatItem(
                    value = "5",
                    label = "Alertes",
                    color = SmartShopColors.MintGreen,
                    icon = "🔔"
                )
                CuteStatItem(
                    value = "1.2K",
                    label = "DT Stock",
                    color = SmartShopColors.BabyBlue,
                    icon = "💰"
                )
            }
        }
    }
}

@Composable
fun CuteStatItem(value: String, label: String, color: Color, icon: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = SmartShopColors.DarkBrown,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = SmartShopColors.MediumBrown,
            fontSize = 12.sp
        )
    }
}