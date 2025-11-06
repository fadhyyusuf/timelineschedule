/*
 * Timeline Schedule - Android Library
 *
 * ⚠️ AI-GENERATED PROJECT DISCLAIMER
 * This project was created with the assistance of Artificial Intelligence (AI).
 * While the code has been reviewed and tested, users should verify functionality
 * for their specific use cases.
 *
 * Copyright (c) 2025 Fadhy Yusuf
 * Licensed under the MIT License
 */

package com.fy.timelineschedule.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Utility class for time and date formatting
 */
object TimeUtils {

    /**
     * Format time for display in the timeline
     */
    fun formatTime(date: Date, use24Hour: Boolean = false): String {
        val pattern = if (use24Hour) "HH:mm" else "hh:mm a"
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return format.format(date)
    }

    /**
     * Format time with timezone
     */
    fun formatTimeWithZone(date: Date, use24Hour: Boolean = false, timezone: String? = null): String {
        val pattern = if (use24Hour) "HH:mm z" else "hh:mm a z"
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return format.format(date)
    }

    /**
     * Get hour from Date
     */
    fun getHour(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    /**
     * Get minute from Date
     */
    fun getMinute(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.MINUTE)
    }

    /**
     * Get minute of day (0-1439)
     */
    fun getMinuteOfDay(date: Date): Int {
        return getHour(date) * 60 + getMinute(date)
    }

    /**
     * Create a Date from hour and minute
     */
    fun createTime(hour: Int, minute: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    /**
     * Check if two dates are on the same day
     */
    fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    /**
     * Format date for header display
     */
    fun formatDate(date: Date): String {
        val format = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        return format.format(date)
    }

    /**
     * Get day of week name
     */
    fun getDayOfWeek(date: Date): String {
        val format = SimpleDateFormat("EEEE", Locale.getDefault())
        return format.format(date)
    }

    /**
     * Check if date is today
     */
    fun isToday(date: Date): Boolean {
        return isSameDay(date, Date())
    }
}

