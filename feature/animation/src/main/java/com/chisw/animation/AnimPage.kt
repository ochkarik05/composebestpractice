package com.chisw.animation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chisw.animation.animations.AnimatedVectorDrawableDemo
import com.chisw.animation.animations.LetItSnow
import com.chisw.animation.animations.SpringAnimDemo

@Composable
fun AnimPage(animPage: AnimPages, modifier: Modifier = Modifier) {
    when (animPage) {
        AnimPages.SNOWFALL -> LetItSnow(modifier)
        AnimPages.SPRING -> SpringAnimDemo(modifier)
        AnimPages.VECTOR -> AnimatedVectorDrawableDemo(modifier)
    }
}
