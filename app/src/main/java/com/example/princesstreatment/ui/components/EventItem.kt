package com.example.princesstreatment.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.princesstreatment.data.local.entity.Event
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun EventItem(
    event: Event,
    onMarkCompleted: () -> Unit,
    onToggleActive: (Boolean) -> Unit,
    onDeleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Title Row with Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (event.isActive)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Switch(
                    checked = event.isActive,
                    onCheckedChange = onToggleActive
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Last occurrence info (if available)
            if (event.lastOccurrence != null) {
                Text(
                    text = "Last: ${event.lastOccurrence!!.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = if (event.isActive) 1f else 0.6f
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Next occurrence with time until
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Next: ${getExactNexTime(event.nextOccurrence)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = if (event.isActive) 1f else 0.6f
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(${getTimeUntil(event.nextOccurrence)})",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (event.isActive)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Complete button (only enabled if event is active)
                IconButton(
                    onClick = onDeleted,
                    enabled = event.isActive
                ) {
                    Icon(
                        imageVector = Icons.Filled.DeleteForever,
                        contentDescription = "Delete",
                        tint = if (event.isActive)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                }

                IconButton(
                    modifier = Modifier.weight(1f, false),
                    onClick = onMarkCompleted,
                    enabled = event.isActive
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Complete",
                        tint = if (event.isActive)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}

/**
 * Helper function to calculate and format the time until the next occurrence
 */
private fun getTimeUntil(nextOccurrence: LocalDateTime): String {
    val now = LocalDateTime.now()
    val duration = Duration.between(now, nextOccurrence)

    return when {
        duration.isNegative -> "Overdue" // Changed from "overdue" to "Overdue"
        duration.toDays() > 30 -> {
            val months = duration.toDays() / 30
            val remainingDays = duration.toDays() % 30
            val monthText = if (months == 1L) "month" else "months"
            val dayText = if (remainingDays == 1L) "day" else "days"
            "$months $monthText, $remainingDays $dayText"
        }

        duration.toDays() > 0 -> {
            val days = duration.toDays()
            val hours = duration.toHours() % 24
            val dayText = if (days == 1L) "day" else "days"
            val hourText = if (hours == 1L) "hour" else "hours"
            "$days $dayText, $hours $hourText"
        }

        duration.toHours() > 0 -> {
            val hours = duration.toHours()
            val minutes = duration.toMinutes() % 60
            val hourText = if (hours == 1L) "hour" else "hours"
            val minuteText = if (minutes == 1L) "minute" else "minutes"
            "$hours $hourText, $minutes $minuteText"
        }

        duration.toMinutes() > 0 -> {
            val minutes = duration.toMinutes()
            val seconds = duration.seconds % 60
            val minuteText = if (minutes == 1L) "minute" else "minutes"
            val secondText = if (seconds == 1L) "second" else "seconds"
            "$minutes $minuteText, $seconds $secondText"
        }
        else -> "Just now"
    }
}

private fun getExactNexTime(nextOccurrence: LocalDateTime): String {
    val now = LocalDateTime.now()
    val duration = Duration.between(now, nextOccurrence)
    val days = duration.toDays()

    return when {
        days >= 1 && days < 7 -> nextOccurrence.format(DateTimeFormatter.ofPattern("EEE - HH:mm"))
        days.toInt() == 0 -> "Today - ${nextOccurrence.format(DateTimeFormatter.ofPattern("HH:mm"))}"
        else -> nextOccurrence.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }
}