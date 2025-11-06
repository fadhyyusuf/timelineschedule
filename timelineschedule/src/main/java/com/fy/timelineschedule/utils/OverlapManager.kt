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

import com.fy.timelineschedule.model.Appointment
import java.util.Calendar
import java.util.Date

/**
 * Internal class to handle overlap detection and positioning of appointments
 */
internal class OverlapManager {

    data class PositionedAppointment(
        val appointment: Appointment,
        val column: Int,
        val totalColumns: Int,
        val startMinute: Int,
        val endMinute: Int
    )

    /**
     * Analyze appointments and calculate their positions for rendering
     * This handles overlapping appointments by assigning them to columns
     */
    fun calculatePositions(appointments: List<Appointment>): List<PositionedAppointment> {
        if (appointments.isEmpty()) return emptyList()

        // Sort appointments by start time, then by duration (longer first)
        val sorted = appointments.sortedWith(
            compareBy<Appointment> { it.startTime }
                .thenByDescending { it.getDurationMinutes() }
        )

        val positioned = mutableListOf<PositionedAppointment>()
        val groups = findOverlapGroups(sorted)

        for (group in groups) {
            val groupPositions = assignColumnsToGroup(group)
            positioned.addAll(groupPositions)
        }

        return positioned
    }

    /**
     * Find groups of overlapping appointments
     */
    private fun findOverlapGroups(appointments: List<Appointment>): List<List<Appointment>> {
        val groups = mutableListOf<MutableList<Appointment>>()

        for (appointment in appointments) {
            var addedToGroup = false

            // Try to add to existing group
            for (group in groups) {
                if (group.any { it.overlapsWith(appointment) }) {
                    group.add(appointment)
                    addedToGroup = true
                    break
                }
            }

            // Create new group if not added
            if (!addedToGroup) {
                groups.add(mutableListOf(appointment))
            }
        }

        return groups
    }

    /**
     * Assign column positions to appointments in an overlapping group
     */
    private fun assignColumnsToGroup(group: List<Appointment>): List<PositionedAppointment> {
        val positioned = mutableListOf<PositionedAppointment>()
        val columnAssignments = mutableMapOf<Appointment, Int>()

        // Sort by start time
        val sorted = group.sortedBy { it.startTime }

        // Track which columns are occupied at each time point
        val columnOccupancy = mutableMapOf<Int, MutableList<Appointment>>()

        for (appointment in sorted) {
            // Find the first available column
            var column = 0
            while (true) {
                val occupants = columnOccupancy.getOrPut(column) { mutableListOf() }
                val hasConflict = occupants.any { it.overlapsWith(appointment) }

                if (!hasConflict) {
                    // Assign this column
                    occupants.add(appointment)
                    columnAssignments[appointment] = column
                    break
                }
                column++
            }
        }

        // Determine total columns needed
        val totalColumns = if (columnAssignments.isEmpty()) 1
                          else columnAssignments.values.maxOrNull()!! + 1

        // Create positioned appointments
        for (appointment in group) {
            val column = columnAssignments[appointment] ?: 0
            positioned.add(
                PositionedAppointment(
                    appointment = appointment,
                    column = column,
                    totalColumns = totalColumns,
                    startMinute = getMinuteOfDay(appointment.startTime),
                    endMinute = getMinuteOfDay(appointment.endTime)
                )
            )
        }

        return positioned
    }

    /**
     * Get minute of day (0-1439) from a Date
     */
    private fun getMinuteOfDay(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
    }
}

