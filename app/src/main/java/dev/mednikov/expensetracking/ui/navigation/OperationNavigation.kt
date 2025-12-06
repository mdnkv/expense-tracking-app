package dev.mednikov.expensetracking.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.mednikov.expensetracking.ui.screens.operations.OperationListScreen

fun NavGraphBuilder.operationNavigation(navController: NavController) {
    navigation(
        startDestination = "operations_list",
        route = HomeNavItem.OperationsItem.route
    ) {
        composable("operations_list") {
            OperationListScreen(navController)
        }
    }
}