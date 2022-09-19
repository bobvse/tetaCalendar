package com.example.testcomposaapplication.week

import androidx.compose.runtime.Immutable
import com.example.testcomposaapplication.day.Day

@Immutable
internal data class Week(
    val isFirstWeekOfTheMonth: Boolean = false,
    val days: List<Day>,
)
