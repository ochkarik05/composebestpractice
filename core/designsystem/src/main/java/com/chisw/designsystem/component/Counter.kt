package com.chisw.designsystem.component

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chisw.designsystem.R

@VisibleForTesting
const val COUNTER_VIEW_TEST_TAG = "ProfileView"

@Composable
fun Counter(value: Int, onIncrement: () -> Unit, onDecrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier.testTag(COUNTER_VIEW_TEST_TAG),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(R.string.counter_view_title), textAlign = TextAlign.Center)
        Text(text = value.toString(), style = MaterialTheme.typography.headlineLarge)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ChiButton(
                onClick = onDecrement,
                text = {
                    Text("-")
                },
            )
            ChiButton(
                onClick = onIncrement,
                text = {
                    Text("+")
                },
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Themed3Preview {
        Counter(value = 10, onIncrement = {}, onDecrement = {})
    }
}
