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
class OperationListViewModel @Inject constructor(private val operationRepository: OperationRepository): ViewModel() {
    var uiState by mutableStateOf(OperationListUiState())

    init {
        getOperations()
    }

    fun getOperations() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val result = operationRepository.getOperations()
                uiState = uiState.copy(
                    operations = result,
                    isLoading = false
                )
            } catch (ex: Exception){
                uiState = uiState.copy(
                    isLoading = false,
                    error = ex.message.toString()
                )
            }
        }
    }
}

data class OperationListUiState (
    val operations: List<Operation> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)