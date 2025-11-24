package dev.mednikov.expensetracking.repositories

import android.util.Log
import dev.mednikov.expensetracking.api.UserApi
import dev.mednikov.expensetracking.models.SignupRequest

class UserRepository (private val userApi: UserApi) {

    suspend fun signup(payload: SignupRequest): Result<Unit> {
        return try {
            val response = userApi.signup(payload)
            Log.d("UserRepository", "Response: ${response.toString()}")
            Result.success(Unit)
        } catch (ex: Exception){
            Log.d("UserRepository", ex.message.toString())
            Result.failure(ex)
        }
    }

}