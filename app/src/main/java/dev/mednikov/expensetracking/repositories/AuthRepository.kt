package dev.mednikov.expensetracking.repositories

import android.util.Log
import dev.mednikov.expensetracking.api.AuthApi
import dev.mednikov.expensetracking.models.LoginRequest
import dev.mednikov.expensetracking.storage.TokenStorage

class AuthRepository(
    private val api: AuthApi,
    private val storage: TokenStorage
) {

    suspend fun login(payload: LoginRequest): Result<Unit> {
        return try {
            val response = api.login(payload)
            Log.d("LOGIN", "Login response: ${response.toString()}")
            storage.saveAuthData(response.token, response.id)
            Result.success(Unit)
        } catch (ex: Exception){
            Result.failure(ex)
        }
    }

}
