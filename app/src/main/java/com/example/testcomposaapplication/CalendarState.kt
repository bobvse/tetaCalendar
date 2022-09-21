package com.example.testcomposaapplication

import java.util.Date

data class MyCalendarState(
    val year: Int,
    val month: Int,
    val startDate: Date?,
    val endDate: Date?,
    val daysCount: Int
)