package com.example.simpletimer

import org.junit.Test

class TimerConverterTests {

    @Test
    fun convert_toMillis_justSeconds() {
        val timeMillis = getTimeMillis(0,0,30)
        assert(timeMillis == 30 * 1000L)
    }

    @Test
    fun convert_toMillis_justMinutes() {
        val timeMillis = getTimeMillis(0,30,0)
        assert(timeMillis == 30 * 1000L * 60)
    }

    @Test
    fun convert_toMillis_justHours() {
        val timeMillis = getTimeMillis(2,0,0)
        assert(timeMillis == 2 * 1000L * 60 * 60)
    }
}