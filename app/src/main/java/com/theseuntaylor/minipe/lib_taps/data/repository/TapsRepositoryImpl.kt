package com.theseuntaylor.minipe.lib_taps.data.repository

import com.theseuntaylor.minipe.lib_taps.data.remote.TapsNetworkDataSource
import com.theseuntaylor.minipe.lib_taps.model.TapsSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import javax.inject.Inject

class TapsRepositoryImpl @Inject constructor(
    private val tapsNetworkDataSource: TapsNetworkDataSource,
) : TapsRepository {

    override suspend fun getTaps(): Flow<List<TapsSuccess>> = flow {
        try {
            val result = tapsNetworkDataSource.getTaps()
            emit(result)
        } catch (e: SocketTimeoutException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

}