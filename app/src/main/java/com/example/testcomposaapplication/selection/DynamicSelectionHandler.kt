package com.example.testcomposaapplication.selection

import java.time.LocalDate

object DynamicSelectionHandler {
    fun calculateNewSelection(
        date: LocalDate,
        selection: List<LocalDate>,
        selectionMode: SelectionMode,
    ): List<LocalDate> = when (selectionMode) {
        SelectionMode.None -> emptyList()
        SelectionMode.Single -> if (date == selection.firstOrNull()) {
            emptyList()
        } else {
            listOf(date)
        }
    }
}
