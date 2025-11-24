package dev.mednikov.expensetracking.viewmodel.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mednikov.expensetracking.models.LoginRequest
import dev.mednikov.expensetracking.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {


    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState = _loginState.asStateFlow()

    fun login(payload: LoginRequest) {
        viewModelScope.launch {
            _loginState.value = LoginUiState(isLoading = true)

            val result = authRepository.login(payload)

            _loginState.value = if (result.isSuccess) {
                LoginUiState(success = true)
            } else {
                LoginUiState(error = result.exceptionOrNull()?.message)
            }
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)