package com.theseuntaylor.minipe.lib_taps.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theseuntaylor.minipe.lib_taps.data.repository.TapsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TapsViewModel @Inject constructor(
    private val tapsRepository: TapsRepository,
) : ViewModel() {

    private val _uiState = mutableStateOf<TapsUiState>(TapsUiState.Initial)
    val uiState: State<TapsUiState> = _uiState

    init {
        getTaps()
    }

    fun getTaps() = viewModelScope.launch {
        tapsRepository.getTaps()
            .onStart {
                _uiState.value = TapsUiState.Initial
            }.catch {
                _uiState.value =
                    TapsUiState.Error(
                        it.message ?: "There was an error loading your recent taps!"
                    )
            }.collect { result ->
                _uiState.value = TapsUiState.Success(result)
            }
    }

}