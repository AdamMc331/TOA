package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.core.utils.getSuffixForDayOfMonth
import com.adammcneilly.toa.tasklist.domain.model.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * All of the necessary configurations for the task list screen UI.
 */
data class TaskListViewState(
    val showLoading: Boolean = true,
    val incompleteTasks: List<Task>? = null,
    val completeTasks: List<Task>? = null,
    val errorMessage: UIText? = null,
    val selectedDate: LocalDate = LocalDate.now(),
) {

    val selectedDateString: UIText
        get() {
            val today = LocalDate.now()
            val isYesterday = (selectedDate == today.minusDays(1))
            val isToday = (selectedDate == today)
            val isTomorrow = (selectedDate == today.plusDays(1))

            return when {
                isYesterday -> UIText.ResourceText(R.string.yesterday)
                isToday -> UIText.ResourceText(R.string.today)
                isTomorrow -> UIText.ResourceText(R.string.tomorrow)
                else -> {
                    val uiDateFormat = "MMM d"
                    val suffix = selectedDate.getSuffixForDayOfMonth()

                    val dateString = DateTimeFormatter.ofPattern(uiDateFormat).format(selectedDate)

                    UIText.StringText("$dateString$suffix")
                }
            }
        }
}
