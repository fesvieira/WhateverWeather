package com.fesvieira.whateverweather.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.glance.unit.ColorProvider
import com.fesvieira.whateverweather.R
import androidx.glance.text.TextStyle as GTextStyle
import androidx.glance.text.FontFamily as GFontFamily
import androidx.glance.text.FontWeight as GFontWeight

val OpenSans = FontFamily(Font(R.font.open_sans))
val GOpenSans = GFontFamily(OpenSans.toString())

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontSize = 96.sp,
        fontFamily = OpenSans,
        color = Color.White,
        fontWeight = FontWeight.Normal
    ),
    bodyLarge = TextStyle(
        fontSize = 34.sp,
        fontFamily = OpenSans,
        color = Color.White,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontSize = 20.sp,
        fontFamily = OpenSans,
        color = Color.White,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = OpenSans,
        color = Color.White,
        fontWeight = FontWeight.Normal
    )
)

object GlanceTypography {
    val headlineLarge = GTextStyle(
        fontSize = 96.sp,
        fontFamily = GOpenSans,
        color = ColorProvider(R.color.white),
        fontWeight = GFontWeight.Normal
    )
    val bodyLarge = GTextStyle(
        fontSize = 34.sp,
        fontFamily = GOpenSans,
        color = ColorProvider(R.color.white),
        fontWeight = GFontWeight.Normal
    )
    val bodyMedium = GTextStyle(
        fontSize = 20.sp,
        fontFamily = GOpenSans,
        color = ColorProvider(R.color.white),
        fontWeight = GFontWeight.Normal
    )
    val bodySmall = GTextStyle(
        fontSize = 14.sp,
        fontFamily = GOpenSans,
        color = ColorProvider(R.color.white),
        fontWeight = GFontWeight.Normal
    )
}