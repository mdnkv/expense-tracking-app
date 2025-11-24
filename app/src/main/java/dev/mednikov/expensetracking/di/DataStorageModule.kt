package dev.mednikov.expensetracking.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mednikov.expensetracking.storage.TokenStorage
import dev.mednikov.expensetracking.ui.shared.PREFERENCES_FILE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStorageModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.dataStoreFile(PREFERENCES_FILE_NAME) }
        )
    }

    @Provides
    @Singleton
    fun provideTokenStorage(dataStore: DataStore<Preferences>): TokenStorage {
        return TokenStorage(dataStore)
    }

}