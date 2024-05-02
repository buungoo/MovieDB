package com.example.moviedb.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getScreenWidth() : Dp {
    return LocalConfiguration.current.screenWidthDp.dp
}

@Composable
fun getReviewCardScreenWidth() : Dp {
    return getScreenWidth() - 8.dp
}