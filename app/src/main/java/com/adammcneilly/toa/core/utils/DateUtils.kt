package com.adammcneilly.toa.core.utils

import java.time.LocalDate

/**
 * For this [LocalDate], parse the day of the month and calculate the suffix for that day.
 * Example: 1st, 2nd, 3rd, 4th, etc...
 */
@Suppress("MagicNumber")
fun LocalDate.getSuffixForDayOfMonth(): String {
    val dayOfMonth = this.dayOfMonth

    return when (dayOfMonth) {
        in 11..13 -> "th"
        else -> {
            when (dayOfMonth % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        }
    }
}
