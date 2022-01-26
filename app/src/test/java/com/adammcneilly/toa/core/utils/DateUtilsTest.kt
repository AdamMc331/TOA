package com.adammcneilly.toa.core.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class DateUtilsTest {

    @Test
    fun parseStSuffixes() {
        listOf(1, 21, 31)
            .convertToSuffixes()
            .forEach { suffix ->
                assertThat(suffix).isEqualTo("st")
            }
    }

    @Test
    fun parseNdSuffixes() {
        listOf(2, 22)
            .convertToSuffixes()
            .forEach { suffix ->
                assertThat(suffix).isEqualTo("nd")
            }
    }

    @Test
    fun parseRdSuffixes() {
        listOf(3, 23)
            .convertToSuffixes()
            .forEach { suffix ->
                assertThat(suffix).isEqualTo("rd")
            }
    }

    @Test
    fun parseThSuffixes() {
        listOf(
            (4..9),
            (10..20),
            (24..30),
        )
            .flatten()
            .convertToSuffixes()
            .forEach { suffix ->
                assertThat(suffix).isEqualTo("th")
            }
    }
}

private fun List<Int>.convertToSuffixes(): List<String> {
    return this
        .map { day ->
            LocalDate.of(2020, Month.JANUARY, day).getSuffixForDayOfMonth()
        }
}
