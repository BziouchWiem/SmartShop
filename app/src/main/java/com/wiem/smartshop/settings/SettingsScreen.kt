
package com.wiem.smartshop.settings

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    var darkMode by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var lowStockAlerts by remember { mutableStateOf(true) }
    var autoBackup by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            tint = Color(0xFF3E2522),
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            "Paramètres",
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
            SettingsHeaderCard()

            // APPARENCE
            AppearanceCard(
                darkMode = darkMode,
                onDarkModeChange = { darkMode = it }
            )

            // NOTIFICATIONS
            NotificationsCard(
                notificationsEnabled = notificationsEnabled,
                lowStockAlerts = lowStockAlerts,
                onNotificationsChange = { notificationsEnabled = it },
                onLowStockChange = { lowStockAlerts = it }
            )

            // DONNÉES & SAUVEGARDE
            DataCard(
                autoBackup = autoBackup,
                onAutoBackupChange = { autoBackup = it }
            )

            // CONFIDENTIALITÉ
            PrivacyCard()

            // APPLICATION
            AppInfoCard()

            // BOUTON RÉINITIALISATION
            ResetButton()
        }
    }
}

@Composable
fun SettingsHeaderCard() {
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
                            Color(0xFFF3E5F5),
                            Color(0xFFE1BEE7)
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
                            Icons.Default.Tune,
                            contentDescription = null,
                            tint = Color(0xFF7B1FA2),
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            "Configuration",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF3E2522)
                        )
                    }
                    Text(
                        "Personnalisez votre expérience",
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
                        Icons.Default.Construction,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color(0xFF7B1FA2)
                    )
                }
            }
        }
    }
}

@Composable
fun AppearanceCard(
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Palette,
                    contentDescription = null,
                    tint = Color(0xFF3E2522),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    "Apparence",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2522)
                )
            }

            HorizontalDivider(color = Color(0xFFFFE0B2))

            SettingsSwitchRow(
                icon = Icons.Default.DarkMode,
                title = "Mode sombre",
                subtitle = "Réduire la fatigue oculaire",
                checked = darkMode,
                onCheckedChange = onDarkModeChange,
                color = Color(0xFF424242),
                iconTint = Color(0xFF757575)
            )

            SettingsOptionRow(
                icon = Icons.Default.ColorLens,
                title = "Thème de couleur",
                subtitle = "Brun élégant",
                color = Color(0xFFFFE0B2),
                iconTint = Color(0xFFD84315)
            )
        }
    }
}

@Composable
fun NotificationsCard(
    notificationsEnabled: Boolean,
    lowStockAlerts: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    onLowStockChange: (Boolean) -> Unit
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color(0xFF3E2522),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    "Notifications",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2522)
                )
            }

            HorizontalDivider(color = Color(0xFFFFE0B2))

            SettingsSwitchRow(
                icon = Icons.Default.NotificationsActive,
                title = "Activer les notifications",
                subtitle = "Recevoir des alertes",
                checked = notificationsEnabled,
                onCheckedChange = onNotificationsChange,
                color = Color(0xFFE3F2FD),
                iconTint = Color(0xFF2196F3)
            )

            SettingsSwitchRow(
                icon = Icons.Default.Warning,
                title = "Alertes stock faible",
                subtitle = "Notification automatique",
                checked = lowStockAlerts,
                onCheckedChange = onLowStockChange,
                enabled = notificationsEnabled,
                color = Color(0xFFFFEBEE),
                iconTint = Color(0xFFF44336)
            )
        }
    }
}

@Composable
fun DataCard(
    autoBackup: Boolean,
    onAutoBackupChange: (Boolean) -> Unit
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Storage,
                    contentDescription = null,
                    tint = Color(0xFF3E2522),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    "Données & Sauvegarde",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2522)
                )
            }

            HorizontalDivider(color = Color(0xFFFFE0B2))

            SettingsSwitchRow(
                icon = Icons.Default.Backup,
                title = "Sauvegarde automatique",
                subtitle = "Quotidienne",
                checked = autoBackup,
                onCheckedChange = onAutoBackupChange,
                color = Color(0xFFF3E5F5),
                iconTint = Color(0xFF9C27B0)
            )

            SettingsOptionRow(
                icon = Icons.Default.CloudUpload,
                title = "Synchronisation cloud",
                subtitle = "Activée",
                color = Color(0xFFE8F5E9),
                iconTint = Color(0xFF4CAF50)
            )

            SettingsActionRow(
                icon = Icons.Default.FileDownload,
                title = "Exporter les données",
                subtitle = "Télécharger une copie",
                color = Color(0xFFE1F5FE),
                iconTint = Color(0xFF03A9F4)
            )
        }
    }
}

@Composable
fun PrivacyCard() {
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Shield,
                    contentDescription = null,
                    tint = Color(0xFF3E2522),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    "Confidentialité",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2522)
                )
            }

            HorizontalDivider(color = Color(0xFFFFE0B2))

            SettingsOptionRow(
                icon = Icons.Default.PrivacyTip,
                title = "Politique de confidentialité",
                subtitle = "Consulter",
                color = Color(0xFFFFF8E1),
                iconTint = Color(0xFFFBC02D)
            )

            SettingsOptionRow(
                icon = Icons.Default.Description,
                title = "Conditions d'utilisation",
                subtitle = "Consulter",
                color = Color(0xFFFFF3E0),
                iconTint = Color(0xFFFF9800)
            )
        }
    }
}

@Composable
fun AppInfoCard() {
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFF3E2522),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    "Application",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2522)
                )
            }

            HorizontalDivider(color = Color(0xFFFFE0B2))

            SettingsOptionRow(
                icon = Icons.Default.AppSettingsAlt,
                title = "Version",
                subtitle = "1.2.0",
                color = Color(0xFFE3F2FD),
                iconTint = Color(0xFF2196F3)
            )

            SettingsOptionRow(
                icon = Icons.Default.Update,
                title = "Mises à jour",
                subtitle = "À jour",
                color = Color(0xFFE8F5E9),
                iconTint = Color(0xFF4CAF50)
            )

            SettingsActionRow(
                icon = Icons.Default.Share,
                title = "Partager l'application",
                subtitle = "Inviter des amis",
                color = Color(0xFFF3E5F5),
                iconTint = Color(0xFF9C27B0)
            )
        }
    }
}

@Composable
fun ResetButton() {
    Button(
        onClick = { /* TODO */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFEBEE)
        )
    ) {
        Icon(
            Icons.Default.RestartAlt,
            contentDescription = null,
            tint = Color(0xFFD32F2F),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Réinitialiser les paramètres",
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFD32F2F)
        )
    }
}

@Composable
fun SettingsSwitchRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    color: Color,
    iconTint: Color,
    enabled: Boolean = true
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) Color(0xFFFFF8F0) else Color(0xFFF5F5F5)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                        .background(if (enabled) color else Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = if (enabled) iconTint else Color(0xFF9E9E9E),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (enabled) Color(0xFF3E2522) else Color(0xFF9E9E9E)
                    )
                    Text(
                        subtitle,
                        fontSize = 12.sp,
                        color = if (enabled) Color(0xFF8C6E63) else Color(0xFFBDBDBD)
                    )
                }
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled
            )
        }
    }
}

@Composable
fun SettingsOptionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    color: Color,
    iconTint: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8F0)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3E2522)
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
                tint = Color(0xFF8C6E63),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun SettingsActionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    color: Color,
    iconTint: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8F0)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3E2522)
                )
                Text(
                    subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF8C6E63)
                )
            }

            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color(0xFF8C6E63),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}