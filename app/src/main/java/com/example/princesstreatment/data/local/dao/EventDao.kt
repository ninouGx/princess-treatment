package com.example.princesstreatment.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.princesstreatment.data.local.entity.Event
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: Event): Long

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEventById(id: Long): Event?

    @Update
    suspend fun updateEvent(event: Event)

    @Query("UPDATE events SET isActive = :isActive WHERE id = :id")
    suspend fun updateEventStatus(id: Long, isActive: Boolean)

    @Transaction
    suspend fun markEventCompleted(eventId: Long, completionTime: LocalDateTime) {
        val event = getEventById(eventId) ?: return

        event.lastOccurrence = completionTime
        event.nextOccurrence = completionTime.plus(event.recurrenceInterval, event.recurrenceUnit)
        //event.completionCount++

        updateEvent(event)
    }

    @Query("SELECT * FROM events WHERE isActive = 1")
    fun getActiveEvents(): Flow<List<Event>>

}