package dev.mednikov.expensetracking.ui.screens.operations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.Operation
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.CreateActionButtonComponent
import dev.mednikov.expensetracking.ui.shared.EmptyListPlaceholderComponent
import dev.mednikov.expensetracking.ui.shared.LoadingPlaceholderComponent
import dev.mednikov.expensetracking.viewmodel.operations.OperationListViewModel

@Composable
fun OperationListScreen(navController: NavController, viewModel: OperationListViewModel = hiltViewModel()){
    val uiState = viewModel.uiState

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect (Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getOperations()
        }
    }

    when {
        uiState.isLoading -> LoadingPlaceholderComponent()
        else -> OperationListComponent(uiState.operations, navController)
    }
}

@Composable
fun OperationListComponent(operations: List<Operation>, navController: NavController){
    Scaffold (
        topBar = {
            ApplicationToolBarComponent(
                title = "Operations",
                onAction = {},
                actionType = AppBarActions.SEARCH
            )
        },
        floatingActionButton = { CreateActionButtonComponent(onAction = {}) }
    ) {paddingValues ->
        if (operations.isEmpty()){
            EmptyListPlaceholderComponent(comment = "No operations found")
        } else {
            LazyColumn (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 10.dp)
            ) {
                items(operations) {operation ->
                    OperationItemComponent(operation, navController)
                }
            }
        }
    }

}
