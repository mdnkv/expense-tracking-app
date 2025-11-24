package dev.mednikov.expensetracking.repositories

import dev.mednikov.expensetracking.api.UserApi
import dev.mednikov.expensetracking.models.SignupRequest

class UserRepository (private val userApi: UserApi) {

    suspend fun signup(payload: SignupRequest): Result<Unit> {
        return try {
            val response = userApi.signup(payload)
            Result.success(Unit)
        } catch (ex: Exception){
            Result.failure(ex)
        }
    }

}