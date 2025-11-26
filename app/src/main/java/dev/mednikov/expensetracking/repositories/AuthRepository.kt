package dev.mednikov.expensetracking.repositories

import dev.mednikov.expensetracking.api.AuthApi
import dev.mednikov.expensetracking.models.LoginRequest
import dev.mednikov.expensetracking.storage.TokenStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val api: AuthApi,
    private val storage: TokenStorage
) {

    suspend fun login(payload: LoginRequest): Result<Unit> {
        return try {
            val response = api.login(payload)
            storage.saveAuthData(response.token, response.id)
            Result.success(Unit)
        } catch (ex: Exception){
            Result.failure(ex)
        }
    }

    suspend fun logout(): Result<Unit> =
        try {
            api.logout()
            storage.clear()
            Result.success(Unit)
        } catch (e: Exception) {
            storage.clear()
            Result.failure(e)
        }

    fun getLoggedIn(): Flow<Boolean> =
        storage.tokenFlow.map {
            !it.isNullOrBlank()
        }

}
