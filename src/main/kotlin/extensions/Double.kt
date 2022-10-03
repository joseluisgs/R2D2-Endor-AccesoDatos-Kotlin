package extensions

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundTo(decimals: Int = 2): Double {
    val factor = 10.0.pow(decimals.toDouble())
    return (this * factor).roundToInt() / factor
}