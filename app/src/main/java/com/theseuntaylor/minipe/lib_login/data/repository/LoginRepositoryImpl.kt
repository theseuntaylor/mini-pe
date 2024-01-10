package com.theseuntaylor.minipe.lib_login.data.repository

import com.theseuntaylor.minipe.core.data.DataStoreService
import com.theseuntaylor.minipe.lib_login.data.remote.LoginNetworkDataSource
import com.theseuntaylor.minipe.lib_login.model.LoginRequestBody
import com.theseuntaylor.minipe.lib_login.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginNetworkDataSource: LoginNetworkDataSource,
    private val localDataSource: DataStoreService,
) : LoginRepository {

    private suspend fun saveToken(token: String) {
        localDataSource.saveToken(token = token)
    }

    override suspend fun login(requestBody: LoginRequestBody): Flow<LoginResponse> = flow {
        try {
            val result = loginNetworkDataSource.login(requestBody)
            saveToken(token = result.apiToken)
            emit(result)
        } catch (e: SocketTimeoutException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}