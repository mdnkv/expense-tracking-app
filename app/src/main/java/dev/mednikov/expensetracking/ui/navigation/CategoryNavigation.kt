package dev.mednikov.expensetracking.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.mednikov.expensetracking.ui.screens.categories.CategoryCreateScreen
import dev.mednikov.expensetracking.ui.screens.categories.CategoryDetailScreen
import dev.mednikov.expensetracking.ui.screens.categories.CategoryListScreen
import dev.mednikov.expensetracking.ui.screens.categories.CategoryUpdateScreen

fun NavGraphBuilder.categoryNavigation(navController: NavController) {
    navigation(
        startDestination = "categories_list",
        route = HomeNavItem.CategoriesItem.route
    ) {
        composable("categories_list") {
            CategoryListScreen(navController)
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
        composable ("${NavScreens.CategoryUpdateScreen.name}/{categoryId}", arguments = listOf(
            navArgument(name = "categoryId") {
                type = NavType.StringType
            }
        )) { backStackEntry ->
            backStackEntry.arguments?.getString("categoryId").let {
                CategoryUpdateScreen(navController, categoryId = it.toString())
            }

        }
    }
}