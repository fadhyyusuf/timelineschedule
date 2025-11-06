# Timeline Schedule Library - Technical Documentation

## Architecture Overview

### Component Structure

```
TimelineScheduleView
├── ScrollView (scrollable container)
│   └── FrameLayout (timeline container)
│       ├── LinearLayout (time column - left side)
│       │   └── TextView[] (hour labels)
│       └── FrameLayout (appointment container - right side)
│           └── MaterialCardView[] (appointment cards)
```

## How Overlapping Works

### 1. Overlap Detection

The `OverlapManager` class handles the detection and positioning of overlapping appointments:

```kotlin
// Two appointments overlap if:
startTime1 < endTime2 && endTime1 > startTime2
```

### 2. Column Assignment Algorithm

1. **Group Formation**: First, all overlapping appointments are grouped together
2. **Column Assignment**: Within each group, appointments are assigned to columns
3. **Width Calculation**: Each appointment's width = containerWidth / totalColumns

Example:
```
Time    Column 0        Column 1
08:00   [Abril Lewis]   [Robert Fox....]
08:30   [............]  [..............]
09:00                   [End Robert Fox]
09:20   [Cody Fisher]   [Annette Black]
```

### 3. Position Calculation

#### Vertical Position (Y-axis)
```kotlin
minuteOfDay = hour * 60 + minute
startY = (minuteOfDay/60 - startHour) * hourHeight
```

#### Horizontal Position (X-axis)
```kotlin
columnWidth = containerWidth / totalColumns
x = column * columnWidth
width = columnWidth - margins
```

## Key Classes

### TimelineScheduleView
Main custom view that handles:
- Drawing the timeline grid
- Positioning appointment cards
- Handling scroll behavior
- Managing user interactions

### Appointment
Data model for appointments:
```kotlin
data class Appointment(
    val id: String,
    val title: String,
    val subtitle: String?,
    val startTime: Date,
    val endTime: Date,
    val color: Int,
    // ... other properties
)
```

### TimelineConfig
Configuration class for customization:
```kotlin
data class TimelineConfig(
    val hourHeight: Int = 100,        // Height per hour in dp
    val timeColumnWidth: Int = 80,    // Width of time labels
    val use24HourFormat: Boolean = false,
    // ... other properties
)
```

### OverlapManager
Internal class that calculates positioning:
```kotlin
data class PositionedAppointment(
    val appointment: Appointment,
    val column: Int,              // Which column (0, 1, 2...)
    val totalColumns: Int,        // Total columns in this group
    val startMinute: Int,         // Start minute of day (0-1439)
    val endMinute: Int            // End minute of day (0-1439)
)
```

## Customization Examples

### Example 1: Dense Schedule
```kotlin
val config = TimelineConfig(
    hourHeight = 80,              // Shorter hours
    cardMinHeight = 40,           // Smaller minimum card height
    titleTextSize = 12f,          // Smaller text
    showGridLines = true
)
```

### Example 2: Large Display
```kotlin
val config = TimelineConfig(
    hourHeight = 150,             // Taller hours
    timeColumnWidth = 100,        // Wider time column
    cardCornerRadius = 12f,       // More rounded corners
    cardElevation = 4f,           // More elevation
    titleTextSize = 16f,          // Larger text
    subtitleTextSize = 14f
)
```

### Example 3: 24-Hour Format
```kotlin
val config = TimelineConfig(
    use24HourFormat = true,       // Use 24-hour format
    showTimeZone = true,          // Show timezone
    timeTextSize = 14f
)
```

## Performance Considerations

### Optimization Techniques

1. **View Recycling**: Cards are created on-demand, not recycled (suitable for reasonable appointment counts)
2. **Lazy Drawing**: Grid lines drawn only when needed
3. **Efficient Layout**: FrameLayout for absolute positioning

### Recommended Limits

- **Appointments**: Up to 100 appointments per day performs well
- **Overlap Depth**: Up to 4 overlapping appointments recommended
- **Time Range**: 6-18 hours (12 hour span) for best UX

## Common Issues & Solutions

### Issue 1: Cards Not Visible
**Solution**: Ensure appointments have valid start/end times and check time range

### Issue 2: Cards Overlapping Incorrectly
**Solution**: Verify that appointment times don't have timezone issues

### Issue 3: Text Too Small
**Solution**: Increase `titleTextSize` and `subtitleTextSize` in config

### Issue 4: Cards Too Narrow with Many Overlaps
**Solution**: Adjust `maxOverlapColumns` in config or use STACKED strategy

## Testing

### Unit Tests
Test the overlap detection algorithm:
```kotlin
@Test
fun testOverlapDetection() {
    val appointment1 = Appointment(...)
    val appointment2 = Appointment(...)
    assertTrue(appointment1.overlapsWith(appointment2))
}
```

### UI Tests
Test the timeline rendering:
```kotlin
@Test
fun testTimelineDisplay() {
    val timelineView = TimelineScheduleView(context)
    timelineView.setAppointments(testAppointments)
    // Verify rendering
}
```

## API Reference

### TimelineScheduleView Methods

| Method | Description |
|--------|-------------|
| `setConfig(config: TimelineConfig)` | Set custom configuration |
| `setAppointments(appointments: List<Appointment>)` | Set appointments to display |
| `setOnAppointmentClickListener(listener)` | Set click listener |
| `setOnAppointmentLongClickListener(listener)` | Set long click listener |

### Appointment Methods

| Method | Description |
|--------|-------------|
| `overlapsWith(other: Appointment): Boolean` | Check if overlaps with another |
| `getDurationMinutes(): Long` | Get duration in minutes |

### TimeUtils Methods

| Method | Description |
|--------|-------------|
| `formatTime(date: Date, use24Hour: Boolean)` | Format time for display |
| `getMinuteOfDay(date: Date): Int` | Get minute of day (0-1439) |
| `createTime(hour: Int, minute: Int): Date` | Create time from hour/minute |
| `isToday(date: Date): Boolean` | Check if date is today |

## Migration Guide

### From Version 1.0.0 to Future Versions

When new versions are released, migration guides will be provided here.

## Contributing

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable names
- Add KDoc comments for public APIs
- Write unit tests for new features

### Pull Request Process
1. Create feature branch
2. Implement feature with tests
3. Update documentation
4. Submit PR with description

## License

Apache 2.0 - See LICENSE file

