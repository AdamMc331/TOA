package com.adammcneilly.toa.procrastinate

import java.time.LocalDate

/**
 * If we want to be consistent with the rest of the project, we would use a sealed class here
 * that defines an active state, a submitting state, and a submitted state.
 *
 * To just get this up and running, we're putting selected option and isComplete into a data class.
 */
data class ProcrastinateTaskViewState(
    val selectedOption: ProcrastinateOption = ProcrastinateOption.Tomorrow,
    val isComplete: Boolean = false,
    val futureDate: LocalDate = LocalDate.now().plusMonths(1),
)
