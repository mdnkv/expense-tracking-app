package dev.mednikov.expensetracking.repositories

import dev.mednikov.expensetracking.api.AccountApi
import dev.mednikov.expensetracking.models.Account
import dev.mednikov.expensetracking.storage.TokenStorage
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException

class AccountRepository(private val api: AccountApi, private val storage: TokenStorage) {

    suspend fun getAccounts(): List<Account>{
        val userId = storage.userIdFlow.firstOrNull().toString()
        return api.getAccounts(userId)
    }

    suspend fun createAccount(payload: Account): Account{
        val userId = storage.userIdFlow.firstOrNull().toString()
        payload.userId = userId
        return api.createAccount(payload)
    }

    suspend fun updateAccount(payload: Account): Account {
        return api.updateAccount(payload)
    }

    suspend fun deleteAccount(id: String){
        api.deleteAccount(id)
    }

    suspend fun getAccountById(id: String): Account? {
        return try {
            api.getAccountById(id)
        } catch(ex: HttpException) {
            if (ex.code() == 404) null else throw ex
        }
    }

}