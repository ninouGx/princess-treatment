package com.example.princesstreatment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var lastOccurrence: LocalDateTime? = null,
    var nextOccurrence: LocalDateTime,
    val recurrenceUnit: ChronoUnit,
    val recurrenceInterval: Long,
    val isActive: Boolean = true,
)