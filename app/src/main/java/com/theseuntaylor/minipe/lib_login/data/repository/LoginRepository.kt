package com.theseuntaylor.minipe.lib_login.data.repository

import com.theseuntaylor.minipe.lib_login.model.LoginRequestBody
import com.theseuntaylor.minipe.lib_login.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(requestBody: LoginRequestBody): Flow<LoginResponse>
}