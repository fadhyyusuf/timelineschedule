# Timeline Schedule Library

A powerful and customizable Android library for displaying appointments in a timeline format with automatic overlap handling.

## Features

✅ **Automatic Overlap Handling** - Intelligently displays overlapping appointments side by side
✅ **Fully Customizable** - Colors, sizes, formats, and more
✅ **Material Design** - Built with Material Design components
✅ **Easy to Use** - Simple API with fluent configuration
✅ **Lightweight** - Minimal dependencies
✅ **Compatible** - Works with Android API 21+ (Android 5.0 Lollipop and above)

## Screenshots

![Timeline Schedule Demo](screenshot.png)

## Installation

### Gradle

Add the JitPack repository to your root `build.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.fadhyyusuf:timelineschedule:tag")
}
```

### Local Module

For local development, add the library module to your project:

```kotlin
dependencies {
    implementation(project(":timelineschedule"))
}
```

## Quick Start

### 1. Add TimelineScheduleView to your layout

```xml
<com.fy.timelineschedule.view.TimelineScheduleView
    android:id="@+id/timelineView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### 2. Create appointments and display them

```kotlin
import com.fy.timelineschedule.model.Appointment
import com.fy.timelineschedule.view.TimelineScheduleView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val timelineView = findViewById<TimelineScheduleView>(R.id.timelineView)
        
        // Create sample appointments
        val appointments = listOf(
            Appointment(
                id = "1",
                title = "Dr. Smith",
                subtitle = "Consultation",
                startTime = createTime(9, 0),
                endTime = createTime(10, 0),
                color = Color.parseColor("#2196F3")
            ),
            Appointment(
                id = "2",
                title = "Team Meeting",
                subtitle = "Conference Room A",
                startTime = createTime(10, 30),
                endTime = createTime(11, 30),
                color = Color.parseColor("#4CAF50")
            )
        )
        
        // Set appointments
        timelineView.setAppointments(appointments)
        
        // Set click listener
        timelineView.setOnAppointmentClickListener { appointment ->
            Toast.makeText(this, "Clicked: ${appointment.title}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun createTime(hour: Int, minute: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        return calendar.time
    }
}
```

## Advanced Usage

### Custom Configuration

```kotlin
import com.fy.timelineschedule.model.TimelineConfig
import com.fy.timelineschedule.model.OverlapStrategy

val config = TimelineConfig(
    // Time column settings
    timeColumnWidth = 80,
    timeTextSize = 12f,
    timeTextColor = Color.GRAY,
    hourHeight = 120,
    
    // Grid settings
    showGridLines = true,
    gridLineColor = Color.parseColor("#E0E0E0"),
    gridLineWidth = 1f,
    
    // Card settings
    cardCornerRadius = 8f,
    cardElevation = 2f,
    cardPadding = 8,
    
    // Indicator settings
    indicatorWidth = 4f,
    showIndicatorDot = true,
    
    // Text settings
    titleTextSize = 14f,
    subtitleTextSize = 12f,
    
    // Time format
    use24HourFormat = false,
    
    // Overlap handling
    overlapStrategy = OverlapStrategy.SIDE_BY_SIDE,
    maxOverlapColumns = 3
)

timelineView.setConfig(config)
```

### Appointment Model

```kotlin
Appointment(
    id = "unique-id",                    // Unique identifier
    title = "Appointment Title",         // Main title (required)
    subtitle = "Optional Subtitle",      // Optional subtitle
    startTime = Date(),                  // Start time
    endTime = Date(),                    // End time
    color = Color.BLUE,                  // Indicator color
    avatarUrl = "https://...",          // Avatar URL (optional)
    avatarDrawableRes = R.drawable.avatar, // Or drawable resource
    backgroundColor = Color.WHITE,       // Card background color
    textColor = Color.BLACK             // Text color
)
```

### Handling Overlapping Appointments

The library automatically handles overlapping appointments. You can configure the behavior:

```kotlin
val config = TimelineConfig(
    overlapStrategy = OverlapStrategy.SIDE_BY_SIDE, // Display side by side
    maxOverlapColumns = 3 // Maximum columns for overlapping appointments
)
```

Available strategies:
- `SIDE_BY_SIDE` - Display overlapping appointments side by side (default)
- `STACKED` - Stack with slight offset
- `HIDE_OVERLAP` - Hide overlapping appointments (not recommended)

### Event Listeners

```kotlin
// Click listener
timelineView.setOnAppointmentClickListener { appointment ->
    // Handle click
    showAppointmentDetails(appointment)
}

// Long click listener
timelineView.setOnAppointmentLongClickListener { appointment ->
    // Handle long click
    showAppointmentOptions(appointment)
}
```

## Customization Options

### TimelineConfig Properties

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `timeColumnWidth` | Int | 80 | Width of time column in dp |
| `timeTextSize` | Float | 12f | Size of time text in sp |
| `timeTextColor` | Int | GRAY | Color of time text |
| `hourHeight` | Int | 100 | Height of each hour in dp |
| `showGridLines` | Boolean | true | Show/hide grid lines |
| `gridLineColor` | Int | #E0E0E0 | Color of grid lines |
| `gridLineWidth` | Float | 1f | Width of grid lines |
| `cardCornerRadius` | Float | 8f | Corner radius of appointment cards |
| `cardElevation` | Float | 2f | Elevation of appointment cards |
| `indicatorWidth` | Float | 4f | Width of color indicator |
| `titleTextSize` | Float | 14f | Size of title text |
| `subtitleTextSize` | Float | 12f | Size of subtitle text |
| `use24HourFormat` | Boolean | false | Use 24-hour time format |
| `overlapStrategy` | OverlapStrategy | SIDE_BY_SIDE | How to handle overlaps |

### Appointment Properties

| Property | Type | Required | Description |
|----------|------|----------|-------------|
| `id` | String | ✅ | Unique identifier |
| `title` | String | ✅ | Main title |
| `subtitle` | String? | ❌ | Optional subtitle |
| `startTime` | Date | ✅ | Start time |
| `endTime` | Date | ✅ | End time |
| `color` | Int | ❌ | Indicator color (default: blue) |
| `avatarUrl` | String? | ❌ | Avatar URL |
| `avatarDrawableRes` | Int? | ❌ | Avatar drawable resource |
| `backgroundColor` | Int | ❌ | Card background (default: white) |
| `textColor` | Int | ❌ | Text color (default: black) |

## Requirements

- **Minimum SDK**: 21 (Android 5.0 Lollipop)
- **Target SDK**: 34 (Android 14)
- **Kotlin**: 1.9+
- **Gradle**: 7.0+

## Dependencies

- AndroidX Core KTX
- AndroidX AppCompat
- Material Components

## Sample App

The repository includes a sample app demonstrating all features. Check the `app` module for implementation examples.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

```
Copyright 2024 FY

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Author

**FY** - [GitHub](https://github.com/yourusername)

## Changelog

### Version 1.0.0 (2024-11-06)
- Initial release
- Timeline view with automatic overlap handling
- Customizable appearance
- Material Design components
- Click and long-click listeners
- Support for Android 5.0+

## Support

If you find this library useful, please give it a ⭐️ on GitHub!

For issues and feature requests, please use the [GitHub Issues](https://github.com/yourusername/timelineschedule/issues) page.

