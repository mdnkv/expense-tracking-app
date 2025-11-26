package dev.mednikov.expensetracking.api

import dev.mednikov.expensetracking.storage.TokenStorage
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenStorage: TokenStorage
) : Interceptor {

    @Volatile private var cachedToken: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // Read token from cache or DataStore
        val token = cachedToken ?: runBlocking {
            tokenStorage.tokenFlow.firstOrNull().also { cachedToken = it }
        }

        return if (token == null || original.header("No-Auth") != null) {
            chain.proceed(original)
        } else {
            val newRequest = original.newBuilder()
                .addHeader("Authorization", "Token $token")
                .build()

            chain.proceed(newRequest)
        }
    }
}