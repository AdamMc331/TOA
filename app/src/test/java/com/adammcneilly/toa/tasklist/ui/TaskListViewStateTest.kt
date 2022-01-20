package com.adammcneilly.toa.tasklist.ui

import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.UIText
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskListViewStateTest {

    @Test
    fun parseDateStringForToday() {
        val today = LocalDate.now()

        val viewState = TaskListViewState(
            selectedDate = today,
        )

        val expectedString = UIText.ResourceText(R.string.today)
        assertThat(viewState.selectedDateString).isEqualTo(expectedString)
    }

    @Test
    fun parseDateStringForTomorrow() {
        val tomorrow = LocalDate.now().plusDays(1)

        val viewState = TaskListViewState(
            selectedDate = tomorrow,
        )

        val expectedString = UIText.ResourceText(R.string.tomorrow)
        assertThat(viewState.selectedDateString).isEqualTo(expectedString)
    }

    @Test
    fun parseDateStringForFuture() {
        val twoDaysFromNow = LocalDate.now().plusDays(2)

        val viewState = TaskListViewState(
            selectedDate = twoDaysFromNow,
        )

        val expectedDateFormat = "MMM dd"
        val expectedDateString = DateTimeFormatter.ofPattern(expectedDateFormat).format(twoDaysFromNow)
        val expectedString = UIText.StringText(expectedDateString)
        assertThat(viewState.selectedDateString).isEqualTo(expectedString)
    }
}
