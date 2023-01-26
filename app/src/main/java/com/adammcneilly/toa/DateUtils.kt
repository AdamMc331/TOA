package com.adammcneilly.toa

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun LocalDate.toEpochMillis(
    zoneId: ZoneId = ZoneId.systemDefault(),
): Long {
    return this.atStartOfDay()
        .atZone(zoneId)
        .toInstant()
        .toEpochMilli()
}

fun LocalDate.toEpochMillisUTC(): Long = this.toEpochMillis(ZoneId.of("UTC"))

fun Long.toLocalDate(
    zoneId: ZoneId = ZoneId.systemDefault(),
): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(zoneId)
        .toLocalDate()
}

fun Long.toLocalDateUTC(): LocalDate = this.toLocalDate(ZoneId.of("UTC"))
