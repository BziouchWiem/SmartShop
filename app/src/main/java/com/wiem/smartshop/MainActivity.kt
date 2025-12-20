package com.wiem.smartshop

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.wiem.smartshop.navigation.NavGraph
import com.wiem.smartshop.ui.theme.SmartShopTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // SharedPreferences
        val prefs = getSharedPreferences("smartshop_prefs", Context.MODE_PRIVATE)
        val hasSeenOnboarding = prefs.getBoolean("has_seen_onboarding", false)

        setContent {
            SmartShopTheme {
                NavGraph(
                    hasSeenOnboarding = hasSeenOnboarding,
                    onOnboardingFinished = {
                        prefs.edit()
                            .putBoolean("has_seen_onboarding", true)
                            .apply()
                    }
                )
            }
        }
    }
}
