package com.theseuntaylor.minipe.lib_login.data.repository

import app.cash.turbine.test
import com.theseuntaylor.minipe.core.data.DataStoreService
import com.theseuntaylor.minipe.core.data.FakeResponse
import com.theseuntaylor.minipe.lib_login.data.remote.LoginNetworkDataSource
import com.theseuntaylor.minipe.lib_login.model.LoginRequestBody
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LoginRepositoryTest {

    @Test
    fun `attempted login with wrong credentials, then unauthorised is returned`() = runTest {
        // given
        val loginRequest = LoginRequestBody("", "")
        val loginErrorMessage = "HTTP 401"
        val dataSource = mock<LoginNetworkDataSource>().apply {
            whenever(login(request = loginRequest)).doAnswer {
                throw java.io.IOException(loginErrorMessage)
            }
        }
        val loginRepository = createLoginRepository(dataSource = dataSource)

        //when
        val loginResponse = loginRepository.login(loginRequest)

        //then
        loginResponse.test {
            assert(awaitError().message == loginErrorMessage)
        }
    }

    @Test
    fun `attempted login with right credentials, then login response is returned`() = runTest {
        // given
        val loginRequest =
            LoginRequestBody("hello@pasentry.com", "securepass")
        val dataSource = mock<LoginNetworkDataSource>().apply {
            whenever(login(request = loginRequest)).thenReturn(FakeResponse.successfulLoginResponse)
        }
        val loginRepository = createLoginRepository(dataSource)

        //when
        val loginResponse = loginRepository.login(loginRequest)

        //then
        loginResponse.test {
            awaitItem().apiToken?.let { assert(it.isNotBlank()) }
            awaitComplete()
        }

    }

    @Test
    fun `attempted login with right credentials, then bearer token is saved`() = runTest {
        // given
        val loginRequest =
            LoginRequestBody("hello@pasentry.com", "securepass")
        val dataSource = mock<LoginNetworkDataSource>().apply {
            whenever(login(request = loginRequest)).thenReturn(FakeResponse.successfulLoginResponse)
        }
        val localDataSource = mock<DataStoreService>().apply {
            whenever(getToken()).thenReturn(flowOf("bearer-token"))
            // { "bearer-token" })
        }
        val loginRepository = createLoginRepository(
            dataSource = dataSource,
        )

        //when
        loginRepository.login(loginRequest).first()

        //then
        verify(localDataSource).saveToken(token = "bearer-token")

    }

    private fun createLoginRepository(
        dataSource: LoginNetworkDataSource = mock(),
        localDataSource: DataStoreService = mock(),
    ) = LoginRepositoryImpl(
        loginNetworkDataSource = dataSource,
        localDataSource = localDataSource,
    )
}