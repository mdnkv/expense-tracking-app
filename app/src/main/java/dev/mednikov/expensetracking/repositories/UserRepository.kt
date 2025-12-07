package dev.mednikov.expensetracking.repositories

import dev.mednikov.expensetracking.api.UserApi
import dev.mednikov.expensetracking.models.SignupRequest
import dev.mednikov.expensetracking.models.User
import dev.mednikov.expensetracking.storage.TokenStorage
import kotlinx.coroutines.flow.firstOrNull

class UserRepository (private val userApi: UserApi, private val tokenStorage: TokenStorage) {

    suspend fun signup(payload: SignupRequest): Result<Unit> {
        return try {
            val response = userApi.signup(payload)
            Result.success(Unit)
        } catch (ex: Exception){
            Result.failure(ex)
        }
    }

    suspend fun getCurrentUser(): User {
        val userId = tokenStorage.userIdFlow.firstOrNull().toString()
        return userApi.getUserById(userId)
    }

    suspend fun updateUser(payload: User): User{
        return userApi.updateUser(payload)
    }

}