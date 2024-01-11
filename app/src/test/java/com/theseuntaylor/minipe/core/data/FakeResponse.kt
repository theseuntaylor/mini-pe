package com.theseuntaylor.minipe.core.data

import com.theseuntaylor.minipe.lib_login.model.LoginResponse
import com.theseuntaylor.minipe.lib_taps.model.TapsSuccess

object FakeResponse {

    val successfulLoginResponse: LoginResponse = LoginResponse()

    val successfulTapsResponse = listOf(
        TapsSuccess(),
        TapsSuccess(),
        TapsSuccess(),
        TapsSuccess(status = "fail"),
        TapsSuccess(),
    )
}