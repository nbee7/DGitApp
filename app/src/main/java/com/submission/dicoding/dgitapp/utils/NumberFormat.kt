package com.submission.dicoding.dgitapp.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

const val SUFFIX_CHARS = "KMBTPE"

fun Int.toShortNumberDisplay(): String {
    val doubleNumber = this.toDouble()
    val formatter = DecimalFormat("###.#")
    val isNumberLessThanThousand = this < 1000.0
    formatter.roundingMode = RoundingMode.DOWN

    return if (isNumberLessThanThousand) {
        formatter.format(doubleNumber)
    } else {
        val exp = (ln(doubleNumber) / ln(1000.0)).toInt()
        formatter.format(this / 1000.0.pow(exp.toDouble())) + SUFFIX_CHARS[exp - 1]
    }
}