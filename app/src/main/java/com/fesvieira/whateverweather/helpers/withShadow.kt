package com.fesvieira.whateverweather.helpers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle

val TextStyle.withShadow: TextStyle get() {
    return this.copy(
        shadow = Shadow(
            color = Color.Black.copy(alpha = 0.6f),
            offset = Offset(x = 2f, y = 4f),
            blurRadius = 0.5f
        )
    )
}