# 🚀 Quick Start Guide - Timeline Schedule Library

## Installation (5 minutes)

### Step 1: Add to your project

Add the library module to your project's `settings.gradle.kts`:

```kotlin
include(":timelineschedule")
```

Add dependency in your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":timelineschedule"))
}
```

### Step 2: Add to layout

In your `activity_main.xml`:

```xml
<com.fy.timelineschedule.view.TimelineScheduleView
    android:id="@+id/timelineView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### Step 3: Use in code

In your `MainActivity.kt`:

```kotlin
import com.fy.timelineschedule.model.Appointment
import com.fy.timelineschedule.view.TimelineScheduleView
import android.graphics.Color
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val timelineView = findViewById<TimelineScheduleView>(R.id.timelineView)
        
        // Create appointments
        val appointments = listOf(
            Appointment(
                id = "1",
                title = "Morning Meeting",
                subtitle = "Conference Room A",
                startTime = createTime(9, 0),
                endTime = createTime(10, 0),
                color = Color.parseColor("#2196F3")
            ),
            Appointment(
                id = "2",
                title = "Lunch Break",
                startTime = createTime(12, 0),
                endTime = createTime(13, 0),
                color = Color.parseColor("#4CAF50")
            )
        )
        
        // Display appointments
        timelineView.setAppointments(appointments)
        
        // Handle clicks (optional)
        timelineView.setOnAppointmentClickListener { appointment ->
            Toast.makeText(this, "Clicked: ${appointment.title}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun createTime(hour: Int, minute: Int): java.util.Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, 0)
        return cal.time
    }
}
```

## That's it! 🎉

Run your app and you'll see a beautiful timeline with your appointments.

---

## Customization (Optional)

Want to customize? Add this before `setAppointments()`:

```kotlin
import com.fy.timelineschedule.model.TimelineConfig

val config = TimelineConfig(
    hourHeight = 120,              // Taller hours
    use24HourFormat = false,       // Use 12-hour format
    showGridLines = true,          // Show grid lines
    cardCornerRadius = 8f,         // Rounded corners
    cardElevation = 2f             // Card shadow
)

timelineView.setConfig(config)
```

---

## Handling Overlaps

The library automatically handles overlapping appointments! Just add them:

```kotlin
val appointments = listOf(
    // These two appointments overlap - library handles it automatically!
    Appointment(
        id = "1",
        title = "Meeting A",
        startTime = createTime(9, 0),
        endTime = createTime(10, 0),
        color = Color.BLUE
    ),
    Appointment(
        id = "2",
        title = "Meeting B",
        startTime = createTime(9, 30),  // Overlaps with Meeting A
        endTime = createTime(10, 30),
        color = Color.RED
    )
)
```

The library will automatically display them side-by-side! 📅

---

## More Examples

Check `MainActivity.kt` in the sample app for more examples including:
- Multiple overlapping appointments
- Custom colors per appointment
- Subtitle text
- Different time ranges
- Click listeners

---

## Need Help?

- 📖 Read the full [README.md](README.md)
- 🔧 Check [TECHNICAL.md](TECHNICAL.md) for details
- 💬 Open an issue on GitHub

---

## Common Customizations

### Change hour size:
```kotlin
TimelineConfig(hourHeight = 150)  // Bigger hours
```

### Use 24-hour format:
```kotlin
TimelineConfig(use24HourFormat = true)
```

### Hide grid lines:
```kotlin
TimelineConfig(showGridLines = false)
```

### Customize colors:
```kotlin
Appointment(
    // ...
    color = Color.parseColor("#FF5722"),           // Indicator color
    backgroundColor = Color.parseColor("#FFEBEE"),  // Card background
    textColor = Color.BLACK                         // Text color
)
```

---

**Happy coding! 🎊**

