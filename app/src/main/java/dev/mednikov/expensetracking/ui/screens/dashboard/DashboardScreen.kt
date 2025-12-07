package dev.mednikov.expensetracking.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.IncomeExpenseData
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.LoadingPlaceholderComponent
import dev.mednikov.expensetracking.viewmodel.dashboard.DashboardViewModel

@Composable
fun DashboardScreen(navController: NavController, dashboardViewModel: DashboardViewModel = hiltViewModel()) {
    val uiState = dashboardViewModel.uiState
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect (Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            dashboardViewModel.loadDashboard()
        }
    }

    when {
        uiState.isLoading -> LoadingPlaceholderComponent()
        else -> DashboardComponent(navController = navController, incomeExpenseData = uiState.incomeExpenseData)
    }
}

@Composable
fun DashboardComponent(navController: NavController, incomeExpenseData: IncomeExpenseData?){
    Scaffold (
        topBar = {
            ApplicationToolBarComponent(
                title = "Dashboard",
                onAction = {navController.navigate(NavScreens.CurrentUserScreen.name)},
                actionType = AppBarActions.USER_ACCOUNT
            )
        }
    ) {paddingValues ->
        Column (
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            IncomeExpenseWidgetComponent(data = incomeExpenseData)
        }
    }
}