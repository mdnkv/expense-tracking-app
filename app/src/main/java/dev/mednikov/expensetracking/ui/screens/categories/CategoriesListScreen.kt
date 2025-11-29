package dev.mednikov.expensetracking.ui.screens.categories

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.Category
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.CreateActionButtonComponent
import dev.mednikov.expensetracking.ui.shared.EmptyListPlaceholderComponent
import dev.mednikov.expensetracking.ui.shared.LoadingPlaceholderComponent
import dev.mednikov.expensetracking.viewmodel.categories.CategoriesListViewModel

@Composable
fun CategoriesListScreen(navController: NavController, viewModel: CategoriesListViewModel = hiltViewModel()){

    val uiState = viewModel.categoryState

    when {
        uiState.loading -> LoadingPlaceholderComponent()
        uiState.categories.isEmpty() -> EmptyListPlaceholderComponent(comment = "No categories found")
        else -> CategoryListComponent(navController, uiState.categories)
    }

}

@Composable
fun CategoryListComponent (navController: NavController, categories: List<Category>) {
    Scaffold (
        topBar = {
            ApplicationToolBarComponent(
                title = "Categories",
                onAction = {},
                actionType = AppBarActions.SEARCH
            )
        },
        floatingActionButton = { CreateActionButtonComponent(onAction = {
            navController.navigate(NavScreens.CategoryCreateScreen.name)
        }) }
    ) {paddingValues ->
        LazyColumn (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 10.dp)
        ) {
            items(categories) {category ->
                CategoryItemComponent(category, navController)
            }
        }
    }
}

@Composable
fun CategoryItemComponent(category: Category, navController: NavController) {
    Row (
        modifier = Modifier.fillMaxWidth().clickable{
            Log.d("CATEGORIESSCREEN", category.toString())
        }
    ) {
        Text(text = category.name, modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp))
    }
}
