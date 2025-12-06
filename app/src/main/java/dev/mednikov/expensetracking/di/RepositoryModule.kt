package dev.mednikov.expensetracking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mednikov.expensetracking.api.AccountApi
import dev.mednikov.expensetracking.api.AuthApi
import dev.mednikov.expensetracking.api.CategoryApi
import dev.mednikov.expensetracking.api.CurrencyApi
import dev.mednikov.expensetracking.api.OperationApi
import dev.mednikov.expensetracking.api.UserApi
import dev.mednikov.expensetracking.repositories.AccountRepository
import dev.mednikov.expensetracking.repositories.AuthRepository
import dev.mednikov.expensetracking.repositories.CategoryRepository
import dev.mednikov.expensetracking.repositories.CurrencyRepository
import dev.mednikov.expensetracking.repositories.OperationRepository
import dev.mednikov.expensetracking.repositories.UserRepository
import dev.mednikov.expensetracking.storage.TokenStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepository(userApi)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi, tokenStorage: TokenStorage): AuthRepository {
        return AuthRepository(authApi, tokenStorage)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(api: CategoryApi, tokenStorage: TokenStorage): CategoryRepository {
        return CategoryRepository(api, tokenStorage)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(api: AccountApi, tokenStorage: TokenStorage): AccountRepository {
        return AccountRepository(api, tokenStorage)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(api: CurrencyApi, tokenStorage: TokenStorage): CurrencyRepository {
        return CurrencyRepository(api, tokenStorage)
    }

    @Provides
    @Singleton
    fun provideOperationRepository(api: OperationApi, currencyRepository: CurrencyRepository, tokenStorage: TokenStorage): OperationRepository {
        return OperationRepository(
            operationApi = api,
            currencyRepository = currencyRepository,
            tokenStorage = tokenStorage
        )
    }
}