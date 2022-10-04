package com.example.simpletimer.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TimerEntity::class], version = 1)
abstract class TimerDatabase: RoomDatabase() {
    abstract fun timerDao(): TimerDao
}