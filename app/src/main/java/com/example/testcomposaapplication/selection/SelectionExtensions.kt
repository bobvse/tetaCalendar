package com.example.testcomposaapplication.selection

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
internal fun Collection<LocalDate>.startOrMax() = firstOrNull() ?: LocalDate.MAX

internal fun Collection<LocalDate>.endOrNull() = drop(1).lastOrNull()

@RequiresApi(Build.VERSION_CODES.O)
internal fun Collection<LocalDate>.fillUpTo(date: LocalDate) =
    (0..date.toEpochDay() - first().toEpochDay()).map {
        first().plusDays(it)
    }
