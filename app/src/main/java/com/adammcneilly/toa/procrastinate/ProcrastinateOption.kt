package com.adammcneilly.toa.procrastinate

import java.time.LocalDate

sealed class ProcrastinateOption(
    open val date: LocalDate,
) {
    data object Tomorrow : ProcrastinateOption(
        date = LocalDate.now().plusDays(1),
    )

    data object NextWeek : ProcrastinateOption(
        date = LocalDate.now().plusWeeks(1),
    )

    data class Future(
        override val date: LocalDate = LocalDate.now().plusMonths(1),
    ) : ProcrastinateOption(date)
}
