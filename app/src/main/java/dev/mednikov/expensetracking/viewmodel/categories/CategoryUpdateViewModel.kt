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
class CategoryUpdateViewModel @Inject constructor (private val repository: CategoryRepository): ViewModel() {
    var uiState by mutableStateOf(UpdateCategoryUiState())
        private set

    fun getCategoryById(categoryId: String){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try{
                val result = repository.getCategoryById(categoryId)
                if (result != null) {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = null,
                        isExist = true,
                        category = result
                    )
                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = null,
                        isExist = false,
                        category = result
                    )
                }
            } catch (ex: Exception){
                uiState = uiState.copy(
                    isLoading = false,
                    error = ex.message.toString()
                )
            }
        }
    }

    fun updateCategory(payload: Category) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, isUpdated = false)
            try{
                val result = repository.updateCategory(payload)
                uiState = uiState.copy(
                    isLoading = false,
                    error = null,
                    isExist = true,
                    category = result,
                    isUpdated =  true
                )
            } catch (ex: Exception){
                uiState = uiState.copy(
                    isLoading = false,
                    isUpdated = false,
                    error = ex.message.toString()
                )
            }
        }
    }
}

data class UpdateCategoryUiState(
    val isLoading: Boolean = false,
    val isUpdated: Boolean = false,
    val isExist: Boolean = false,
    val category: Category? = null,
    val error: String? = null
)