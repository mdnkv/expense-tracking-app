package dev.mednikov.expensetracking.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.mednikov.expensetracking.ui.screens.categories.CategoriesListScreen
import dev.mednikov.expensetracking.ui.screens.categories.CategoryCreateScreen
import dev.mednikov.expensetracking.ui.screens.categories.CategoryDetailScreen

fun NavGraphBuilder.categoryNavigation(navController: NavController) {
    navigation(
        startDestination = "categories_list",
        route = HomeNavItem.CategoriesItem.route
    ) {
        composable("categories_list") {
            CategoriesListScreen(navController)
        }
        composable (NavScreens.CategoryCreateScreen.name) {
            CategoryCreateScreen(navController)
        }
        composable ("${NavScreens.CategoryDetailScreen.name}/{categoryId}", arguments = listOf(
            navArgument(name = "categoryId") {
                type = NavType.StringType
            }
        )) { backStackEntry ->
            backStackEntry.arguments?.getString("categoryId").let {
                CategoryDetailScreen(navController, categoryId = it.toString())
            }

        }
    }
}