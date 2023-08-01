package com.chisw.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

private const val MIN_SIZE_DP = 100
private const val MAX_SIZE_DP = 160

@Composable
fun CollageItem(modifier: Modifier = Modifier) {
    Card(
        modifier,
    ) {
        Text(
            stringResource(R.string.collage_layout_card),
            modifier = Modifier.padding(8.dp),
        )
        CollageLayout(
            modifier = Modifier.padding(16.dp),
        ) {
            Box(
                Modifier
                    .size(Random.nextInt(MIN_SIZE_DP, MAX_SIZE_DP).dp)
                    .background(Color.Yellow),
            )
            TextItem(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            )
            TextItem(
                "Quisque ac nisi ac elit semper congue",
            )
            Box(
                Modifier
                    .size(Random.nextInt(MIN_SIZE_DP, MAX_SIZE_DP).dp)
                    .background(Color.Red),
            )
            Box(
                Modifier
                    .size(Random.nextInt(MIN_SIZE_DP, MAX_SIZE_DP).dp)
                    .background(Color.Green),
            )
            TextItem(
                "Nunc luctus purus et nulla volutpat, at bibendum libero tempus",
            )
            TextItem(
                " Integer eu purus eget urna suscipit faucibus",
            )
            Box(
                Modifier
                    .size(Random.nextInt(MIN_SIZE_DP, MAX_SIZE_DP).dp)
                    .background(Color.Yellow),
            )
        }
    }
}

@Composable
fun TextItem(
    text: String,
    modifier: Modifier = Modifier,
    size: Dp = Random.nextInt(MIN_SIZE_DP, MAX_SIZE_DP).dp,
) {
    Text(
        text,
        modifier
            .width(size)
            .padding(8.dp),
    )
}
