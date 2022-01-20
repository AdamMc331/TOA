package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.tasklist.domain.model.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * All of the necessary configurations for the task list screen UI.
 */
data class TaskListViewState(
    val showLoading: Boolean = true,
    val tasks: List<Task>? = null,
    val errorMessage: UIText? = null,
    val selectedDate: LocalDate = LocalDate.now(),
) {

    val selectedDateString: UIText
        get() {
            val isToday = (selectedDate == LocalDate.now())
            val isTomorrow = (selectedDate == LocalDate.now().plusDays(1))

            return when {
                isToday -> UIText.ResourceText(R.string.today)
                isTomorrow -> UIText.ResourceText(R.string.tomorrow)
                else -> {
                    val uiDateFormat = "MMM dd"

                    val uiString = DateTimeFormatter.ofPattern(uiDateFormat).format(selectedDate)

                    UIText.StringText(uiString)
                }
            }
        }
}
