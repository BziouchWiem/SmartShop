package com.wiem.smartshop.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.wiem.smartshop.R      // ⛔️ IMPORTANT !!!

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {

    var start by remember { mutableStateOf(false) }
    var showSubtitle by remember { mutableStateOf(false) }

    val scale = animateFloatAsState(
        targetValue = if (start) 1.1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = "scale"
    )

    val alpha = animateFloatAsState(
        targetValue = if (start) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha"
    )

    LaunchedEffect(Unit) {
        start = true
        delay(1000)
        showSubtitle = true
        delay(2000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF3E2522),
                        Color(0xFF5D4037)
                    ),
                    center = Offset(500f, 500f),
                    radius = 800f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale.value)
        ) {

            // Cercle + Logo
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFB68A6A))
                    .alpha(alpha.value),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.handbag_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(110.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "LuxeBag",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.alpha(alpha.value)
            )

            if (showSubtitle) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Boutique Intelligente",
                    fontSize = 18.sp,
                    color = Color(0xFFFFE0B2),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.alpha(alpha.value)
                )
            }
        }
    }
}
