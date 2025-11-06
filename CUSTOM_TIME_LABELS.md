# Custom Time Labels Feature

> **⚠️ AI-Generated Project Disclaimer**  
> This project was created with the assistance of Artificial Intelligence (AI). While the code has been reviewed and tested, users should verify functionality for their specific use cases.

## Overview

The Custom Time Labels feature allows you to replace the default hourly time labels (e.g., "08:00 AM", "09:00 AM") with your own custom labels. This is useful for various scenarios where standard time labels don't fit your use case.

## Use Cases

### 1. **Custom Time Periods**
Display non-standard time intervals:
```kotlin
listOf(
    "07:00 AM",
    "07:30 AM",
    "08:00 AM",
    "08:30 AM",
    // ...
)
```

### 2. **Named Time Blocks**
Use descriptive names instead of times:
```kotlin
listOf(
    "Early Morning",
    "Morning",
    "Late Morning",
    "Noon",
    "Afternoon",
    "Evening",
    "Night"
)
```

### 3. **Event-Based Labels**
Label times by events:
```kotlin
listOf(
    "Registration",
    "Opening Ceremony",
    "Keynote Speech",
    "Break",
    "Workshop A",
    "Workshop B",
    "Lunch",
    "Panel Discussion",
    "Closing"
)
```

### 4. **Custom Schedule Labels**
For schools, businesses, or special events:
```kotlin
listOf(
    "Period 1",
    "Period 2",
    "Recess",
    "Period 3",
    "Period 4",
    "Lunch",
    "Period 5",
    "Period 6"
)
```

## How to Use

### Basic Implementation

```kotlin
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

val config = TimelineConfig(
    hourHeight = 120,
    customTimeLabels = customLabels
)

binding.timelineView.setConfig(config)
```

