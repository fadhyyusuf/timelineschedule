# Quick Start Guide

> **⚠️ AI-Generated Project Disclaimer**  
> This project was created with the assistance of Artificial Intelligence (AI). While the code has been reviewed and tested, users should verify functionality for their specific use cases.

## Installation

### Step 1: Add JitPack Repository

Add JitPack to your root `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Or in your root `build.gradle.kts` (legacy):

```kotlin
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add Dependency

Add the library dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.fadhyyusuf:timelineschedule:1.0.0")
}
```

### Step 3: Sync Project

Sync your project with Gradle files.

## Basic Implementation

### 1. Add TimelineView to Layout

Create or open your layout XML file (e.g., `activity_main.xml`):

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.fy.timelineschedule.TimelineView
        android:id="@+id/timelineView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### 2. Set Up in Activity/Fragment

#### Using View Binding (Recommended)

**Enable View Binding** in your app's `build.gradle.kts`:

```kotlin
android {
    buildFeatures {
        viewBinding = true
    }
}
```

**In your Activity:**

```kotlin
package com.example.myapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityMainBinding
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
        // Create sample appointments
        val appointments = createSampleAppointments()

        // Configure timeline (optional)
        val config = TimelineConfig(
            hourHeight = 120,
            use24HourFormat = false,
            showGridLines = true,
            showCurrentTimeIndicator = true,
            currentTimeIndicatorColor = Color.RED
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
            Appointment(
                id = "1",
                title = "Morning Meeting",
                subtitle = "Team Sync",
                startTime = createTime(today, 9, 0),
                endTime = createTime(today, 10, 0),
                color = Color.parseColor("#2196F3"),
                backgroundColor = Color.WHITE
            ),
            Appointment(
                id = "2",
                title = "Doctor Appointment",
                subtitle = "Dr. Smith",
                startTime = createTime(today, 11, 0),
                endTime = createTime(today, 12, 0),
                color = Color.parseColor("#4CAF50"),
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

#### Using findViewById (Legacy)

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timelineView = findViewById<TimelineView>(R.id.timelineView)
        
        val appointments = createSampleAppointments()
        timelineView.setAppointments(appointments)
        
        timelineView.setOnAppointmentClickListener { appointment ->
            Toast.makeText(this, "Clicked: ${appointment.title}", Toast.LENGTH_SHORT).show()
        }
    }
    
    // ... rest of the code
}
```

### 3. Run Your App

Build and run your app. You should see a timeline view with your appointments displayed!

## Next Steps

- **Customize Configuration**: Explore [TimelineConfig](TECHNICAL.md#timelineconfig) options
- **Custom Time Labels**: Learn about [Custom Time Labels](CUSTOM_TIME_LABELS.md)
- **Styling**: Customize colors, sizes, and appearance
- **Dynamic Updates**: Add, remove, or update appointments dynamically
- **Event Handling**: Implement custom click handlers

## Common Use Cases

### Appointment Booking System

```kotlin
val appointments = listOf(
    Appointment(
        id = "1",
        title = "John Doe",
        subtitle = "Consultation",
        startTime = createTime(today, 9, 0),
        endTime = createTime(today, 9, 30),
        color = Color.parseColor("#2196F3"),
        backgroundColor = Color.WHITE
    )
)
```

### Daily Schedule View

```kotlin
val config = TimelineConfig(
    hourHeight = 100,
    use24HourFormat = true,
    showGridLines = true,
    showCurrentTimeIndicator = true
)
```

### Custom Time Periods

```kotlin
val config = TimelineConfig(
    customTimeLabels = listOf(
        "Early Morning",
        "Morning",
        "Midday",
        "Afternoon",
        "Evening",
        "Night"
    )
)
```

## Troubleshooting

### JitPack Build Failed

If you encounter build errors, ensure:
- You have internet connection
- The version tag exists on GitHub
- JitPack repository is correctly added

### Appointments Not Showing

Check that:
- Start time is before end time
- Times are within the visible range
- Appointments list is not empty
- `setAppointments()` is called after `setConfig()`

### Current Time Indicator Not Visible

Ensure:
- `showCurrentTimeIndicator = true` in config
- Current time is within the timeline range
- Indicator color contrasts with background

## Support

For more help:
- Check [Technical Documentation](TECHNICAL.md)
- Open an issue on [GitHub](https://github.com/fadhyyusuf/timelineschedule/issues)
- Review sample code in the demo app

---

Made with ❤️ and AI assistance

