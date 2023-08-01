package com.chisw.layouts

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chisw.designsystem.component.ChiHeader

@Composable
fun CustomLayoutDemoScreen() {
    LazyColumn(
        modifier = Modifier
            .padding(top = 16.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        item {
            ChiHeader(
                text = stringResource(id = R.string.custom_layout_demo_screen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }

        item {
            RadialLayoutItem(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
        }
        item {
            CollageItem(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
        }
    }
}
