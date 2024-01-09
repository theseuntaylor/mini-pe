package com.theseuntaylor.minipe.lib_taps.model

data class TapsSuccess(
    val tappedAt: String,
    val status: String,
    val readerId: String,
)

data class TapsError(
    val error: String,
)