package com.theseuntaylor.minipe.lib_login.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.theseuntaylor.minipe.core.TextFieldState
import com.theseuntaylor.minipe.utils.passwordValidator
import com.theseuntaylor.minipe.utils.usernameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
//    private val repository LoginRepository,
) : ViewModel() {

    object StringConstants {
        const val validUsername = "Please put in a valid email"
        const val validPassword = "Please put in a valid password"
    }

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    val usernameTextFieldState = TextFieldState(
        validator = { it.usernameValidator() },
        errorFor = StringConstants.validUsername
    )
    val passwordTextFieldState = TextFieldState(
        validator = { it.passwordValidator() },
        errorFor = StringConstants.validPassword
    )
    val isButtonEnabled get()  = usernameTextFieldState.isValid && passwordTextFieldState.isValid

    fun login() {
        Log.e("Button Pressed?", "Button Pressed")
        TODO("Not yet implemented")
    }

}