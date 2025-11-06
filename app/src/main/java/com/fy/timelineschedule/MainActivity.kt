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

package com.fy.timelineschedule

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fy.timelineschedule.databinding.ActivityMainBinding
import com.fy.timelineschedule.model.Appointment
import com.fy.timelineschedule.model.TimelineConfig
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTimeline()
    }

    private fun setupTimeline() {
        // Create sample appointments similar to the image
        val appointments = createSampleAppointments()

        // Example 1: Using custom time labels (dynamic!)
        val customLabels = listOf(
            "07:00 AM",
            "08:00 AM",
            "09:00 AM",
            "10:00 AM",
            "11:00 AM",
            "12:00 PM",
            "01:00 PM",
            "02:00 PM",
            "03:00 PM",
            "04:00 PM",
            "05:00 PM"
        )

        // Configure timeline with custom time labels
        val config = TimelineConfig(
            hourHeight = 120,
            use24HourFormat = false,
            showGridLines = true,
            gridLineColor = Color.parseColor("#BDBDBD"),
            gridLineWidth = 2f,
            showVerticalDivider = true,
            verticalDividerColor = Color.parseColor("#BDBDBD"),
            verticalDividerWidth = 2f,
            showCurrentTimeIndicator = true,
            currentTimeIndicatorColor = Color.parseColor("#FF5252"),
            currentTimeIndicatorWidth = 3f,
            currentTimeDotRadius = 6f,
            cardCornerRadius = 8f,
            cardElevation = 2f,

            // Set custom time labels (NEW!)
            customTimeLabels = customLabels
        )

        // Set up the timeline view
        binding.timelineView.apply {
            setConfig(config)
            setAppointments(appointments)
            setOnAppointmentClickListener { appointment ->
                Toast.makeText(
                    this@MainActivity,
                    "Clicked: ${appointment.title}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createSampleAppointments(): List<Appointment> {
        val today = Calendar.getInstance()

        return listOf(
            // Abril Lewis - PST header (8:00 AM)
            Appointment(
                id = "1",
                title = "Abril Lewis",
                subtitle = "Physician",
                startTime = createTime(today, 8, 0),
                endTime = createTime(today, 8, 30),
                color = Color.parseColor("#9C27B0"),
                backgroundColor = Color.parseColor("#F3E5F5")
            ),

            // Robert Fox - Checked out (8:00 AM) - Overlaps with Abril
            Appointment(
                id = "2",
                title = "Robert Fox",
                subtitle = "Checked out",
                startTime = createTime(today, 8, 0),
                endTime = createTime(today, 9, 0),
                color = Color.parseColor("#F44336"),
                backgroundColor = Color.parseColor("#FFEBEE")
            ),

            // Cody Fisher - Checked in (9:00 AM)
            Appointment(
                id = "3",
                title = "Cody Fisher",
                subtitle = "Checked in",
                startTime = createTime(today, 9, 0),
                endTime = createTime(today, 10, 0),
                color = Color.parseColor("#E91E63"),
                backgroundColor = Color.parseColor("#FCE4EC")
            ),

            // Annette Black - Checked in (9:20 AM) - Overlaps with Cody
            Appointment(
                id = "4",
                title = "Annette Black",
                subtitle = "Checked in",
                startTime = createTime(today, 9, 20),
                endTime = createTime(today, 10, 0),
                color = Color.parseColor("#9C27B0"),
                backgroundColor = Color.WHITE
            ),

            // Kathryn Murphy - Checked in (10:00 AM)
            Appointment(
                id = "5",
                title = "Kathryn Murphy",
                subtitle = "Checked in",
                startTime = createTime(today, 10, 0),
                endTime = createTime(today, 11, 0),
                color = Color.parseColor("#673AB7"),
                backgroundColor = Color.WHITE
            ),

            // Brooklyn Simmon - Confirmed (10:30 AM) - Overlaps with Kathryn
            Appointment(
                id = "6",
                title = "Brooklyn Simmon",
                subtitle = "Confirmed",
                startTime = createTime(today, 10, 30),
                endTime = createTime(today, 11, 30),
                color = Color.parseColor("#2196F3"),
                backgroundColor = Color.WHITE
            ),

            // Arlene McCoy - Confirmed (11:00 AM)
            Appointment(
                id = "7",
                title = "Arlene McCoy",
                subtitle = "Confirmed",
                startTime = createTime(today, 11, 0),
                endTime = createTime(today, 11, 30),
                color = Color.parseColor("#00BCD4"),
                backgroundColor = Color.WHITE
            ),

            // Seminar (12:00 PM - 1:00 PM)
            Appointment(
                id = "8",
                title = "Seminar",
                subtitle = null,
                startTime = createTime(today, 12, 0),
                endTime = createTime(today, 13, 0),
                color = Color.parseColor("#607D8B"),
                backgroundColor = Color.parseColor("#F5F5F5")
            )
        )
    }

    private fun createTime(calendar: Calendar, hour: Int, minute: Int): java.util.Date {
        val cal = calendar.clone() as Calendar
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }
}