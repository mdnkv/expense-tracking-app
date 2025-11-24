package dev.mednikov.expensetracking.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.mednikov.expensetracking.ui.screens.authentication.LoginScreen
import dev.mednikov.expensetracking.ui.screens.authentication.SignupScreen
import dev.mednikov.expensetracking.ui.screens.home.HomeScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavScreens.LoginScreen.name
    ) {
        composable (NavScreens.SignupScreen.name) {
            SignupScreen(navController)
        }
        composable (NavScreens.LoginScreen.name) {
            LoginScreen(navController)
        }
        composable (NavScreens.HomeScreen.name) {
            HomeScreen()
        }
    }
}