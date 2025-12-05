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
class AccountUpdateViewModel @Inject constructor(val repository: AccountRepository): ViewModel() {
    var uiState by mutableStateOf(AccountUpdateUiState())
        private set

    fun getAccountById(id: String){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try{
                val result = repository.getAccountById(id)
                if (result != null) {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = null,
                        isExist = true,
                        account = result
                    )
                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = null,
                        isExist = false,
                        account = result
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

    fun updateAccount(payload: Account){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, isUpdated = false)
            try {
                val result = repository.updateAccount(payload)
                uiState = uiState.copy(
                    isUpdated = true,
                    isLoading = false,
                    account = result
                )

            } catch (ex: Exception){
                uiState = uiState.copy(
                    isLoading = false,
                    isUpdated = true,
                    error = ex.message.toString()
                )
            }
        }
    }
}

data class AccountUpdateUiState (
    val isLoading: Boolean = false,
    val isUpdated: Boolean = false,
    val isExist: Boolean = false,
    val account: Account? = null,
    val error: String? = null
)