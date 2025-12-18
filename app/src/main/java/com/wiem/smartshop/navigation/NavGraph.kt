package com.wiem.smartshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.wiem.smartshop.auth.LoginScreen
import com.wiem.smartshop.auth.SignupScreen
import com.wiem.smartshop.home.HomeScreen
import com.wiem.smartshop.product.ProductListScreen
import com.wiem.smartshop.statistics.StatisticsScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object Products : Screen("products")
    object Statistics : Screen("statistics")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val startDestination =
        if (FirebaseAuth.getInstance().currentUser != null)
            Screen.Home.route
        else
            Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onSignupClick = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onLogout = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onGoToProducts = {
                    navController.navigate(Screen.Products.route)
                },
                onGoToStatistics = {
                    navController.navigate(Screen.Statistics.route)
                }
            )
        }

        composable(Screen.Products.route) {
            ProductListScreen()
        }

        composable(Screen.Statistics.route) {
            StatisticsScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}