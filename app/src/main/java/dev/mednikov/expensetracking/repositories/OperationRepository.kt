package dev.mednikov.expensetracking.repositories

import dev.mednikov.expensetracking.api.OperationApi
import dev.mednikov.expensetracking.models.Currency
import dev.mednikov.expensetracking.models.Operation
import dev.mednikov.expensetracking.storage.TokenStorage
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException
import javax.inject.Inject

class OperationRepository @Inject constructor(
    private val operationApi: OperationApi,
    private val currencyRepository: CurrencyRepository,
    private val tokenStorage: TokenStorage
) {

    private suspend fun getUserId(): String = tokenStorage.userIdFlow.firstOrNull().toString()
    private suspend fun getCurrency(): Currency {
        val currencies = currencyRepository.getCurrencies()
        return currencies.first()
    }

    suspend fun createOperation(payload: Operation): Operation {
        val userId = getUserId()
        val currencyId = getCurrency().id
        payload.userId = userId
        payload.currencyId = currencyId
        return operationApi.createOperation(payload)
    }

    suspend fun getOperations(): List<Operation> {
        val userId = getUserId()
        return operationApi.getOperations(userId)
    }

    suspend fun getOperation(id: String): Operation? {
        return try {
            operationApi.getOperationById(id)
        } catch(ex: HttpException) {
            if (ex.code() == 404) null else throw ex
        }
    }

    suspend fun deleteOperation(id: String){
        operationApi.deleteOperation(id)
    }

    suspend fun updateOperation(payload: Operation): Operation{
        return operationApi.updateOperation(payload)
    }

}