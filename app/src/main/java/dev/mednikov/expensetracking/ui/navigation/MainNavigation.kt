package dev.mednikov.expensetracking.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.mednikov.expensetracking.ui.screens.authentication.LoginScreen
import dev.mednikov.expensetracking.ui.screens.authentication.SignupScreen
import dev.mednikov.expensetracking.ui.screens.home.HomeScreen
import dev.mednikov.expensetracking.viewmodel.authentication.AuthViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val isAuthenticated by authViewModel.isAuthenticatedState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) NavScreens.HomeScreen.name else NavScreens.LoginScreen.name
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