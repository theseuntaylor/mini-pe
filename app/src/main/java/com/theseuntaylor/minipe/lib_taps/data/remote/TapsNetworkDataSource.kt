package com.theseuntaylor.minipe.lib_taps.data.remote

import okhttp3.Response
import retrofit2.http.GET

interface TapsNetworkDataSource {
    @GET("taps")
    // TODO: map to either list or object with `error`
    suspend fun getTaps(): Response
}