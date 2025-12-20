package com.wiem.smartshop.profile

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
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBackClick: () -> Unit) {
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = Color(0xFF3E2522),
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            "Mon Profil",
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
            // CARTE PROFIL PRINCIPALE
            ProfileHeaderCard(
                displayName = currentUser?.displayName ?: "Utilisateur LuxeBag",
                email = currentUser?.email ?: "email@example.com"
            )

            // STATISTIQUES PERSONNELLES
            PersonalStatsCard()

            // OPTIONS DU PROFIL
            ProfileOptionsCard()

            // PRÉFÉRENCES
            PreferencesCard()
        }
    }
}

@Composable
fun ProfileHeaderCard(displayName: String, email: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFE0B2),
                            Color.White
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFF6B6B),
                                    Color(0xFFFFAB40)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Avatar",
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )
                }

                // Nom
                Text(
                    displayName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF3E2522)
                )

                // Email
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = Color(0xFF8C6E63),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        email,
                        fontSize = 14.sp,
                        color = Color(0xFF8C6E63)
                    )
                }

                // Badge membre
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE8F5E9)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Verified,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "Membre depuis 2025",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PersonalStatsCard() {
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
                        Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = Color(0xFF3E2522),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Vos Statistiques",
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
                PersonalStatItem(
                    icon = Icons.Default.AddBox,
                    value = "42",
                    label = "Produits\najoutés",
                    color = Color(0xFFE1BEE7),
                    iconTint = Color(0xFF9C27B0)
                )

                PersonalStatItem(
                    icon = Icons.Default.Notifications,
                    value = "156",
                    label = "Notifications\nreçues",
                    color = Color(0xFFFFCDD2),
                    iconTint = Color(0xFFD32F2F)
                )

                PersonalStatItem(
                    icon = Icons.Default.Timer,
                    value = "28j",
                    label = "Membre\nactif",
                    color = Color(0xFFC8E6C9),
                    iconTint = Color(0xFF388E3C)
                )
            }
        }
    }
}

@Composable
fun PersonalStatItem(
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
            fontSize = 11.sp,
            color = Color(0xFF8C6E63),
            lineHeight = 14.sp
        )
    }
}

@Composable
fun ProfileOptionsCard() {
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Gestion du Compte",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E2522),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            )

            ProfileOptionItem(
                icon = Icons.Default.Edit,
                title = "Modifier le profil",
                subtitle = "Changer votre nom, photo",
                color = Color(0xFFE3F2FD),
                iconTint = Color(0xFF2196F3)
            )

            ProfileOptionItem(
                icon = Icons.Default.Security,
                title = "Sécurité",
                subtitle = "Mot de passe, authentification",
                color = Color(0xFFFFF3E0),
                iconTint = Color(0xFFFF9800)
            )

            ProfileOptionItem(
                icon = Icons.Default.History,
                title = "Historique d'activité",
                subtitle = "Voir vos récentes actions",
                color = Color(0xFFF3E5F5),
                iconTint = Color(0xFF9C27B0)
            )
        }
    }
}

@Composable
fun PreferencesCard() {
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Assistance",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E2522),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            )

            ProfileOptionItem(
                icon = Icons.Default.Help,
                title = "Aide & Support",
                subtitle = "FAQ, contactez-nous",
                color = Color(0xFFE8F5E9),
                iconTint = Color(0xFF4CAF50)
            )

            ProfileOptionItem(
                icon = Icons.Default.Info,
                title = "À propos",
                subtitle = "Version 1.2.0",
                color = Color(0xFFFFF8E1),
                iconTint = Color(0xFFFBC02D)
            )
        }
    }
}

@Composable
fun ProfileOptionItem(
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
            horizontalArrangement = Arrangement.spacedBy(16.dp)
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