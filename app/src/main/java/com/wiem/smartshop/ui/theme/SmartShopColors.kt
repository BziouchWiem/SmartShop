// SmartShopColors.kt - Version complète
package com.wiem.smartshop.ui.theme

import androidx.compose.ui.graphics.Color

// Palette professionnelle SmartShop + couleurs "cute"
object SmartShopColors {
    // Couleurs principales
    val DarkBrown = Color(0xFF3E2522)      // #3E2522
    val MediumBrown = Color(0xFF8C6E63)    // #8C6E63
    val LightBrown = Color(0xFFD3A376)     // #D3A376
    val Cream = Color(0xFFFFE0B2)          // #FFE0B2
    val White = Color(0xFFFFF2DF)          // #FFF2DF

    // Nouveautés - Palette "cute"
    val PinkPastel = Color(0xFFFFB6C1)     // Rose pastel cute
    val MintGreen = Color(0xFF98FB98)      // Vert menthe
    val Lavender = Color(0xFFE6E6FA)       // Lavande
    val Peach = Color(0xFFFFDAB9)          // Pêche
    val BabyBlue = Color(0xFF89CFF0)       // Bleu bébé
    val LightYellow = Color(0xFFFFFFE0)    // Jaune clair

    // Catégories couleurs
    val LuxuryColor = Color(0xFFFFD700)    // Or pour Luxury
    val CasualColor = Color(0xFF87CEEB)    // Bleu ciel pour Casual
    val PartyColor = Color(0xFFFF69B4)     // Rose vif pour Party
    val OfficeColor = Color(0xFF98FB98)    // Vert menthe pour Office

    // Couleurs fonctionnelles
    val Success = Color(0xFF4CAF50)
    val Warning = Color(0xFFFF9800)
    val Error = Color(0xFFE53935)
    val Info = Color(0xFF2196F3)

    // Dégradés
    val GradientStart = DarkBrown
    val GradientEnd = MediumBrown

    // Nouveaux dégradés "cute"
    val GradientPink = listOf(Color(0xFFFFB6C1), Color(0xFFFFC0CB))
    val GradientBlue = listOf(Color(0xFF89CFF0), Color(0xFF87CEEB))
    val GradientPurple = listOf(Color(0xFFE6E6FA), Color(0xFFD8BFD8))
    val GradientGreen = listOf(Color(0xFF98FB98), Color(0xFF90EE90))
}