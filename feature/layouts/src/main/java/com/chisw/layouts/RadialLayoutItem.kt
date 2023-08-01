package com.chisw.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun RadialLayoutItem(modifier: Modifier = Modifier) {
    Card(
        modifier,
    ) {
        Text(
            stringResource(R.string.radial_layout_card),
            modifier = Modifier.padding(8.dp),
        )
        RadialLayout(
            options = listOf("Uno", "Due", "Tre", "Quattro"),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        )
    }
}
