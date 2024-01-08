package com.theseuntaylor.minipe.lib_login.remote

import retrofit2.http.POST

interface AuthenticationNetworkDataSource {
    @POST("login")
    fun login() {

    }
}