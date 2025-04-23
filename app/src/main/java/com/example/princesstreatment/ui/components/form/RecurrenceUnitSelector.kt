package com.example.princesstreatment.ui.components.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurrenceUnitSelector(
    recurrenceValue: String,
    onRecurrenceValueChange: (String) -> Unit,
    selectedUnit: ChronoUnit,
    onUnitSelected: (ChronoUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    val recurrenceUnits = listOf(
        ChronoUnit.DAYS to "Days",
        ChronoUnit.WEEKS to "Weeks",
        ChronoUnit.MONTHS to "Months"
    )

    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Recurrence Pattern",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Interval value
                OutlinedTextField(
                    value = recurrenceValue,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
                            onRecurrenceValueChange(newValue)
                        }
                    },
                    label = { Text("Every") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(0.3f),
                    singleLine = true
                )

                // Unit selector
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(start = 8.dp)
                ) {
                    OutlinedTextField(
                        value = recurrenceUnits.find { it.first == selectedUnit }?.second ?: "",
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier.menuAnchor(PrimaryNotEditable),
                        label = { Text("Unit") },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        recurrenceUnits.forEach { (unit, name) ->
                            DropdownMenuItem(
                                text = { Text(name) },
                                onClick = {
                                    onUnitSelected(unit)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}