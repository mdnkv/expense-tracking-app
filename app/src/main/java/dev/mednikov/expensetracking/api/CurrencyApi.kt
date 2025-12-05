package dev.mednikov.expensetracking.api

import dev.mednikov.expensetracking.models.Currency
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {

    @GET("currencies/user/{userId}")
    suspend fun getCurrencies(@Path("userId") userId: String): List<Currency>

}