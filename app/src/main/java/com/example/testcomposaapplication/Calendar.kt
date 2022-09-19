package com.example.testcomposaapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.testcomposaapplication.day.DayState
import com.example.testcomposaapplication.day.DefaultDay
import com.example.testcomposaapplication.header.DefaultMonthHeader
import com.example.testcomposaapplication.header.MonthState
import com.example.testcomposaapplication.month.DaysOfWeek
import com.example.testcomposaapplication.month.MonthContent
import com.example.testcomposaapplication.month.MonthPager
import com.example.testcomposaapplication.selection.DynamicSelectionState
import com.example.testcomposaapplication.selection.SelectionMode
import com.example.testcomposaapplication.selection.SelectionState
import com.example.testcomposaapplication.week.DefaultWeekHeader
import com.example.testcomposaapplication.week.rotateRight
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

@Stable
class CalendarState<T : SelectionState>(
    val monthState: MonthState,
    val selectionState: T,
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectableCalendar(
    modifier: Modifier = Modifier,
    firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
    today: LocalDate = LocalDate.now(),
    showAdjacentMonths: Boolean = true,
    horizontalSwipeEnabled: Boolean = true,
    calendarState: CalendarState<DynamicSelectionState> = rememberSelectableCalendarState(),
    dayContent: @Composable BoxScope.(DayState<DynamicSelectionState>) -> Unit = { DefaultDay(it) },
    monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
    weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(it) },
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
        Box { content(PaddingValues()) }
    },
) {
    Calendar(
        modifier = modifier,
        firstDayOfWeek = firstDayOfWeek,
        today = today,
        showAdjacentMonths = showAdjacentMonths,
        horizontalSwipeEnabled = horizontalSwipeEnabled,
        calendarState = calendarState,
        dayContent = dayContent,
        monthHeader = monthHeader,
        weekHeader = weekHeader,
        monthContainer = monthContainer
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
public fun <T : SelectionState> Calendar(
    calendarState: CalendarState<T>,
    modifier: Modifier = Modifier,
    firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
    today: LocalDate = LocalDate.now(),
    showAdjacentMonths: Boolean = true,
    horizontalSwipeEnabled: Boolean = true,
    dayContent: @Composable BoxScope.(DayState<T>) -> Unit = { DefaultDay(it) },
    monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
    weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(it) },
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
        Box { content(PaddingValues()) }
    },
) {
    val daysOfWeek = remember(firstDayOfWeek) {
        DayOfWeek.values().rotateRight(DaysOfWeek - firstDayOfWeek.ordinal)
    }

    Column(
        modifier = modifier,
    ) {
        monthHeader(calendarState.monthState)
        if (horizontalSwipeEnabled) {
            MonthPager(
                showAdjacentMonths = showAdjacentMonths,
                selectionState = calendarState.selectionState,
                today = today,
                daysOfWeek = daysOfWeek,
                dayContent = dayContent,
                weekHeader = weekHeader,
                monthContainer = monthContainer,
            )
        } else {
            MonthContent(
                currentMonth = calendarState.monthState.currentMonth,
                showAdjacentMonths = showAdjacentMonths,
                selectionState = calendarState.selectionState,
                today = today,
                daysOfWeek = daysOfWeek,
                dayContent = dayContent,
                weekHeader = weekHeader,
                monthContainer = monthContainer,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun rememberSelectableCalendarState(
    initialMonth: YearMonth = YearMonth.now(),
    initialSelection: List<LocalDate> = emptyList(),
    initialSelectionMode: SelectionMode = SelectionMode.Single,
    confirmSelectionChange: (newValue: List<LocalDate>) -> Boolean = { true },
    monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
        MonthState(initialMonth = initialMonth)
    },
    selectionState: DynamicSelectionState = rememberSaveable(
        saver = DynamicSelectionState.Saver(confirmSelectionChange),
    ) {
        DynamicSelectionState(confirmSelectionChange, initialSelection, initialSelectionMode)
    },
): CalendarState<DynamicSelectionState> = remember { CalendarState(monthState, selectionState) }

