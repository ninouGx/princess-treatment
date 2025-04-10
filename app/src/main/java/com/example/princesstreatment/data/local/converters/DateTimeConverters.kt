package com.example.princesstreatment.data.local.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class DateTimeConverters {
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun fromChronoUnit(chronoUnit: ChronoUnit): String {
        return chronoUnit.name
    }

    @TypeConverter
    fun toChronoUnit(value: String): ChronoUnit {
        return ChronoUnit.valueOf(value)
    }
}