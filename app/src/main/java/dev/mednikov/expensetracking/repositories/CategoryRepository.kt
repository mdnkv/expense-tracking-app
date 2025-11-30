package dev.mednikov.expensetracking.repositories

import dev.mednikov.expensetracking.api.CategoryApi
import dev.mednikov.expensetracking.models.Category
import dev.mednikov.expensetracking.storage.TokenStorage
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val api: CategoryApi, private val storage: TokenStorage) {

    private suspend fun getUserId(): String = storage.userIdFlow.firstOrNull().toString()

    suspend fun getCategories(): List<Category> {
        val userId = getUserId()
        return api.getCategories(userId)
    }

    suspend fun createCategory (payload: Category): Category {
        val userId = getUserId()
        payload.userId = userId
        return api.createCategory(payload)
    }

    suspend fun updateCategory (payload: Category): Category {
        return api.updateCategory(payload)
    }

    suspend fun deleteCategory(id: String){
        api.deleteCategory(id)
    }

    suspend fun getCategoryById(id: String): Category? {
        return try {
            api.getCategoryById(id)
        } catch(ex: HttpException) {
            if (ex.code() == 404) null else throw ex
        }
    }

}