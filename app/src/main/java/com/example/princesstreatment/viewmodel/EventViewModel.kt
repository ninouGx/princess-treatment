package com.example.princesstreatment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.princesstreatment.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    val allEvents = repository.eventListFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        //loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            repository.addEvent(
                title = "Sample Event",
                description = "This is a sample event.",
                nextOccurrence = LocalDateTime.now().plusDays(1),
                recurrenceUnit = ChronoUnit.DAYS,
                recurrenceInterval = 1
            )

            repository.addEvent(
                title = "Another Event",
                description = "This is another sample event.",
                nextOccurrence = LocalDateTime.now().plusDays(2),
                recurrenceUnit = ChronoUnit.DAYS,
                recurrenceInterval = 2
            )

            repository.addEvent(
                title = "Weekly Event",
                description = "This is a weekly event.",
                nextOccurrence = LocalDateTime.now().plusWeeks(1),
                recurrenceUnit = ChronoUnit.WEEKS,
                recurrenceInterval = 1
            )
        }
    }

    // Functions for handling events
    fun addEvent(
        title: String,
        description: String,
        nextOccurrence: LocalDateTime,
        recurrenceUnit: ChronoUnit,
        recurrenceInterval: Long
    ) {
        viewModelScope.launch {
            repository.addEvent(
                title,
                description,
                nextOccurrence,
                recurrenceUnit,
                recurrenceInterval
            )
        }
    }

    fun deleteEvent(eventId: Long) {
        viewModelScope.launch {
            repository.deleteEvent(eventId)
        }
    }

    fun markEventCompleted(eventId: Long) {
        viewModelScope.launch {
            repository.markEventCompleted(eventId)
        }
    }

    fun activateEvent(eventId: Long) {
        viewModelScope.launch {
            repository.activateEvent(eventId)
        }
    }

    fun deactivateEvent(eventId: Long) {
        viewModelScope.launch {
            repository.deactivateEvent(eventId)
        }
    }
}