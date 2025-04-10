package com.example.princesstreatment.data.repository

import com.example.princesstreatment.data.local.dao.EventDao
import com.example.princesstreatment.data.local.entity.Event
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventDao: EventDao
) {
    val eventListFlow: Flow<List<Event>> = eventDao.getAllEvents()

    suspend fun addEvent(title: String, description: String, nextOccurrence: LocalDateTime, recurrenceUnit: ChronoUnit, recurrenceInterval: Long): Long {
        val event = Event(
            title = title,
            description = description,
            nextOccurrence = nextOccurrence,
            recurrenceUnit = recurrenceUnit,
            recurrenceInterval = recurrenceInterval,
        )
        return eventDao.insertEvent(event)
    }

    suspend fun deleteEvent(id: Long) {
        val event = eventDao.getEventById(id)
            ?: throw IllegalArgumentException("Event with id $id not found")
        eventDao.deleteEvent(event)
    }

    suspend fun getEventById(id: Long): Event? {
        return eventDao.getEventById(id)
    }

    suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event)
    }

    suspend fun markEventCompleted(eventId: Long) {
        eventDao.markEventCompleted(eventId, LocalDateTime.now())
    }

    suspend fun activateEvent(eventId: Long) {
        eventDao.updateEventStatus(eventId, true)
    }

    suspend fun deactivateEvent(eventId: Long) {
        eventDao.updateEventStatus(eventId, false)
    }

}