### Complete Example

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTimelineWithCustomLabels()
    }

    private fun setupTimelineWithCustomLabels() {
        // Define custom time labels
        val customLabels = listOf(
            "Early Morning (6-7 AM)",
            "Morning (7-8 AM)",
            "Mid Morning (8-9 AM)",
            "Late Morning (9-10 AM)",
            "Pre-Noon (10-11 AM)",
            "Noon (11-12 PM)",
            "Early Afternoon (12-1 PM)",
            "Afternoon (1-2 PM)",
            "Mid Afternoon (2-3 PM)",
            "Late Afternoon (3-4 PM)",
            "Early Evening (4-5 PM)",
            "Evening (5-6 PM)"
        )

        // Create appointments
        val appointments = createSampleAppointments()

        // Configure timeline with custom labels
        val config = TimelineConfig(
            hourHeight = 120,
            use24HourFormat = false,
            showGridLines = true,
            gridLineColor = Color.parseColor("#E0E0E0"),
            showCurrentTimeIndicator = true,
            currentTimeIndicatorColor = Color.parseColor("#FF5252"),
            customTimeLabels = customLabels  // Set custom labels here
        )

        // Apply configuration and appointments
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
            Appointment(
                id = "1",
                title = "Morning Workout",
                subtitle = "Gym Session",
                startTime = createTime(today, 7, 0),
                endTime = createTime(today, 8, 0),
                color = Color.parseColor("#4CAF50"),
                backgroundColor = Color.WHITE
            ),
            Appointment(
                id = "2",
                title = "Team Meeting",
                subtitle = "Weekly Sync",
                startTime = createTime(today, 9, 0),
                endTime = createTime(today, 10, 0),
                color = Color.parseColor("#2196F3"),
                backgroundColor = Color.WHITE
            ),
            Appointment(
                id = "3",
                title = "Lunch Break",
                subtitle = null,
                startTime = createTime(today, 12, 0),
                endTime = createTime(today, 13, 0),
                color = Color.parseColor("#FF9800"),
                backgroundColor = Color.parseColor("#FFF3E0")
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
```

## Important Notes

### Label Count and Spacing

- The number of custom labels determines the number of time blocks displayed
- Each label corresponds to one time block on the timeline
- The `hourHeight` parameter controls the height of each block
- Appointments are positioned relative to these blocks

### Appointment Positioning

When using custom labels, appointment positioning works as follows:

1. **Without Custom Labels**: Appointments are positioned based on actual time (e.g., 8:00 AM, 9:30 AM)
2. **With Custom Labels**: Appointments are still positioned by time, but the labels show your custom text

Example:
```kotlin
// Custom labels
customTimeLabels = listOf("Block 1", "Block 2", "Block 3")

// Appointment at 8:00 AM will appear in the first block
// Appointment at 9:00 AM will appear in the second block
// etc.
```

### Best Practices

1. **Consistent Spacing**: Use consistent time intervals for predictable layout
2. **Clear Labels**: Keep labels short and descriptive
3. **Match Appointments**: Ensure appointment times align with your custom time blocks
4. **Test Different Sizes**: Adjust `hourHeight` to ensure labels are readable

## Dynamic Updates

You can update custom labels dynamically:

```kotlin
fun updateTimeLabels(newLabels: List<String>) {
    val config = TimelineConfig(
        customTimeLabels = newLabels
    )
    binding.timelineView.setConfig(config)
    
    // Optionally refresh appointments
    binding.timelineView.setAppointments(currentAppointments)
}
```

## Examples by Scenario

### School Schedule

```kotlin
val schoolSchedule = listOf(
    "8:00 - Period 1 (Math)",
    "9:00 - Period 2 (English)",
    "10:00 - Recess",
    "10:30 - Period 3 (Science)",
    "11:30 - Period 4 (History)",
    "12:30 - Lunch Break",
    "13:30 - Period 5 (Art)",
    "14:30 - Period 6 (PE)",
    "15:30 - Home Time"
)
```

### Conference Schedule

```kotlin
val conferenceSchedule = listOf(
    "08:00 - Registration",
    "09:00 - Welcome & Keynote",
    "10:30 - Coffee Break",
    "11:00 - Session 1",
    "12:00 - Lunch",
    "13:00 - Session 2",
    "14:00 - Workshops",
    "15:30 - Networking",
    "16:30 - Closing Remarks"
)
```

### Medical Clinic

```kotlin
val clinicHours = listOf(
    "08:00 AM - Morning Shift Start",
    "09:00 AM",
    "10:00 AM",
    "11:00 AM",
    "12:00 PM - Lunch Break",
    "01:00 PM - Afternoon Shift",
    "02:00 PM",
    "03:00 PM",
    "04:00 PM",
    "05:00 PM - Closing"
)
```

### Gym Schedule

```kotlin
val gymClasses = listOf(
    "06:00 - Early Bird Yoga",
    "07:00 - Morning HIIT",
    "08:00 - Spin Class",
    "09:00 - Pilates",
    "10:00 - CrossFit",
    "11:00 - Boxing",
    "12:00 - Lunch Hour Flow",
    "17:00 - Evening Bootcamp",
    "18:00 - Zumba",
    "19:00 - Night Yoga"
)
```

## Limitations

- Custom labels are static strings and don't auto-update based on time
- The number of labels should match your intended time blocks
- Very long labels may be truncated depending on available space
- Labels are displayed in order from top to bottom

## Future Enhancements

Potential future features:
- Auto-generate labels based on time range
- Support for multi-line labels
- Customizable label styling (font, size, color)
- Label templates for common use cases

## Troubleshooting

### Labels Not Showing

Ensure:
- `customTimeLabels` is not empty
- Labels are provided as a `List<String>`
- Config is applied before setting appointments

### Labels Overlapping

Try:
- Increasing `hourHeight` value
- Using shorter label text
- Adjusting font size (if customization is available)

### Appointments Misaligned

Remember:
- Appointments use actual time values
- Custom labels are just visual text
- Ensure appointment times match your intended blocks

## Support

For issues or questions about custom time labels:
- Check the [main README](README.md)
- Review [TECHNICAL.md](TECHNICAL.md) for implementation details
- Open an issue on [GitHub](https://github.com/fadhyyusuf/timelineschedule/issues)

---

Made with ❤️ and AI assistance

