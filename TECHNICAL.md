# Technical Documentation

> **⚠️ AI-Generated Project Disclaimer**  
> This project was created with the assistance of Artificial Intelligence (AI). While the code has been reviewed and tested, users should verify functionality for their specific use cases.

## Architecture Overview

The Timeline Schedule library follows a modular architecture with clear separation of concerns:

```
timelineschedule/
├── model/
│   ├── Appointment.kt       # Data model for appointments
│   └── TimelineConfig.kt    # Configuration data class
├── TimelineView.kt          # Main custom view component
└── utils/
    └── TimeUtils.kt         # Time-related utility functions
```

## Core Components

### 1. TimelineView

The main custom view that extends `ScrollView` and handles the rendering of the timeline.

**Key Responsibilities:**
- Rendering the timeline grid
- Drawing time labels
- Positioning appointments
- Handling overlapping appointments
- Drawing the current time indicator
- Managing user interactions

**Main Methods:**

```kotlin
class TimelineView : ScrollView {
    
    // Configuration
    fun setConfig(config: TimelineConfig)
    
    // Data
    fun setAppointments(appointments: List<Appointment>)
    
    // Interaction
    fun setOnAppointmentClickListener(listener: (Appointment) -> Unit)
    
    // Internal rendering
    private fun drawTimeline(canvas: Canvas)
    private fun drawAppointments(canvas: Canvas)
    private fun drawCurrentTimeIndicator(canvas: Canvas)
    private fun calculateAppointmentColumns(appointments: List<Appointment>)
}
```

### 2. Appointment Model

Data class representing a single appointment or event.

```kotlin
data class Appointment(
    val id: String,                    // Unique identifier
    val title: String,                 // Main title text
    val subtitle: String? = null,      // Optional subtitle
    val startTime: Date,               // Start time
    val endTime: Date,                 // End time
    val color: Int,                    // Border/text color
    val backgroundColor: Int = Color.WHITE,  // Background color
    var column: Int = 0,               // Column for overlapping (internal)
    var totalColumns: Int = 1          // Total columns needed (internal)
)
```

**Field Details:**

- **id**: Unique identifier for the appointment (used for click handling)
- **title**: Primary text displayed on the appointment card
- **subtitle**: Secondary text (optional), displayed below title
- **startTime**: Java `Date` object representing start time
- **endTime**: Java `Date` object representing end time
- **color**: Android color integer for border and text
- **backgroundColor**: Android color integer for card background
- **column**: Internal field, calculated automatically for overlapping
- **totalColumns**: Internal field, total columns needed for overlapping

### 3. TimelineConfig

Configuration class for customizing the timeline appearance and behavior.

```kotlin
data class TimelineConfig(
    // Layout
    val hourHeight: Int = 100,                    // Height per hour in pixels
    val timeColumnWidth: Int = 80,                // Width of time label column
    val appointmentPadding: Int = 4,              // Padding between appointments
    
    // Time Format
    val use24HourFormat: Boolean = false,         // 12h vs 24h format
    val customTimeLabels: List<String>? = null,   // Custom time labels
    
    // Grid Lines
    val showGridLines: Boolean = true,            // Show horizontal lines
    val gridLineColor: Int = Color.GRAY,          // Grid line color
    val gridLineWidth: Float = 1f,                // Grid line width
    
    // Vertical Divider
    val showVerticalDivider: Boolean = true,      // Show vertical divider
    val verticalDividerColor: Int = Color.GRAY,   // Divider color
    val verticalDividerWidth: Float = 2f,         // Divider width
    
    // Current Time Indicator
    val showCurrentTimeIndicator: Boolean = true, // Show current time line
    val currentTimeIndicatorColor: Int = Color.RED, // Indicator color
    val currentTimeIndicatorWidth: Float = 2f,    // Indicator line width
    val currentTimeDotRadius: Float = 6f,         // Indicator dot radius
    
    // Card Styling
    val cardCornerRadius: Float = 8f,             // Card corner radius
    val cardElevation: Float = 2f,                // Card shadow elevation
    
    // Text Styling
    val timeLabelTextSize: Float = 14f,           // Time label font size
    val appointmentTitleTextSize: Float = 14f,    // Appointment title size
    val appointmentSubtitleTextSize: Float = 12f  // Appointment subtitle size
)
```

