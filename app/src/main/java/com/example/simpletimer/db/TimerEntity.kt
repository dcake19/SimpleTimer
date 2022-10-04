package com.example.simpletimer.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TimerTables.TIMERS)
data class TimerEntity(
    @ColumnInfo(name = TimerColumns.NAME) var name: String,
    @ColumnInfo(name = TimerColumns.TIME_MILLIS) var timeMillis: Long,
    @PrimaryKey(autoGenerate = true) var id: Long = 0
)
