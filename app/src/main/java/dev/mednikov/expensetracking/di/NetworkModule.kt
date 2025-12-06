package dev.mednikov.expensetracking.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mednikov.expensetracking.api.AccountApi
import dev.mednikov.expensetracking.api.AuthApi
import dev.mednikov.expensetracking.api.AuthInterceptor
import dev.mednikov.expensetracking.api.CategoryApi
import dev.mednikov.expensetracking.api.CurrencyApi
import dev.mednikov.expensetracking.api.DateSerializer
import dev.mednikov.expensetracking.api.OperationApi
import dev.mednikov.expensetracking.api.UserApi
import dev.mednikov.expensetracking.storage.TokenStorage
import dev.mednikov.expensetracking.ui.shared.BACKEND_API_ROOT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenStorage: TokenStorage): AuthInterceptor {
        return AuthInterceptor(tokenStorage)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, DateSerializer())
            .create()
        return Retrofit.Builder()
            .baseUrl(BACKEND_API_ROOT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi = retrofit.create(CategoryApi::class.java)

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi = retrofit.create(AccountApi::class.java)

    @Provides
    @Singleton
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi = retrofit.create(CurrencyApi::class.java)

    @Provides
    @Singleton
    fun provideOperationApi(retrofit: Retrofit): OperationApi = retrofit.create(OperationApi::class.java)

}