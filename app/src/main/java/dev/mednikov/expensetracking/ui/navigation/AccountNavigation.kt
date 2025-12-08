package dev.mednikov.expensetracking.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.mednikov.expensetracking.ui.screens.accounts.AccountCreateScreen
import dev.mednikov.expensetracking.ui.screens.accounts.AccountDetailScreen
import dev.mednikov.expensetracking.ui.screens.accounts.AccountListScreen
import dev.mednikov.expensetracking.ui.screens.accounts.AccountUpdateScreen

fun NavGraphBuilder.accountNavigation(navController: NavController) {
    navigation(
        startDestination = "account_list",
        route = HomeNavItem.AccountsItem.route
    ) {
        composable("account_list") {
            AccountListScreen(navController)
        }
        composable (NavScreens.AccountCreateScreen.name) {
            AccountCreateScreen(navController)
        }
        composable ("${NavScreens.AccountDetailScreen.name}/{accountId}", arguments = listOf(
            navArgument(name = "accountId") {
                type = NavType.StringType
            }
        )) { backStackEntry ->
            backStackEntry.arguments?.getString("accountId").let {
                AccountDetailScreen(navController, accountId = it.toString())
            }
        }
        composable ("${NavScreens.AccountUpdateScreen.name}/{accountId}", arguments = listOf(
            navArgument(name = "accountId") {
                type = NavType.StringType
            }
        )) { backStackEntry ->
            backStackEntry.arguments?.getString("accountId").let {
                AccountUpdateScreen(navController, accountId = it.toString())
            }
        }
    }
}