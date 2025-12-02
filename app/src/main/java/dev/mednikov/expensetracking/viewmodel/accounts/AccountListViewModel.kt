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
class AccountListViewModel @Inject constructor(val repository: AccountRepository): ViewModel() {
    var uiState by mutableStateOf(AccountListUiState())

    init {
        getAccounts()
    }

    fun getAccounts() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val accounts = repository.getAccounts()
                uiState = uiState.copy(isLoading = false, accounts = accounts, error = null)
            } catch (ex: Exception) {
                uiState = uiState.copy(isLoading = false, error = ex.message.toString())
            }
        }
    }

}

data class AccountListUiState(
    val isLoading: Boolean = true,
    val accounts: List<Account> = emptyList(),
    val error: String? = null
)