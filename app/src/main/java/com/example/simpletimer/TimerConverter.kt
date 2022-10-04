package com.example.simpletimer

data class CountDownTime(val hour: Int, val minute: Int, val second: Int)

fun getCountDownTime(milliseconds: Long): CountDownTime {
    val second = (milliseconds / 1000) % 60
    val minute = (milliseconds / (1000 * 60)) % 60
    val hour = (milliseconds / (1000 * 60 * 60))
    return CountDownTime(hour.toInt(), minute.toInt(), second.toInt())
}

fun getTimeMillis(hour: Int, minute: Int, second: Int): Long {
    return second*1000L + minute*1000L*60 + hour*1000L*60*60
}

fun getDisplayTime(milliseconds: Long): String {
    val cdt = getCountDownTime(milliseconds)
    return "${cdt.hour}:${convertToDisplayTime(cdt.minute)}:${convertToDisplayTime(cdt.second)}"
}

private fun convertToDisplayTime(value: Int): String {
    return if (value >= 10) value.toString() else "0$value"
}