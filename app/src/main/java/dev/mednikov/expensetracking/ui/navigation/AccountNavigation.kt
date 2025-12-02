package dev.mednikov.expensetracking.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.mednikov.expensetracking.ui.screens.accounts.AccountListScreen

fun NavGraphBuilder.accountNavigation(navController: NavController) {
    navigation(
        startDestination = "account_list",
        route = HomeNavItem.AccountsItem.route
    ) {
        composable("account_list") {
            AccountListScreen(navController)
        }
    }
}