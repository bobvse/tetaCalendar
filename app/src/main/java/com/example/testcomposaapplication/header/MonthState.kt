package com.example.testcomposaapplication.header

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import java.time.YearMonth

fun MonthState(initialMonth: YearMonth): MonthState = MonthStateImpl(initialMonth)

@Stable
interface MonthState {
    var currentMonth: YearMonth

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        @Suppress("FunctionName") // Factory function
        fun Saver(): Saver<MonthState, String> = Saver(
            save = { it.currentMonth.toString() },
            restore = { MonthState(YearMonth.parse(it)) }
        )
    }
}

@Stable
private class MonthStateImpl(
    initialMonth: YearMonth,
) : MonthState {
    private var _currentMonth by mutableStateOf<YearMonth>(initialMonth)
    override var currentMonth: YearMonth
        get() = _currentMonth
        set(value) {
            _currentMonth = value
        }
}
