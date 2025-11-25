package dev.mednikov.expensetracking.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.mednikov.expensetracking.ui.screens.dashboard.DashboardScreen

fun NavGraphBuilder.dashboardNavigation(navController: NavController) {
    navigation(
        startDestination = "dashboard",
        route = HomeNavItem.DashboardItem.route
    ) {
        composable("dashboard") {
            DashboardScreen(navController)
        }
    }
}