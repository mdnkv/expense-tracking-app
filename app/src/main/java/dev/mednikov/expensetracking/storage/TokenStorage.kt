package dev.mednikov.expensetracking.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class TokenStorage(private val dataStore: DataStore<Preferences>) {

    companion object {
        val TOKEN_KEY = stringPreferencesKey("token")
        val USER_ID_KEY = stringPreferencesKey("userId")
    }

    val tokenFlow = dataStore.data.map {it[TOKEN_KEY] }
    val userIdFlow = dataStore.data.map { it[USER_ID_KEY] }

    suspend fun saveAuthData(token: String, userId: String) {
        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USER_ID_KEY] = userId
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
