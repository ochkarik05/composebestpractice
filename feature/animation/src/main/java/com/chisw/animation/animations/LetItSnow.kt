package com.chisw.animation.animations

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chisw.animation.R
import com.chisw.animation.modifiers.SnowFallType
import com.chisw.animation.modifiers.snowfall
import com.chisw.designsystem.component.Themed3Preview

@Composable
fun LetItSnow(modifier: Modifier = Modifier) {
    var isSnowing by rememberSaveable {
        mutableStateOf(false)
    }

    val color by animateColorAsState(targetValue = if (isSnowing) Color.Black else Color.Transparent, label = "")

    Column(
        modifier
            .fillMaxSize()
            .then(
                if (isSnowing) {
                    Modifier
                        .snowfall(SnowFallType.FLYING_CIRCLES)
                        .snowfall(SnowFallType.SNOWFLAKES)
                } else {
                    Modifier
                },
            )
            .background(color),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                stringResource(R.string.let_it_snow),
                color = if (color == Color.Black) Color.White else MaterialTheme.colorScheme.onBackground,
            )
            Switch(checked = isSnowing, onCheckedChange = {
                isSnowing = it
            })
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Themed3Preview {
        LetItSnow()
    }
}
