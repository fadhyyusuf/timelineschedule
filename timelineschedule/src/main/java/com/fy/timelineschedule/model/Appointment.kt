package com.fy.timelineschedule.model

import android.graphics.Color
import java.util.Date

/**
 * Data class representing an appointment in the timeline
 *
 * @property id Unique identifier for the appointment
 * @property title Title of the appointment
 * @property subtitle Optional subtitle (e.g., "Physician", "Confirmed")
 * @property startTime Start time of the appointment
 * @property endTime End time of the appointment
 * @property color Color of the appointment indicator (default is blue)
 * @property avatarUrl Optional URL for avatar image
 * @property avatarDrawableRes Optional drawable resource for avatar
 * @property backgroundColor Background color of the appointment card
 * @property textColor Text color of the appointment
 */
data class Appointment(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val startTime: Date,
    val endTime: Date,
    val color: Int = Color.parseColor("#2196F3"),
    val avatarUrl: String? = null,
    val avatarDrawableRes: Int? = null,
    val backgroundColor: Int = Color.WHITE,
    val textColor: Int = Color.BLACK
) {
    /**
     * Check if this appointment overlaps with another appointment
     */
    fun overlapsWith(other: Appointment): Boolean {
        return startTime.before(other.endTime) && endTime.after(other.startTime)
    }

    /**
     * Get duration in minutes
     */
    fun getDurationMinutes(): Long {
        return (endTime.time - startTime.time) / (1000 * 60)
    }
}

