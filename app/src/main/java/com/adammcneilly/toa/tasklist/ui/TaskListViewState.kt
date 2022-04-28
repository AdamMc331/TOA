package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.models.Task
import com.adammcneilly.toa.core.ui.AlertMessage
import com.adammcneilly.toa.core.ui.UIText
import com.adammcneilly.toa.core.utils.getSuffixForDayOfMonth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * All of the necessary configurations for the task list screen UI.
 *
 * @property[errorMessage] Unlike [alertMessage], this is used to signify a problem requesting data
 * such as an inability to fetch tasks. It will be shown within the content of the screen.
 * @property[taskToReschedule] If this is not null, this is the [Task] entity
 * that the user is currently rescheduling.
 * @property[alertMessage] Unlike [errorMessage], this message will be presented to the user in some
 * alerting fashion like a snackbar, dialog, or other way of grabbing their attention. This signifies
 * a problem processing user input.
 */
data class TaskListViewState(
    val showLoading: Boolean = true,
    val incompleteTasks: List<Task>? = null,
    val completedTasks: List<Task>? = null,
    val errorMessage: UIText? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val taskToReschedule: Task? = null,
    val alertMessage: AlertMessage? = null,
) {

    /**
     * As long as we are not in a loading or error scenario, we can show the task list (or empty state).
     */
    val showTasks: Boolean
        get() = !showLoading && errorMessage == null

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
                    val suffix = selectedDate.getSuffixForDayOfMonth()

                    val dateString = DateTimeFormatter.ofPattern(DATE_HEADER_FORMAT).format(selectedDate)

                    UIText.StringText("$dateString$suffix")
                }
            }
        }

    companion object {
        const val DATE_HEADER_FORMAT = "MMM d"
    }
}
