package io.vopenia.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import eu.codlab.compose.theme.LocalDarkTheme

@Composable
fun defaultCardBackground(isSelected: Boolean = false): Color {
    return if (isSelected) {
        if (LocalDarkTheme.current) {
            AppColor.BackgroundBlue
        } else {
            AppColor.GrayLight
        }
    } else if (LocalDarkTheme.current) {
        AppColor.BackgroundLightBlue
    } else {
        AppColor.WhiteCream
    }
}