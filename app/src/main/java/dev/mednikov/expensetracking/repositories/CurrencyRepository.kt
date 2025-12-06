package dev.mednikov.expensetracking.repositories

import dev.mednikov.expensetracking.api.CurrencyApi
import dev.mednikov.expensetracking.models.Currency
import dev.mednikov.expensetracking.storage.TokenStorage
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CurrencyRepository @Inject constructor(val currencyApi: CurrencyApi, val storage: TokenStorage) {

    private suspend fun getUserId(): String = storage.userIdFlow.firstOrNull().toString()

    suspend fun getCurrencies(): List<Currency> {
        val userId = getUserId()
        return currencyApi.getCurrencies(userId)
    }

}