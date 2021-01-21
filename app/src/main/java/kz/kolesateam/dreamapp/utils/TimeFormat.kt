package kz.kolesateam.dreamapp.utils

import java.util.*
import java.util.concurrent.TimeUnit

class TimeFormat {

    fun getTimeAgo(duration: Long): String {
        val nowDate = Date()
        var seconds: Long = TimeUnit.MILLISECONDS.toSeconds(nowDate.time - duration)
        var minutes: Long = TimeUnit.MILLISECONDS.toMinutes(nowDate.time - duration)
        var hours: Long = TimeUnit.MILLISECONDS.toHours(nowDate.time - duration)
        var days: Long = TimeUnit.MILLISECONDS.toDays(nowDate.time - duration)


        when {
            seconds<60 -> return "только что"
            minutes.toInt() == 1 -> return "минуту назад"
            minutes in 2..59 -> return  "$minutes минут назад"
            hours.toInt() == 1 -> return "час назад"
            hours in 2..23 -> return "$hours час(а) назад"
            days.toInt() == 1 -> return "1 день назад"
            else -> return "$days дней(дня) назад"
        }
    }
}