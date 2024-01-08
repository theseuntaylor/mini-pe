package com.theseuntaylor.minipe.lib_taps.remote

import com.theseuntaylor.minipe.lib_taps.model.Taps
import retrofit2.http.GET

interface TapsNetworkDataSource {
    @GET("taps")
    suspend fun getTaps(): List<Taps>
}