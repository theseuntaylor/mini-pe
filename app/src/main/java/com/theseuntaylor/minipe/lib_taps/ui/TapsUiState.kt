package com.theseuntaylor.minipe.lib_taps.ui

import com.theseuntaylor.minipe.lib_taps.model.TapsSuccess


sealed class TapsUiState {
    data object Initial: TapsUiState()
    data object Loading: TapsUiState()
    data class Error(val errorMessage: String): TapsUiState()
    data class Success(val response: List<TapsSuccess>): TapsUiState()
}