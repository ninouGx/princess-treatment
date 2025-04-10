package com.example.princesstreatment.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.princesstreatment.data.local.converters.DateTimeConverters
import com.example.princesstreatment.data.local.dao.EventDao
import com.example.princesstreatment.data.local.entity.Event

@Database(
    entities = [Event::class],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
@TypeConverters(DateTimeConverters::class)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "princess_treatment_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}