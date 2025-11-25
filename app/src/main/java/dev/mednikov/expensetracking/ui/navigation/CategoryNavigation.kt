package dev.mednikov.expensetracking.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.mednikov.expensetracking.ui.screens.categories.CategoriesListScreen

fun NavGraphBuilder.categoryNavigation(navController: NavController) {
    navigation(
        startDestination = "categories_list",
        route = HomeNavItem.CategoriesItem.route
    ) {
        composable("categories_list") {
            CategoriesListScreen(navController)
        }
    }
}