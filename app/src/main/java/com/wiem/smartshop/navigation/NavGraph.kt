package com.wiem.smartshop.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.wiem.smartshop.auth.LoginScreen
import com.wiem.smartshop.auth.SignupScreen
import com.wiem.smartshop.home.HomeScreen
import com.wiem.smartshop.notifications.NotificationsScreen
import com.wiem.smartshop.onboarding.OnboardingScreen
import com.wiem.smartshop.product.ProductDetailScreen
import com.wiem.smartshop.product.ProductListScreen
import com.wiem.smartshop.product.ProductViewModel
import com.wiem.smartshop.profile.ProfileScreen
import com.wiem.smartshop.settings.SettingsScreen
import com.wiem.smartshop.splash.SplashScreen
import com.wiem.smartshop.statistics.StatisticsScreen
import com.wiem.smartshop.ui.products.EditProductDialog

// ==================== ROUTES ====================
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Dashboard : Screen("dashboard")
    object Products : Screen("products")
    object Statistics : Screen("statistics")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object Notifications : Screen("notifications")

    // MODIFICATION : Support pour String et Int
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Any) = "product_detail/$productId"
    }
}

@Composable
fun NavGraph(
    hasSeenOnboarding: Boolean,
    onOnboardingFinished: () -> Unit
) {
    val navController = rememberNavController()
    val currentUser = FirebaseAuth.getInstance().currentUser

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        // ==================== SPLASH ====================
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    val destination = when {
                        !hasSeenOnboarding -> Screen.Onboarding.route
                        currentUser != null -> Screen.Dashboard.route
                        else -> Screen.Login.route
                    }

                    navController.navigate(destination) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // ==================== ONBOARDING ====================
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinished = {
                    onOnboardingFinished()
                    val destination =
                        if (currentUser != null) Screen.Dashboard.route
                        else Screen.Login.route

                    navController.navigate(destination) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // ==================== AUTH ====================
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onSignupClick = { navController.navigate(Screen.Signup.route) }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        // ==================== DASHBOARD ====================
        composable(Screen.Dashboard.route) {
            val viewModel: ProductViewModel = viewModel()
            HomeScreen(
                productViewModel = viewModel,
                onGoToProducts = { navController.navigate(Screen.Products.route) },
                onGoToStatistics = { navController.navigate(Screen.Statistics.route) },
                onGoToNotifications = { navController.navigate(Screen.Notifications.route) },
                onGoToProfile = { navController.navigate(Screen.Profile.route) },
                onGoToSettings = { navController.navigate(Screen.Settings.route) },
                onLogout = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }

        // ==================== PRODUCTS ====================
        composable(Screen.Products.route) {
            val viewModel: ProductViewModel = viewModel()
            ProductListScreen(
                productViewModel = viewModel,
                onBackClick = { navController.navigateUp() },
                onProductClick = { product ->
                    // AJOUT DE LOGS pour déboguer
                    println("🔍 Navigation vers produit ID: ${product.id}")
                    navController.navigate(Screen.ProductDetail.createRoute(product.id))
                }
            )
        }

        // ==================== PRODUCT DETAIL ====================
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")

            // AJOUT DE LOGS pour déboguer
            println("🔍 ProductDetail - ID reçu: $productId")

            val viewModel: ProductViewModel = viewModel()
            val products by viewModel.products.collectAsState(emptyList())

            // AJOUT DE LOGS
            println("🔍 Nombre de produits: ${products.size}")
            products.forEach { println("🔍 Produit disponible: ${it.id} - ${it.name}") }

            val product = products.find { it.id == productId }

            println("🔍 Produit trouvé: ${product?.name ?: "NULL"}")

            var showEditDialog by remember { mutableStateOf(false) }

            if (product != null) {
                ProductDetailScreen(
                    product = product,
                    onBackClick = { navController.navigateUp() },
                    onEdit = { showEditDialog = true },
                    onDelete = {
                        viewModel.deleteProduct(product)
                        navController.navigateUp()
                    }
                )

                if (showEditDialog) {
                    EditProductDialog(
                        product = product,
                        onDismiss = { showEditDialog = false },
                        onConfirm = { updated ->
                            viewModel.updateProduct(updated)
                            showEditDialog = false
                        }
                    )
                }
            } else {
                // Afficher un message d'erreur au lieu de naviguer en arrière immédiatement
                LaunchedEffect(Unit) {
                    println("⚠️ ERREUR: Produit non trouvé avec l'ID: $productId")
                    kotlinx.coroutines.delay(100) // Petit délai pour laisser les logs s'afficher
                    navController.navigateUp()
                }
            }
        }

        // ==================== OTHERS ====================
        composable(Screen.Statistics.route) {
            StatisticsScreen(onBackClick = { navController.navigateUp() })
        }

        composable(Screen.Profile.route) {
            ProfileScreen(onBackClick = { navController.navigateUp() })
        }

        composable(Screen.Settings.route) {
            SettingsScreen(onBackClick = { navController.navigateUp() })
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(onBackClick = { navController.navigateUp() })
        }
    }
}