package dev.mednikov.expensetracking.viewmodel.accounts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mednikov.expensetracking.models.Account
import dev.mednikov.expensetracking.repositories.AccountRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailViewModel @Inject constructor(val repository: AccountRepository): ViewModel() {
    var detailUiState by mutableStateOf(AccountDetailUiState())
        private set

    var deleteUiState by mutableStateOf(AccountDeleteUiState())
        private set

    fun getAccountById(id: String){
        viewModelScope.launch {
            detailUiState = detailUiState.copy(isLoading = true)
            try{
                val result = repository.getAccountById(id)
                if (result != null) {
                    detailUiState = detailUiState.copy(
                        isLoading = false,
                        error = null,
                        isExist = true,
                        account = result
                    )
                } else {
                    detailUiState = detailUiState.copy(
                        isLoading = false,
                        error = null,
                        isExist = false,
                        account = result
                    )
                }
            } catch (ex: Exception){
                detailUiState = detailUiState.copy(
                    isLoading = false,
                    error = ex.message.toString()
                )
            }
        }
    }

    fun deleteAccount(id: String) {
        viewModelScope.launch {
            deleteUiState = deleteUiState.copy(isLoading = true, isDeleted = false)
            try {
                repository.deleteAccount(id)
                deleteUiState = deleteUiState.copy(isLoading = false, isDeleted = true)
            } catch (ex: Exception) {
                deleteUiState = deleteUiState.copy(isLoading = false, isDeleted = false, error = ex.message.toString())
            }
        }
    }
}

data class AccountDetailUiState(
    val isLoading: Boolean = false,
    val isExist: Boolean = false,
    val account: Account? = null,
    val error: String? = null
)

data class AccountDeleteUiState(
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val error: String? = null
)