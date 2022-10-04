package com.example.simpletimer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface TimerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timerEntity: TimerEntity)

    @Query("select * from ${TimerTables.TIMERS}")
    fun getTimers(): Flowable<List<TimerEntity>>

}