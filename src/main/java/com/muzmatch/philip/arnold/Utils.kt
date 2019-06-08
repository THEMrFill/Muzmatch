package com.muzmatch.philip.arnold

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Seconds
import org.joda.time.format.DateTimeFormat

object Utils {
    val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    val DEFAULT_TIME_ZONE = "UTC"

    fun toUTC(date: DateTime, dateFormat: String = DEFAULT_DATE_TIME_FORMAT, timeZone: DateTimeZone = DateTimeZone.forID(DEFAULT_TIME_ZONE)): String {
        val output = date.withZone(timeZone).toString(dateFormat)
        return output
    }

    fun fromUTC(value: String, dateFormat: String = DEFAULT_DATE_TIME_FORMAT, timeZone: DateTimeZone = DateTimeZone.forID(DEFAULT_TIME_ZONE)) : DateTime {
        val formatter = DateTimeFormat.forPattern(DEFAULT_DATE_TIME_FORMAT)
        val dt = formatter.parseDateTime(value)
        return dt
    }

    fun differenceInSeconds(current: DateTime, previous: DateTime): Int {
        val diff = Seconds.secondsBetween(previous, current)
        return Math.abs(diff.seconds)
    }

    fun differenceOf20Seconds(current: DateTime, previous: DateTime): Boolean {
        return differenceInSeconds(current, previous) <= 20
    }
    fun differenceOf20Seconds(current: String, previous: String): Boolean {
        return differenceOf20Seconds(fromUTC(current), fromUTC(previous))
    }

    fun differenceOfAnHour(current: DateTime, previous: DateTime): Boolean {
        return differenceInSeconds(current, previous) <= 60 * 60
    }
    fun differenceOfAnHour(current: String, previous: String): Boolean {
        return differenceOfAnHour(fromUTC(current), fromUTC(previous))
    }

    fun formatDateTimeLocal(datetime: DateTime): String {
        val formatter = DateTimeFormat.forPattern("EEEE HH:mm")
        val dt = formatter.print(datetime)
        return dt
    }
}