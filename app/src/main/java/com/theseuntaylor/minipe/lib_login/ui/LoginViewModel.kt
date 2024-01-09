package com.theseuntaylor.minipe.lib_login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theseuntaylor.minipe.core.TextFieldState
import com.theseuntaylor.minipe.lib_login.data.repository.LoginRepository
import com.theseuntaylor.minipe.lib_login.model.LoginRequestBody
import com.theseuntaylor.minipe.utils.passwordValidator
import com.theseuntaylor.minipe.utils.usernameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
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
    val isButtonEnabled get() = usernameTextFieldState.isValid && passwordTextFieldState.isValid

    fun login() = viewModelScope.launch(Dispatchers.Main) {
        val requestBody = LoginRequestBody(
            username = usernameTextFieldState.text,
            password = passwordTextFieldState.text
        )
        repository.login(requestBody)
            .onStart {
                _loginState.update { loadingLoginState ->
                    loadingLoginState.copy(inProgress = true)
                }
            }
            .catch {
                _loginState.update { failedLoginState ->
                    failedLoginState.copy(
                        errorMessage = it.localizedMessage,
                        inProgress = false,
                    )
                }
            }
            .collect { res ->
            _loginState
                .update { loginState ->
                loginState.copy(
                    inProgress = false,
                    loginSuccessful = true,
                    result = res
                )
            }
        }
    }

}