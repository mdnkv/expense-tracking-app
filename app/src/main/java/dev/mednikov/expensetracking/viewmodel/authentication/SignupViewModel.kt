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

    private val _signupState = MutableStateFlow(SignupUiState())
    val signupState = _signupState.asStateFlow()

    fun signup(payload: SignupRequest) {
        viewModelScope.launch {
            _signupState.value = SignupUiState(isLoading = true)

            val result = userRepository.signup(payload)

            _signupState.value = if (result.isSuccess) {
                SignupUiState(success = true)
            } else {
                SignupUiState(error = result.exceptionOrNull()?.message)
            }
        }
    }

}

data class SignupUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)
