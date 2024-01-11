package com.theseuntaylor.minipe.core.data

import kotlinx.coroutines.flow.Flow

interface DataStoreService {
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String>
}