package dev.mednikov.expensetracking.api

import dev.mednikov.expensetracking.models.SignupRequest
import dev.mednikov.expensetracking.models.SignupResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {

    @POST("users/create")
    @Headers("No-Auth: true")
    suspend fun signup(@Body payload: SignupRequest): SignupResponse

}