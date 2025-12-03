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
class AccountCreateViewModel @Inject constructor(val repository: AccountRepository): ViewModel() {

    var uiState by mutableStateOf(AccountCreateUiState())

    fun createAccount(payload: Account){
       viewModelScope.launch {
           uiState = uiState.copy(isLoading = true)
           try {
              val result = repository.createAccount(payload)
               uiState = uiState.copy(isLoading = false, isCreated = true)
           } catch (ex: Exception){
               uiState = uiState.copy(isLoading = false, isCreated = false, error=ex.toString())
           }
       }
    }

}

data class AccountCreateUiState(
    val isLoading: Boolean = false,
    val isCreated: Boolean = false,
    val error: String? = null
)