package dev.mednikov.expensetracking.viewmodel.categories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mednikov.expensetracking.models.Category
import dev.mednikov.expensetracking.repositories.CategoryRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(private val repository: CategoryRepository): ViewModel() {

    var categoryState by mutableStateOf(CategoryUiState())

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            categoryState = categoryState.copy(loading = true)
            try {
                val result = repository.getCategories()
                categoryState = categoryState.copy(
                    loading = false,
                    categories = result,
                    error = null
                )
            } catch (ex: Exception) {
                categoryState = categoryState.copy(
                    loading = false,
                    error = ex.message.toString()
                )
            }
        }
    }


}

data class CategoryUiState(
    val loading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val error: String? = null
)