package utils

import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.minutes

fun Int.hourMinutes(): String {
    return "${this.minutes.inWholeHours}h ${this % 60}m"
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}
