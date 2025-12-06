package dev.mednikov.expensetracking.viewmodel.operations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mednikov.expensetracking.models.Operation
import dev.mednikov.expensetracking.repositories.OperationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperationDetailViewModel @Inject constructor (val repository: OperationRepository): ViewModel(){
    var detailUiState by mutableStateOf(OperationDetailUiState())
        private set

    var deleteUiState by mutableStateOf(OperationDeleteUiState())
        private set

    fun getOperationById(id: String){
        viewModelScope.launch {
            detailUiState = detailUiState.copy(isLoading = true)
            try {
                val result = repository.getOperation(id)
                detailUiState = detailUiState.copy(
                    isLoading = false,
                    error = null,
                    isExist = result != null,
                    operation = result
                )
            } catch (ex: Exception){
                detailUiState = detailUiState.copy(isLoading = false, error = ex.message.toString())
            }
        }
    }

    fun deleteOperation(id: String){
        viewModelScope.launch {
            deleteUiState = deleteUiState.copy(isLoading = true, error = null)
            try {
                repository.deleteOperation(id)
                deleteUiState = deleteUiState.copy(isLoading = false, isDeleted = true)
            } catch (ex: Exception){
                deleteUiState = deleteUiState.copy(isLoading = false, isDeleted = false, error = ex.message.toString())
            }
        }
    }
}

data class OperationDetailUiState(
    val isLoading: Boolean = false,
    val isExist: Boolean = false,
    val operation: Operation? = null,
    val error: String? = null
)

data class OperationDeleteUiState(
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val error: String? = null
)