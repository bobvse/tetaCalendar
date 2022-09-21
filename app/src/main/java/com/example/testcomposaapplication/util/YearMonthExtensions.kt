package com.example.testcomposaapplication.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
internal operator fun YearMonth.dec() = this.minusMonths(1)

@RequiresApi(Build.VERSION_CODES.O)
internal operator fun YearMonth.inc() = this.plusMonths(1)
