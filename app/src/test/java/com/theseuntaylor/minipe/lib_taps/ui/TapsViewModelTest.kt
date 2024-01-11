package com.theseuntaylor.minipe.lib_taps.ui

import com.theseuntaylor.minipe.core.MainDispatcherRule
import com.theseuntaylor.minipe.core.data.FakeResponse
import com.theseuntaylor.minipe.lib_taps.data.repository.TapsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class TapsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getTaps is executed, if success, response is received`() = runTest {
        //given
        val expectedResponse = TapsUiState.Success(FakeResponse.successfulTapsResponse)
        val repository = mock<TapsRepository>().apply {
            whenever(getTaps()).thenReturn(
                flowOf(
                    FakeResponse.successfulTapsResponse
                )
            )
        }
        val tapsViewModel = createTapsViewModel(tapsRepository = repository)
        //when
        try {
            val testDispatcher = UnconfinedTestDispatcher(testScheduler)
            Dispatchers.setMain(testDispatcher)

            tapsViewModel.getTaps()

            testDispatcher.scheduler.advanceUntilIdle()

            val receivedResponse = tapsViewModel.uiState.value
            //then
            assertThat(expectedResponse, `is`(receivedResponse))
        } finally {
            Dispatchers.resetMain()
        }

    }

    private fun createTapsViewModel(
        tapsRepository: TapsRepository = mock(),
    ) = TapsViewModel(
        tapsRepository = tapsRepository,
    )

}