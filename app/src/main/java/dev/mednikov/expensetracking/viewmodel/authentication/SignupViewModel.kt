package dev.mednikov.expensetracking.viewmodel.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mednikov.expensetracking.models.SignupRequest
import dev.mednikov.expensetracking.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private val _uiState = MutableStateFlow<SignupState>(SignupState.Idle)
    val uiState = _uiState.asStateFlow()

    fun signup(payload: SignupRequest) {
        viewModelScope.launch {
            // Set loading state
            _uiState.value = SignupState.Loading

            // Call repo
            val result = userRepository.signup(payload)

            // Check result
            _uiState.value = if (result.isSuccess) {
                SignupState.Success
            } else {
                SignupState.Error(result.exceptionOrNull()?.message ?: "Error")
            }

        }
    }

}

sealed class SignupState {
    object Idle : SignupState()
    object Loading : SignupState()
    object Success : SignupState()
    data class Error(val message: String) : SignupState()
}