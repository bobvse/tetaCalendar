package com.example.testcomposaapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectableCalendarSample() {
    val calendarState = rememberSelectableCalendarState()

    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        SelectableCalendar()

        SelectionControls(selectionState = calendarState.selectionState)
    }
}

@Composable
private fun SelectionControls(
    selectionState: DynamicSelectionState,
) {
    Text(
        text = "Calendar Selection Mode",
        style = MaterialTheme.typography.h5,
    )
    SelectionMode.values().forEach { selectionMode ->
        Row(modifier = Modifier.fillMaxWidth()) {
            RadioButton(
                selected = selectionState.selectionMode == selectionMode,
                onClick = { selectionState.selectionMode = selectionMode }
            )
            Text(text = selectionMode.name)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }

    Text(
        text = "Selection: ${selectionState.selection.joinToString { it.toString() }}",
        style = MaterialTheme.typography.h6,
    )
}