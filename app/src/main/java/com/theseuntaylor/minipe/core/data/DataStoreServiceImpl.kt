package com.theseuntaylor.minipe.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreServiceImpl @Inject constructor(
    private val preferencesDataStore: DataStore<Preferences>,
) : DataStoreService {

    private val TOKEN_KEY = stringPreferencesKey("bearer_token")
    override suspend fun saveToken(token: String) {
        preferencesDataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    override fun getToken(): Flow<String> {
        return preferencesDataStore.data.map {
            it[TOKEN_KEY] ?: ""
        }
    }
}