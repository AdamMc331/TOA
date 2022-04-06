package com.adammcneilly.toa

import java.time.LocalDate
import java.time.ZoneId

fun LocalDate.toEpochMillis(): Long {
    return this.atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}
