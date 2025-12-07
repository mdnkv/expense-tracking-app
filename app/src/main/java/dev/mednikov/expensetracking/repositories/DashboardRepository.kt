package dev.mednikov.expensetracking.repositories

import dev.mednikov.expensetracking.api.DashboardApi
import dev.mednikov.expensetracking.models.IncomeExpenseData
import dev.mednikov.expensetracking.storage.TokenStorage
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DashboardRepository  @Inject constructor(val api: DashboardApi, val storage: TokenStorage) {

    suspend fun getIncomeExpenseData(): IncomeExpenseData {
        val userId = storage.userIdFlow.firstOrNull().toString()
        return api.getIncomeExpenseData(userId)
    }
}