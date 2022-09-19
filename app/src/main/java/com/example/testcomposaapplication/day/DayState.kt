package com.example.testcomposaapplication.day

import androidx.compose.runtime.Stable
import com.example.testcomposaapplication.selection.SelectionState

@Stable
 data class DayState<T : SelectionState>(
    private val day: Day,
    val selectionState: T,
) : Day by day
