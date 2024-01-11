package com.theseuntaylor.minipe.lib_taps.model

data class TapsSuccess(
    val tappedAt: String = "2018-12-14T09:55:00",
    val status: String = "Success",
    val readerId: String = "Reader-0001",
)

data class TapsError(
    val error: String
)