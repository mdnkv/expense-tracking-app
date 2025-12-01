package dev.mednikov.expensetracking.ui.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.ConfirmDialogComponent
import dev.mednikov.expensetracking.ui.shared.InputFieldComponent
import dev.mednikov.expensetracking.viewmodel.categories.CategoryUpdateViewModel

@Composable
fun CategoryUpdateScreen(navController: NavController, categoryId: String, viewModel: CategoryUpdateViewModel = hiltViewModel()){
    val confirmDialogState = rememberSaveable { mutableStateOf(false) }
    val nameState = rememberSaveable { mutableStateOf("") }

    val uiState = viewModel.uiState

    LaunchedEffect(categoryId) {
        viewModel.getCategoryById(categoryId)
    }

    LaunchedEffect(uiState.isUpdated) {
        if (uiState.isUpdated) {
            navController.popBackStack()
        }
    }

    LaunchedEffect(uiState.isLoading) {
        if (!uiState.isLoading && uiState.isExist) {
            nameState.value = uiState.category!!.name
        }
    }

    ConfirmDialogComponent (
        dialogState = confirmDialogState,
        label = "Do you want to quit without saving?",
        confirmAction = {
            navController.popBackStack()
        },
        dismissAction = {}
    )
    Scaffold(
        topBar = {
            ApplicationToolBarComponent(
                title = "Update category",
                onAction = {
                    if (nameState.value.isNotEmpty()) {
                        // Create
                        val payload = uiState.category!!.copy(name = nameState.value)
                        viewModel.updateCategory(payload)
                    }
                },
                actionType = AppBarActions.CONFIRM,
                onBack = {confirmDialogState.value = true},
                showBackButton = true
            )
        }
    ) { paddingValues ->
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            InputFieldComponent(
                label = "Name",
                state = nameState
            )
        }
    }


}
