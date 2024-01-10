package com.theseuntaylor.minipe.lib_taps.data.remote

import com.theseuntaylor.minipe.lib_taps.model. TapsSuccess
import retrofit2.http.GET

interface TapsNetworkDataSource {
    @GET("taps")
            suspend fun getTaps(): List<TapsSuccess>
}