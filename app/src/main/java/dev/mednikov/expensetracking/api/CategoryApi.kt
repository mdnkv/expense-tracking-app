package dev.mednikov.expensetracking.api

import dev.mednikov.expensetracking.models.Category
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryApi {

    @POST("categories/create")
    suspend fun createCategory (@Body payload: Category): Category

    @PUT("categories/update")
    suspend fun updateCategory (@Body payload: Category): Category

    @DELETE("categories/delete/{id}")
    suspend fun deleteCategory (@Path("id") id: String)

    @GET("categories/user/{userId}")
    suspend fun getCategories (@Path("userId") userId: String): List<Category>

    @GET("categories/category/{id}")
    suspend fun getCategoryById(@Path("id") id: String): Category

}