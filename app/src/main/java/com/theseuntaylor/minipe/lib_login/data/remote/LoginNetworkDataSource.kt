package com.theseuntaylor.minipe.lib_login.data.remote

import com.theseuntaylor.minipe.lib_login.model.LoginRequestBody
import com.theseuntaylor.minipe.lib_login.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginNetworkDataSource {
    @POST("login")
    suspend fun login(@Body request: LoginRequestBody): LoginResponse
}