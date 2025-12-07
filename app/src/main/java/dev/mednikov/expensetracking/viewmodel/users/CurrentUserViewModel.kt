package dev.mednikov.expensetracking.viewmodel.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mednikov.expensetracking.models.User
import dev.mednikov.expensetracking.repositories.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentUserViewModel @Inject constructor(val userRepository: UserRepository): ViewModel(){

    var uiState by mutableStateOf(CurrentUserUiState())
        private set

    init {
        getCurrentUser()
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val result = userRepository.getCurrentUser()
                uiState = uiState.copy(isLoading = false, user = result)
            } catch (ex: Exception){
                uiState = uiState.copy(isLoading = false, error = ex.message.toString())
            }
        }
    }

    fun updateUser(payload: User){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, isUpdated = false)
            try {
                val result = userRepository.updateUser(payload)
                uiState = uiState.copy(isLoading = false, user = result, isUpdated = true)
            } catch (ex: Exception){
                uiState = uiState.copy(isLoading = false, error = ex.message.toString())
            }
        }
    }

}

data class CurrentUserUiState(
    val isLoading: Boolean = false,
    val isUpdated: Boolean = false,
    val user: User? = null,
    val error: String? = null
)