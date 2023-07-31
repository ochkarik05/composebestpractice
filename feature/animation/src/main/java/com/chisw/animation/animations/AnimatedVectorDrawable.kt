package com.chisw.animation.animations

import androidx.annotation.DrawableRes
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chisw.animation.R

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
private fun AnimatedVectorDrawable(@DrawableRes resId: Int, modifier: Modifier = Modifier) {
    val image = AnimatedImageVector.animatedVectorResource(resId)
    var atEnd by remember { mutableStateOf(false) }
    Image(
        painter = rememberAnimatedVectorPainter(image, atEnd),
        contentDescription = "Timer",
        modifier = modifier
            .clickable {
                atEnd = !atEnd
            },
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun AnimatedVectorDrawableDemo(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(stringResource(R.string.vector_drawable_title))
        AnimatedVectorCard(R.drawable.hourglass_animated)
        AnimatedVectorCard(R.drawable.rotation_animated)
        AnimatedVectorCard(R.drawable.arrows_animated)
        AnimatedVectorCard(R.drawable.uploading_animated)
        AnimatedVectorCard(R.drawable.upload_complete_animated)
    }
}

@Composable
private fun ColumnScope.AnimatedVectorCard(resId: Int) {
    OutlinedCard(
        modifier = Modifier.align(Alignment.CenterHorizontally),
    ) {
        AnimatedVectorDrawable(
            resId,
            Modifier
                .width(128.dp)
                .padding(24.dp),
        )
    }
}
