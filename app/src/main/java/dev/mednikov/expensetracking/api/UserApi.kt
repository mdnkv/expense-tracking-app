package dev.mednikov.expensetracking.api

import dev.mednikov.expensetracking.models.SignupRequest
import dev.mednikov.expensetracking.models.SignupResponse
import dev.mednikov.expensetracking.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    @POST("users/create")
    @Headers("No-Auth: true")
    suspend fun signup(@Body payload: SignupRequest): SignupResponse

    @GET("users/user/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): User

    @PUT("users/update/user")
    suspend fun updateUser(@Body payload: User): User

}