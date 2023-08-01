package com.chisw.designsystem.component

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chisw.designsystem.R

@VisibleForTesting
const val DATETIME_VIEW_TEST_TAG = "ProfileView"

@Composable
fun DateTimeView(date: String, time: String, modifier: Modifier = Modifier) {
    var showDate by rememberSaveable {
        mutableStateOf(false)
    }

    DateTimeView(time, date, showDate, modifier.testTag(DATETIME_VIEW_TEST_TAG)) {
        showDate = !showDate
    }
}

@Composable
fun DateTimeView(
    time: String,
    date: String,
    showDate: Boolean,
    modifier: Modifier,
    toggleShowDate: () -> Unit,
) {
    Column(
        modifier.clickable(onClick = toggleShowDate),
        verticalArrangement = spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(R.string.date_time_view_title),
            textAlign = TextAlign.Center,
        )
        Text(time, style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp))
        AnimatedVisibility(visible = showDate) {
            Text(date)
        }
    }
}

@Preview
@Composable
private fun Preview(@PreviewParameter(SampleBooleanProvider::class) showDate: Boolean) {
    Themed3Preview {
        DateTimeView(
            date = "July, 22, 2032",
            time = "07:00",
            showDate = showDate,
            modifier = Modifier,
            toggleShowDate = {},
        )
    }
}

class SampleBooleanProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}
