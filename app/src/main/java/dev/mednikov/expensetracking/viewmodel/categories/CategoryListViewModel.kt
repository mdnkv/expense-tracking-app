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
class CategoryListViewModel @Inject constructor(private val repository: CategoryRepository): ViewModel() {

    var uiState by mutableStateOf(CategoryListUiState())

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            uiState = uiState.copy(loading = true)
            try {
                val result = repository.getCategories()
                uiState = uiState.copy(
                    loading = false,
                    categories = result,
                    error = null
                )
            } catch (ex: Exception) {
                uiState = uiState.copy(
                    loading = false,
                    error = ex.message.toString()
                )
            }
        }
    }


}

data class CategoryListUiState(
    val loading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val error: String? = null
)