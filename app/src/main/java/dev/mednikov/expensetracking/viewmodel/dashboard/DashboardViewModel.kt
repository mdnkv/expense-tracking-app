package dev.mednikov.expensetracking.viewmodel.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mednikov.expensetracking.models.IncomeExpenseData
import dev.mednikov.expensetracking.repositories.DashboardRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(val dashboardRepository: DashboardRepository): ViewModel() {

    var uiState by mutableStateOf(DashboardUiState())
        private set

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val result = dashboardRepository.getIncomeExpenseData()
                uiState = uiState.copy(isLoading = false, incomeExpenseData = result)
            } catch (ex: Exception){
                uiState = uiState.copy(isLoading = false, error = ex.message.toString())
            }
        }
    }

}

data class DashboardUiState(
    val isLoading: Boolean = false,
    val incomeExpenseData: IncomeExpenseData? = null,
    val error: String? = null
)