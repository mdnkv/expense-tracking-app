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
class CategoryCreateViewModel @Inject constructor(private val repository: CategoryRepository) : ViewModel() {

    var uiState by mutableStateOf(CreateCategoryUiState())
        private set

    fun createCategory(payload: Category) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            try {
                repository.createCategory(payload)
                uiState = uiState.copy(
                    isLoading = false,
                    created = true,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun resetCreated() {
        uiState = uiState.copy(created = false)
    }
}

data class CreateCategoryUiState(
    val isLoading: Boolean = false,
    val created: Boolean = false,
    val error: String? = null
)