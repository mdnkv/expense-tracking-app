package dev.mednikov.expensetracking.viewmodel.operations

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
class OperationCreateViewModel @Inject constructor(
    val operationRepository: OperationRepository,
    val categoryRepository: CategoryRepository,
    val accountRepository: AccountRepository): ViewModel() {

    var uiState by mutableStateOf(OperationCreateUiState())
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

    fun createOperation(payload: Operation){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val result = operationRepository.createOperation(payload)
                uiState = uiState.copy(isLoading = false, isCreated = true, error = null)
            } catch (ex: Exception){
                uiState = uiState.copy(isLoading = false, isCreated = false, error = ex.message.toString())
            }
        }

    }

}

data class OperationCreateUiState(
    val isLoading: Boolean = false,
    val isCreated: Boolean = false,
    val categories: List<Category> = emptyList(),
    val accounts: List<Account> = emptyList(),
    val error: String? = null
);