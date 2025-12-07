package dev.mednikov.expensetracking.api

import dev.mednikov.expensetracking.models.IncomeExpenseData
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardApi {

    @GET("dashboard/income-expense/{userId}")
    suspend fun getIncomeExpenseData(@Path("userId") userId: String): IncomeExpenseData

}