package dev.mednikov.expensetracking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mednikov.expensetracking.api.AuthApi
import dev.mednikov.expensetracking.api.CategoryApi
import dev.mednikov.expensetracking.api.UserApi
import dev.mednikov.expensetracking.repositories.AuthRepository
import dev.mednikov.expensetracking.repositories.CategoryRepository
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

}