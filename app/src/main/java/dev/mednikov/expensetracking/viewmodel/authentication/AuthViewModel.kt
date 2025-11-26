package dev.mednikov.expensetracking.viewmodel.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mednikov.expensetracking.repositories.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    val isAuthenticatedState = authRepository.getLoggedIn()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            false
        )

    fun logout () {
        viewModelScope.launch{
            authRepository.logout()
        }
    }
}
