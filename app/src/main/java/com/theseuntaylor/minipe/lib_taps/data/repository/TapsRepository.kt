package com.theseuntaylor.minipe.lib_taps.data.repository

import com.theseuntaylor.minipe.lib_taps.model.TapsSuccess
import kotlinx.coroutines.flow.Flow

interface TapsRepository {
    suspend fun getTaps(): Flow<List<TapsSuccess>>
}