package dev.mednikov.expensetracking.viewmodel.operations

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mednikov.expensetracking.models.Account
import dev.mednikov.expensetracking.models.Category
import dev.mednikov.expensetracking.models.Operation
import dev.mednikov.expensetracking.repositories.AccountRepository
import dev.mednikov.expensetracking.repositories.CategoryRepository
import dev.mednikov.expensetracking.repositories.OperationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperationUpdateViewModel @Inject constructor(
    val operationRepository: OperationRepository,
    val categoryRepository: CategoryRepository,
    val accountRepository: AccountRepository
): ViewModel() {
    var uiState by mutableStateOf(OperationUpdateUiState())
        private set

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            try {
                val accounts = accountRepository.getAccounts()
                val categories = categoryRepository.getCategories()
                uiState = uiState.copy(accounts = accounts, categories = categories)
            } catch (ex: Exception){

            }
        }

    }

    fun getOperationById(id: String){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try{
                val result = operationRepository.getOperation(id)
                uiState = uiState.copy(
                    isLoading = false,
                    error = null,
                    isExist = result != null,
                    operation = result
                )

            } catch (ex: Exception){
                uiState = uiState.copy(
                    isLoading = false,
                    error = ex.message.toString()
                )
            }
        }
    }

    fun updateOperation(payload: Operation){
        Log.d("OPERATIONUPDATE", payload.toString())
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, isUpdated = false)
            try {
                val result = operationRepository.updateOperation(payload)
                uiState = uiState.copy(
                    isUpdated = true,
                    isLoading = false,
                    operation = result
                )

            } catch (ex: Exception){
                Log.d("OPERATIONUPDATE", ex.message.toString())
                uiState = uiState.copy(
                    isLoading = false,
                    isUpdated = true,
                    error = ex.message.toString()
                )
            }
        }
    }
}

data class OperationUpdateUiState (
    val isLoading: Boolean = false,
    val isUpdated: Boolean = false,
    val isExist: Boolean = false,
    val operation: Operation? = null,
    val categories: List<Category> = emptyList(),
    val accounts: List<Account> = emptyList(),
    val error: String? = null
)