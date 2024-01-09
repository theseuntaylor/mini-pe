package com.theseuntaylor.minipe.lib_login.ui

import com.theseuntaylor.minipe.lib_login.model.LoginResponse

data class LoginState(
    val inProgress: Boolean = false,
    val loginSuccessful: Boolean = false,
    val errorMessage: String? = null,
    val result: LoginResponse? = null
)