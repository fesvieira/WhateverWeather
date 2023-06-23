package com.fesvieira.whateverweather.helpers

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.abs
import kotlin.math.pow

val Double?.formatTemperature: String get() {
    if (this == null) return "--"
    val hasDecimalPoint = ((abs(this) % 1.0) > (1*(10.0.pow(-1))))
    val valueBigDecimal = BigDecimal.valueOf(this)

    val newRoundingMode = when {
        this > 0 -> RoundingMode.CEILING
        this < 0 -> RoundingMode.FLOOR
        else -> RoundingMode.CEILING
    }

    val valueTruncated = valueBigDecimal.setScale(
        if (hasDecimalPoint) 1 else 0,
        newRoundingMode
    )

    return "${valueTruncated}º"
}