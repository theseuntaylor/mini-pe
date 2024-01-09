package com.theseuntaylor.minipe.lib_login.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("api-token")
    val apiToken: String,
    val error: String,
)