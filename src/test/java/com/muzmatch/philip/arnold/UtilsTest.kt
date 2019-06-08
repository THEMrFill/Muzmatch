package com.muzmatch.philip.arnold

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Assert.*
import org.junit.Test

class UtilsTest {
    @Test
    fun `UTC format GMT datetime`() {
        val date = setupDate(2019, 5, 20, 16, 20, 0, 0)
        assertEquals(Utils.toUTC(date), "2019-05-20 16:20:00")
    }

    @Test
    fun `UTC format BST datetime`() {
        val date = setupDate(2019, 5, 20, 16, 20, 0, +1)
        assertEquals(Utils.toUTC(date), "2019-05-20 15:20:00")
    }

    @Test
    fun `UTC format EST datetime`() {
        val date = setupDate(2019, 5, 20, 16, 20, 0, -5)
        assertEquals(Utils.toUTC(date), "2019-05-20 21:20:00")
    }

    @Test
    fun `Zero seconds betwee the same times`() {
        val date = setupDate(2019, 5, 20, 16, 20, 0, 0)
        assertTrue(Utils.differenceInSeconds(date, date) == 0)
    }

    @Test
    fun `10 seconds betwee sates`() {
        val date = setupDate(2019, 5, 20, 16, 20, 0, 0)
        val date2 = setupDate(2019, 5, 20, 16, 20, 10, 0)
        assertTrue(Utils.differenceInSeconds(date, date2) == 10)
    }

    @Test
    fun `60 seconds betwee dates`() {
        val date = setupDate(2019, 5, 20, 16, 20, 0, 0)
        val date2 = setupDate(2019, 5, 20, 16, 21, 0, 0)
        assertTrue(Utils.differenceInSeconds(date, date2) == 60)
    }

    @Test
    fun `Less than 20 seconds betwee dates`() {
        val date = setupDate(2019, 5, 20, 16, 20, 0, 0)
        val date2 = setupDate(2019, 5, 20, 16, 20, 10, 0)
        assertTrue(Utils.differenceOf20Seconds(date, date2))
    }
    @Test
    fun `More than 20 seconds betwee dates`() {
        val date = setupDate(2019, 5, 20, 16, 20, 0, 0)
        val date2 = setupDate(2019, 5, 20, 16, 50, 0, 0)
        assertFalse(Utils.differenceOf20Seconds(date, date2))
    }


    @Test
    fun `Less than an hour betwee dates`() {
        val date = setupDate(2019, 5, 20, 16, 20, 0, 0)
        val date2 = setupDate(2019, 5, 20, 16, 40, 0, 0)
        assertTrue(Utils.differenceOfAnHour(date, date2))
    }
    @Test
    fun `More than an hour betwee dates`() {
        val date = setupDate(2019, 5, 20, 16, 20, 0, 0)
        val date2 = setupDate(2019, 5, 20, 17, 30, 0, 0)
        assertFalse(Utils.differenceOfAnHour(date, date2))
    }

    // this exists to create a DateTime object with a time & tome zone, the default Java data function
    // is rather messy with dates, times & time zones - note that the offset is in place as the JodaTime
    // time zone functionality makes duplicates of zone names, so gets messy,
    // i.e. BST is British Summer Time, Bangladesh Standard Time, Bougainville Standard Time
    // so sending the offset is more reliable for testing

    fun setupDate(year: Int, mon: Int, day: Int, hour: Int, min: Int, sec: Int, timezone: Int): DateTime {
        // separated into variables for debugging
        val dtz = DateTimeZone.forOffsetHours(timezone)
        val dt = DateTime(year, mon, day, hour, min, sec, dtz)
        return dt
    }
}