package dev.mednikov.expensetracking.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.mednikov.expensetracking.ui.screens.operations.OperationCreateScreen
import dev.mednikov.expensetracking.ui.screens.operations.OperationDetailScreen
import dev.mednikov.expensetracking.ui.screens.operations.OperationListScreen

fun NavGraphBuilder.operationNavigation(navController: NavController) {
    navigation(
        startDestination = "operations_list",
        route = HomeNavItem.OperationsItem.route
    ) {
        composable("operations_list") {
            OperationListScreen(navController)
        }
        composable(NavScreens.OperationCreateScreen.name){
            OperationCreateScreen(navController)
        }
        composable ("${NavScreens.OperationDetailScreen.name}/{operationId}", arguments = listOf(
            navArgument(name = "operationId") {
                type = NavType.StringType
            }
        )) { backStackEntry ->
            backStackEntry.arguments?.getString("operationId").let {
                OperationDetailScreen(navController, operationId = it.toString())
            }

        }
    }
}