package dev.mednikov.expensetracking.ui.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.Category
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.ConfirmDialogComponent
import dev.mednikov.expensetracking.ui.shared.EditActionButtonComponent
import dev.mednikov.expensetracking.ui.shared.ItemNotFoundComponent
import dev.mednikov.expensetracking.ui.shared.LoadingPlaceholderComponent
import dev.mednikov.expensetracking.viewmodel.categories.CategoryDetailViewModel

@Composable
fun CategoryDetailScreen(
    navController: NavController,
    categoryId: String,
    viewModel: CategoryDetailViewModel = hiltViewModel()
){

    val uiState = viewModel.categoryUiState
    val deleteState = viewModel.deleteUiState

    LaunchedEffect(categoryId) {
        viewModel.getCategoryById(categoryId)
    }

    LaunchedEffect(deleteState) {
        if (deleteState.isDeleted) {
            navController.popBackStack()
        }
    }

    when {
        uiState.isLoading -> LoadingPlaceholderComponent()
        !uiState.isExist -> ItemNotFoundComponent(navController)
        uiState.error != null -> ItemNotFoundComponent(navController)
        else -> CategoryDetailComponent(navController = navController, category = uiState.category!!) {
            viewModel.deleteCategory(categoryId)
        }
    }

}

@Composable
fun CategoryDetailComponent(navController: NavController, category: Category, onDelete: () -> Unit) {
    val deleteDialogState = rememberSaveable { mutableStateOf(false) }
    ConfirmDialogComponent (
        dialogState = deleteDialogState,
        label = "Do you want to delete this category?",
        confirmAction = {
            onDelete()
        },
        dismissAction = {}
    )
    Scaffold (
        topBar = {
            ApplicationToolBarComponent(
                title = "Category detail",
                onBack = {
                    navController.popBackStack()
                },
                onAction = {
                    deleteDialogState.value = true
                },
                showBackButton = true,
                actionType = AppBarActions.DELETE
            )
        },
        floatingActionButton = {
            EditActionButtonComponent {
                navController.navigate(NavScreens.CategoryUpdateScreen.name +"/${category.id!!}")
            }
        }
    ) { paddingValues ->
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            Text(text = category.name,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 16.dp),
                style = MaterialTheme.typography.bodyLarge)

        }
    }

}