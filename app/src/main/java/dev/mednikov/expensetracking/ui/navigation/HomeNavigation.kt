package dev.mednikov.expensetracking.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun HomeNavigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = HomeNavItem.DashboardItem.route
    ) {
        dashboardNavigation(navController)
        operationNavigation(navController)
        accountNavigation(navController)
        categoryNavigation(navController)
    }
}

sealed class HomeNavItem(
    val route: String,
    val label: String
) {
    object DashboardItem: HomeNavItem(
        NavScreens.DashboardScreen.name,
        "Dashboard"
    )
    object OperationsItem: HomeNavItem(
        NavScreens.OperationsListScreen.name,
        "Operations"
    )
    object AccountsItem: HomeNavItem(
        NavScreens.AccountsListScreen.name,
        "Accounts"
    )
    object CategoriesItem: HomeNavItem(
        NavScreens.CategoriesListScreen.name,
        "Categories"
    )
}

val bottomNavItems = listOf(
    HomeNavItem.DashboardItem,
    HomeNavItem.OperationsItem,
    HomeNavItem.AccountsItem,
    HomeNavItem.CategoriesItem
)
