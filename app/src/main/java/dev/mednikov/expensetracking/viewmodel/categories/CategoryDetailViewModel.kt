package dev.mednikov.expensetracking.viewmodel.categories

import android.util.Log
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
class CategoryDetailViewModel @Inject constructor(private val repository: CategoryRepository): ViewModel() {
    var categoryUiState by mutableStateOf(DetailCategoryUiState())
        private set

    var deleteUiState by mutableStateOf(DeleteCategoryUiState())
        private set

    fun getCategoryById(id: String){
        viewModelScope.launch {
            categoryUiState = categoryUiState.copy(isLoading = true)
            try{
                val result = repository.getCategoryById(id)
                if (result != null) {
                    categoryUiState = categoryUiState.copy(
                        isLoading = false,
                        error = null,
                        isExist = true,
                        category = result
                    )
                } else {
                    categoryUiState = categoryUiState.copy(
                        isLoading = false,
                        error = null,
                        isExist = false,
                        category = result
                    )
                }
            } catch (ex: Exception){
                categoryUiState = categoryUiState.copy(
                    isLoading = false,
                    error = ex.message.toString()
                )
            }
        }
    }

    fun deleteCategory(id: String){
        viewModelScope.launch {
            deleteUiState = deleteUiState.copy(isLoading = true, isDeleted = false)
            try {
                repository.deleteCategory(id)
                deleteUiState = deleteUiState.copy(isLoading = false, isDeleted = true)
            } catch (ex: Exception) {
                deleteUiState = deleteUiState.copy(isLoading = false, isDeleted = false, error = ex.message.toString())
            }
        }
    }
}

data class DetailCategoryUiState(
    val isLoading: Boolean = false,
    val isExist: Boolean = false,
    val category: Category? = null,
    val error: String? = null
)

data class DeleteCategoryUiState(
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val error: String? = null
)