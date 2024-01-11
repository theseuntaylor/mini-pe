package com.theseuntaylor.minipe.lib_login.ui

import com.theseuntaylor.minipe.core.MainDispatcherRule
import com.theseuntaylor.minipe.core.data.FakeResponse
import com.theseuntaylor.minipe.lib_login.data.repository.LoginRepository
import com.theseuntaylor.minipe.lib_login.model.LoginRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when login is executed, then inProgress is set`() = runTest {
        //given
        val repository = mock<LoginRepository>().apply {
            whenever(login(any())).thenReturn(emptyFlow())
        }
        val loginViewModel = createLoginViewModel(loginRepository = repository)
        //when
        loginViewModel.login()
        //then
        assertThat(loginViewModel.loginState.first().inProgress, `is`(true))
    }

    @Test
    fun `when login is executed, if there is an error, then errorMessage is set`() {

        val exceptionMessage = "there was an error"

        runTest {
            //given
            val repository = mock<LoginRepository>().apply {
                whenever(login(any())).thenReturn(
                    flow { throw Throwable(exceptionMessage) }
                )
            }
            val loginViewModel = createLoginViewModel(loginRepository = repository)
            //when
            loginViewModel.login()
            //then
            assertThat(loginViewModel.loginState.value.errorMessage, `is`(exceptionMessage))
            assertThat(loginViewModel.loginState.value.inProgress, `is`(false))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when login is executed, if there is no error, then successful response is received`() {

        val password = "securepass"
        val email = "hello@passentry.com"

        runTest {
            //given

            val loginRequest = LoginRequestBody(
                username = email,
                password = password
            )
            val repository = mock<LoginRepository>().apply {
                whenever(login(requestBody = loginRequest)).thenReturn(
                    flowOf(FakeResponse.successfulLoginResponse)
                )
            }
            val loginViewModel = createLoginViewModel(
                loginRepository = repository,
            )
            //when
            try {
                loginViewModel.login()
                //then
                assertThat(
                    loginViewModel.loginState.value.result,
                    `is`(FakeResponse.successfulLoginResponse)
                )
            } finally {
                Dispatchers.resetMain()
            }
        }
    }

    private fun createLoginViewModel(
        loginRepository: LoginRepository = mock(),
    ) = LoginViewModel(
        repository = loginRepository,
    )
}