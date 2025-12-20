package com.wiem.smartshop.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SmartShopColorScheme = lightColorScheme(
    primary = SmartShopColors.DarkBrown,
    secondary = SmartShopColors.MediumBrown,
    background = SmartShopColors.White,
    surface = SmartShopColors.Cream,
    error = SmartShopColors.Error,

    onPrimary = SmartShopColors.White,
    onSecondary = SmartShopColors.White,
    onBackground = SmartShopColors.DarkBrown,
    onSurface = SmartShopColors.DarkBrown
)

@Composable
fun SmartShopTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SmartShopColorScheme,
        content = content
    )
}
