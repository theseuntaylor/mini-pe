package com.theseuntaylor.minipe.core.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theseuntaylor.minipe.lib_taps.model.TapsSuccess
import com.theseuntaylor.minipe.utils.returnDate
import com.theseuntaylor.minipe.utils.returnTime

@Composable
fun TapChip(tap: TapsSuccess) {
    Card {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Reader ID: ${tap.readerId}")
                Text("Tap Status: ${tap.status}")
            }
            VerticalSpacer(height = 5.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Date of Tap: ${tap.tappedAt.returnDate()}")
                Text("Time of Tap: ${tap.tappedAt.returnTime()}")
            }
        }
    }
}

@Composable
@Preview
fun TapChipPreview() {
    TapChip(
        tap = TapsSuccess(
            "2018-12-14T09:55:00", "Status", "Some Random ID"
        )
    )
}