## Key Algorithms

### 1. Overlapping Appointment Detection

The library automatically detects and handles overlapping appointments:

```kotlin
private fun calculateAppointmentColumns(appointments: List<Appointment>) {
    // Sort appointments by start time
    val sortedAppointments = appointments.sortedBy { it.startTime }
    
    // Track active columns
    val columns = mutableListOf<MutableList<Appointment>>()
    
    for (appointment in sortedAppointments) {
        // Find available column
        var placed = false
        for (column in columns) {
            if (!hasOverlap(column.last(), appointment)) {
                column.add(appointment)
                appointment.column = columns.indexOf(column)
                placed = true
                break
            }
        }
        
        // Create new column if needed
        if (!placed) {
            val newColumn = mutableListOf(appointment)
            columns.add(newColumn)
            appointment.column = columns.size - 1
        }
    }
    
    // Set total columns for all appointments
    val totalColumns = columns.size
    sortedAppointments.forEach { it.totalColumns = totalColumns }
}

private fun hasOverlap(a: Appointment, b: Appointment): Boolean {
    return a.endTime.after(b.startTime) && a.startTime.before(b.endTime)
}
```

### 2. Time to Pixel Conversion

Converting time values to pixel positions on the canvas:

```kotlin
private fun timeToPixel(time: Date): Float {
    val calendar = Calendar.getInstance().apply { this.time = time }
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    
    val startHour = 0 // Or configurable start hour
    val hourOffset = hour - startHour
    val minuteOffset = minute / 60f
    
    return (hourOffset + minuteOffset) * config.hourHeight
}
```

### 3. Current Time Indicator

Real-time positioning of the current time indicator:

```kotlin
private fun drawCurrentTimeIndicator(canvas: Canvas) {
    if (!config.showCurrentTimeIndicator) return
    
    val now = Date()
    val yPosition = timeToPixel(now)
    
    val paint = Paint().apply {
        color = config.currentTimeIndicatorColor
        strokeWidth = config.currentTimeIndicatorWidth
        style = Paint.Style.STROKE
    }
    
    // Draw horizontal line
    canvas.drawLine(
        config.timeColumnWidth.toFloat(),
        yPosition,
        width.toFloat(),
        yPosition,
        paint
    )
    
    // Draw dot at the start
    paint.style = Paint.Style.FILL
    canvas.drawCircle(
        config.timeColumnWidth.toFloat(),
        yPosition,
        config.currentTimeDotRadius,
        paint
    )
}
```

## Performance Considerations

### 1. View Recycling

The library uses a single custom view with efficient canvas drawing rather than creating individual views for each appointment. This provides:
- Reduced memory footprint
- Faster rendering
- Better scroll performance

### 2. Drawing Optimization

```kotlin
override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    
    // Only draw visible region
    val visibleTop = scrollY
    val visibleBottom = scrollY + height
    
    // Draw only visible appointments
    appointments.filter { appointment ->
        val top = timeToPixel(appointment.startTime)
        val bottom = timeToPixel(appointment.endTime)
        bottom >= visibleTop && top <= visibleBottom
    }.forEach { appointment ->
        drawAppointment(canvas, appointment)
    }
}
```

### 3. Invalidation Strategy

The view only invalidates when necessary:
- Configuration changes
- Appointment updates
- Current time indicator updates (periodic)

```kotlin
private fun startCurrentTimeUpdater() {
    handler.postDelayed(object : Runnable {
        override fun run() {
            if (config.showCurrentTimeIndicator) {
                invalidate() // Redraw to update current time indicator
                handler.postDelayed(this, 60000) // Update every minute
            }
        }
    }, 60000)
}
```

## Customization Points

### 1. Custom Rendering

Extend `TimelineView` to customize rendering:

```kotlin
class CustomTimelineView : TimelineView {
    
    override fun drawAppointment(canvas: Canvas, appointment: Appointment) {
        // Custom appointment rendering
    }
    
    override fun drawTimeLabel(canvas: Canvas, time: String, y: Float) {
        // Custom time label rendering
    }
}
```

### 2. Custom Appointment Layout

Implement custom positioning logic:

