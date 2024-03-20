package com.adammcneilly.toa.procrastinate

import java.time.LocalDate

/**
 * If we want to be consistent with the rest of the project, we would use a sealed class here
 * that defines an active state, a submitting state, and a submitted state.
 *
 * To just get this up and running, we're putting selected option and isComplete into a data class.
 *
 * @param[futureDate] We store this in our viewmodel state so that it persists. The reason we have this as a separate
 * field is because the [selectedOption] only needs to use this if we're pushing out into the future.
 * And we don't want a toggle between tomorrow/next week options to impact this value.
 */
data class ProcrastinateTaskViewState(
    val selectedOption: ProcrastinateOption = ProcrastinateOption.Tomorrow,
    val isComplete: Boolean = false,
    val futureDate: LocalDate = LocalDate.now().plusMonths(1),
)
