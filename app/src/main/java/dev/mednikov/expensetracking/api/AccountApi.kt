package dev.mednikov.expensetracking.api

import dev.mednikov.expensetracking.models.Account
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApi {

    @POST("accounts/create")
    suspend fun createAccount(@Body payload: Account): Account

    @PUT("accounts/update")
    suspend fun updateAccount(@Body payload: Account): Account

    @DELETE("accounts/delete/{id}")
    suspend fun deleteAccount (@Path("id") id: String)

    @GET("accounts/account/{id}")
    suspend fun getAccountById(@Path("id") id: String): Account?

    @GET("accounts/user/{userId}")
    suspend fun getAccounts (@Path("userId") userId: String): List<Account>

}