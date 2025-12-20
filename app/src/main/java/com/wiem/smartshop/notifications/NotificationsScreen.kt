package com.wiem.smartshop.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

data class Notification(
    val id: String = UUID.randomUUID().toString(),
    val type: NotificationType,
    val title: String,
    val message: String,
    val timestamp: Date = Date(),
    val isRead: Boolean = false
)

enum class NotificationType {
    LOW_STOCK, NEW_PRODUCT, SYSTEM, ALERT, INFO
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onBackClick: () -> Unit) {
    var notifications by remember {
        mutableStateOf(
            listOf(
                Notification(
                    type = NotificationType.LOW_STOCK,
                    title = "Stock faible",
                    message = "Le produit 'Laptop HP' a un stock critique (5 unités)",
                    timestamp = Date(System.currentTimeMillis() - 3600000),
                    isRead = false
                ),
                Notification(
                    type = NotificationType.NEW_PRODUCT,
                    title = "Nouveau produit",
                    message = "Samsung Galaxy S24 ajouté au catalogue",
                    timestamp = Date(System.currentTimeMillis() - 7200000),
                    isRead = false
                ),
                Notification(
                    type = NotificationType.ALERT,
                    title = "Alerte de prix",
                    message = "Prix de iPhone 15 Pro modifié",
                    timestamp = Date(System.currentTimeMillis() - 86400000),
                    isRead = true
                )
            )
        )
    }

    val unreadCount = notifications.count { !it.isRead }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            tint = Color(0xFF3E2522),
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            "Notifications",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color(0xFF3E2522)
                        )
                        if (unreadCount > 0) {
                            Badge(
                                containerColor = Color(0xFFD32F2F),
                                contentColor = Color.White
                            ) {
                                Text("$unreadCount")
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Retour", tint = Color(0xFF3E2522))
                    }
                },
                actions = {
                    if (unreadCount > 0) {
                        TextButton(
                            onClick = {
                                notifications = notifications.map { it.copy(isRead = true) }
                            }
                        ) {
                            Text("Tout marquer lu", color = Color(0xFF2196F3))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFF2DF)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF2DF))
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
                                )
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier.size(60.dp).clip(CircleShape)
                                        .background(Color(0xFFE3F2FD)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.NotificationsActive,
                                        null,
                                        tint = Color(0xFF2196F3),
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Text(
                                    "${notifications.size}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF3E2522)
                                )
                                Text("Total", fontSize = 12.sp, color = Color(0xFF8C6E63))
                            }

                            Box(
                                modifier = Modifier.width(1.dp).height(60.dp)
                                    .background(Color(0xFF90CAF9))
                            )

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier.size(60.dp).clip(CircleShape)
                                        .background(Color(0xFFFFCDD2)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.FiberNew,
                                        null,
                                        tint = Color(0xFFD32F2F),
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Text(
                                    "$unreadCount",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF3E2522)
                                )
                                Text("Non lues", fontSize = 12.sp, color = Color(0xFF8C6E63))
                            }
                        }
                    }
                }
            }

            items(notifications, key = { it.id }) { notification ->
                val (bgColor, iconColor, icon) = when (notification.type) {
                    NotificationType.LOW_STOCK -> Triple(
                        Color(0xFFFFEBEE),
                        Color(0xFFD32F2F),
                        Icons.Default.Warning
                    )
                    NotificationType.NEW_PRODUCT -> Triple(
                        Color(0xFFE8F5E9),
                        Color(0xFF4CAF50),
                        Icons.Default.AddCircle
                    )
                    NotificationType.ALERT -> Triple(
                        Color(0xFFFFF3E0),
                        Color(0xFFFF9800),
                        Icons.Default.ErrorOutline
                    )
                    NotificationType.SYSTEM -> Triple(
                        Color(0xFFE3F2FD),
                        Color(0xFF2196F3),
                        Icons.Default.Settings
                    )
                    NotificationType.INFO -> Triple(
                        Color(0xFFF3E5F5),
                        Color(0xFF9C27B0),
                        Icons.Default.Info
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (notification.isRead) Color.White else Color(
                            0xFFFFFBF0
                        )
                    ),
                    elevation = CardDefaults.cardElevation(if (notification.isRead) 4.dp else 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(56.dp).clip(CircleShape)
                                .background(bgColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(icon, null, tint = iconColor, modifier = Modifier.size(28.dp))
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    notification.title,
                                    fontSize = 16.sp,
                                    fontWeight = if (notification.isRead) FontWeight.SemiBold else FontWeight.Bold,
                                    color = Color(0xFF3E2522)
                                )
                                if (!notification.isRead) {
                                    Box(
                                        modifier = Modifier.size(10.dp).clip(CircleShape)
                                            .background(Color(0xFF2196F3))
                                    )
                                }
                            }
                            Text(
                                notification.message,
                                fontSize = 14.sp,
                                color = Color(0xFF8C6E63)
                            )
                            Text(
                                formatTimestamp(notification.timestamp),
                                fontSize = 12.sp,
                                color = Color(0xFF8C6E63)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatTimestamp(date: Date): String {
    val diff = System.currentTimeMillis() - date.time
    return when {
        diff < 60000 -> "À l'instant"
        diff < 3600000 -> "${diff / 60000} min"
        diff < 86400000 -> "${diff / 3600000}h"
        else -> "${diff / 86400000}j"
    }
}