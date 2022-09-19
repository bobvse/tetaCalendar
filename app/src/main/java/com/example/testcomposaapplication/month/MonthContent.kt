package com.example.testcomposaapplication.month

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.testcomposaapplication.day.DayState
import com.example.testcomposaapplication.week.WeekContent
import com.example.testcomposaapplication.week.getWeeks
import com.example.testcomposaapplication.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

internal const val DaysOfWeek = 7

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun <T : SelectionState> MonthPager(
    showAdjacentMonths: Boolean,
    selectionState: T,
    monthState: MonthState,
    daysOfWeek: List<DayOfWeek>,
    today: LocalDate,
    modifier: Modifier = Modifier,
    dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
    weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
    val startIndex = 1
    val coroutineScope = rememberCoroutineScope()

    MonthContent(
        showAdjacentMonths = showAdjacentMonths,
        selectionState = selectionState,
        currentMonth = YearMonth.now(),
        today = today,
        daysOfWeek = daysOfWeek,
        dayContent = dayContent,
        weekHeader = weekHeader,
        monthContainer = monthContainer
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun <T : SelectionState> MonthContent(
    showAdjacentMonths: Boolean,
    selectionState: T,
    currentMonth: YearMonth,
    daysOfWeek: List<DayOfWeek>,
    today: LocalDate,
    modifier: Modifier = Modifier,
    dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
    weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            content = { weekHeader(daysOfWeek) },
        )

        monthContainer { paddingValues ->
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(paddingValues)
            ) {
                currentMonth.getWeeks(
                    includeAdjacentMonths = showAdjacentMonths,
                    firstDayOfTheWeek = daysOfWeek.first(),
                    today = today,
                ).forEach { week ->
                    WeekContent(
                        week = week,
                        selectionState = selectionState,
                        dayContent = dayContent,
                    )
                }
            }
        }
    }
}
