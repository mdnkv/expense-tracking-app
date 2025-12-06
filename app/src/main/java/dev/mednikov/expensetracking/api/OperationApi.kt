package dev.mednikov.expensetracking.api

import dev.mednikov.expensetracking.models.Operation
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OperationApi {

    @POST("operations/create")
    suspend fun createOperation(@Body payload: Operation): Operation

    @PUT("operations/update")
    suspend fun updateOperation(@Body payload: Operation): Operation

    @DELETE("operations/delete/{id}")
    suspend fun deleteOperation(@Path("id") operationId: String)

    @GET("operations/operation/{id}")
    suspend fun getOperationById(@Path("id") operationId: String): Operation

    @GET("operations/user/{userId}")
    suspend fun getOperations(@Path("userId") userId: String): List<Operation>

}