```kotlin
protected open fun calculateAppointmentRect(appointment: Appointment): RectF {
    val top = timeToPixel(appointment.startTime)
    val bottom = timeToPixel(appointment.endTime)
    val columnWidth = (width - config.timeColumnWidth) / appointment.totalColumns.toFloat()
    val left = config.timeColumnWidth + (columnWidth * appointment.column)
    val right = left + columnWidth - config.appointmentPadding
    
    return RectF(left, top, right, bottom)
}
```

### 3. Custom Time Labels

The library supports custom time labels:

```kotlin
val config = TimelineConfig(
    customTimeLabels = listOf(
        "Morning Block",
        "Midday Block",
        "Afternoon Block",
        "Evening Block"
    )
)
```

## Threading Model

- **Main Thread**: All UI rendering and user interaction
- **No Background Threads**: The library does not create background threads
- **Caller Responsibility**: Ensure appointments are loaded on appropriate thread

Example with coroutines:

```kotlin
lifecycleScope.launch {
    val appointments = withContext(Dispatchers.IO) {
        // Load appointments from database
        repository.getAppointments()
    }
    
    // Update UI on main thread
    binding.timelineView.setAppointments(appointments)
}
```

## Testing

### Unit Testing

Test appointment overlap detection:

```kotlin
@Test
fun testOverlapDetection() {
    val appointment1 = Appointment(
        id = "1",
        title = "Test 1",
        startTime = createTime(9, 0),
        endTime = createTime(10, 0),
        color = Color.BLUE
    )
    
    val appointment2 = Appointment(
        id = "2",
        title = "Test 2",
        startTime = createTime(9, 30),
        endTime = createTime(10, 30),
        color = Color.GREEN
    )
    
    assertTrue(hasOverlap(appointment1, appointment2))
}
```

### UI Testing

Test view interaction with Espresso:

```kotlin
@Test
fun testAppointmentClick() {
    val appointment = Appointment(
        id = "1",
        title = "Meeting",
        startTime = createTime(9, 0),
        endTime = createTime(10, 0),
        color = Color.BLUE
    )
    
    activityRule.activity.runOnUiThread {
        timelineView.setAppointments(listOf(appointment))
    }
    
    onView(withId(R.id.timelineView))
        .perform(click())
    
    // Verify click listener called
}
```

## Dependencies

### Required

```kotlin
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
}
```

### Optional (for advanced features)

```kotlin
dependencies {
    // For date/time handling
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    
    // For coroutines support
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
```

## API Reference

### TimelineView Methods

| Method | Description | Parameters | Return Type |
|--------|-------------|------------|-------------|
| `setConfig()` | Set timeline configuration | `config: TimelineConfig` | `Unit` |
| `setAppointments()` | Set list of appointments | `appointments: List<Appointment>` | `Unit` |
| `setOnAppointmentClickListener()` | Set click listener | `listener: (Appointment) -> Unit` | `Unit` |
| `scrollToTime()` | Scroll to specific time | `time: Date` | `Unit` |
| `scrollToCurrentTime()` | Scroll to current time | - | `Unit` |

### Extension Functions

```kotlin
// Scroll to current time
fun TimelineView.scrollToNow() {
    scrollToTime(Date())
}

// Add appointment dynamically
fun TimelineView.addAppointment(appointment: Appointment) {
    val current = getCurrentAppointments().toMutableList()
    current.add(appointment)
    setAppointments(current)
}
```

## Compatibility

- **Min SDK**: 21 (Android 5.0 Lollipop)
- **Target SDK**: 34 (Android 14)
- **Kotlin**: 1.9+
- **Java**: Java 8+ (for Java projects)

## Known Limitations

1. **Memory**: Very large appointment lists (1000+) may impact performance
2. **Time Range**: Currently supports 24-hour day view only
3. **Timezone**: Uses device local timezone
4. **Animations**: No built-in animations for appointment changes

## Future Enhancements

- Week view support
- Month view support
- Drag-and-drop appointment editing
- Appointment resize
- Multi-day appointment spanning
- Custom appointment views
- Zoom functionality
- Theme support (Material 3)

## Contributing

When contributing, please:
1. Follow Kotlin coding conventions
2. Add unit tests for new features
3. Update documentation
4. Ensure backward compatibility

## License

MIT License - See [LICENSE](LICENSE) file for details.

---

Made with ❤️ and AI assistance

