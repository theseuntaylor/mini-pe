package com.theseuntaylor.minipe.lib_taps.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theseuntaylor.minipe.R
import com.theseuntaylor.minipe.core.composables.Loader
import com.theseuntaylor.minipe.core.composables.TapChip

@Composable
fun TapsScreen(
    tapsViewModel: TapsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by tapsViewModel.uiState

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.taps_header),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 24.sp,
            ),
            textAlign = TextAlign.Center
        )
        when (val state = uiState) {
            is TapsUiState.Initial, TapsUiState.Loading -> Loader()
            is TapsUiState.Error -> {
                Button(onClick = tapsViewModel::getTaps) {
                    Text(text = stringResource(R.string.retry))
                }
                Toast.makeText(context, state.errorMessage, Toast.LENGTH_LONG).show()
            }

            is TapsUiState.Success -> {
                val taps = state.response
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    items(taps) { tap ->
                        TapChip(tap)
                    }
                }
            }
        }
    }

}