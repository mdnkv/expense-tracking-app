package dev.mednikov.expensetracking.api

import dev.mednikov.expensetracking.models.LoginRequest
import dev.mednikov.expensetracking.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    @Headers("No-Auth: true")
    suspend fun login(@Body payload: LoginRequest): LoginResponse

    @POST("auth/logout")
    suspend fun logout()

}