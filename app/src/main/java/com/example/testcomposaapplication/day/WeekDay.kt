package com.example.testcomposaapplication.day

import androidx.compose.runtime.Immutable
import com.example.testcomposaapplication.day.Day
import java.time.LocalDate

@Immutable
internal class WeekDay(
  override val date: LocalDate,
  override val isCurrentDay: Boolean,
  override val isFromCurrentMonth: Boolean
) : Day
