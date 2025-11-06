# Timeline Schedule Library

> **⚠️ AI-Generated Project Disclaimer**  
> This project was created with the assistance of Artificial Intelligence (AI). While the code has been reviewed and tested, users should verify functionality for their specific use cases.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

A modern, flexible, and customizable timeline schedule view library for Android applications. Perfect for appointment booking systems, calendar views, scheduling apps, and any application that needs to display time-based events in a vertical timeline format.

## ✨ Features

- 📅 **Vertical Timeline Layout** - Display appointments in a clean, scrollable vertical timeline
- ⏰ **Flexible Time Range** - Support for 12-hour and 24-hour formats
- 🎨 **Fully Customizable** - Colors, sizes, fonts, and styling options
- 📱 **Responsive Design** - Automatic column layout for overlapping appointments
- 🔍 **Current Time Indicator** - Real-time visual indicator showing current time
- 🎯 **Click Listeners** - Handle appointment clicks with ease
- 🏷️ **Custom Time Labels** - Set your own time labels (NEW!)
- 🎭 **Multiple Appointment States** - Support for different appointment statuses
- 🌈 **Color Coding** - Distinguish appointments by color
- ⚡ **Smooth Scrolling** - Optimized performance for large datasets
- 📐 **Grid Lines** - Optional grid lines for better time visualization
- 🔄 **Real-time Updates** - Dynamically update appointments

## 📸 Screenshots

*Coming soon*

## 🚀 Quick Start

### Installation

Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.fadhyyusuf:timelineschedule:1.0.0")
}
```

Add JitPack repository to your root `settings.gradle.kts`:

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

### Basic Usage

1. **Add to your layout:**

```xml
<com.fy.timelineschedule.TimelineView
    android:id="@+id/timelineView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

2. **Create appointments in your Activity/Fragment:**

```kotlin
val appointments = listOf(
    Appointment(
        id = "1",
        title = "Doctor Appointment",
        subtitle = "Dr. Smith",
        startTime = createTime(8, 0),
        endTime = createTime(9, 0),
        color = Color.parseColor("#2196F3"),
        backgroundColor = Color.WHITE
    ),
    Appointment(
        id = "2",
        title = "Team Meeting",
        subtitle = "Conference Room A",
        startTime = createTime(10, 0),
        endTime = createTime(11, 30),
        color = Color.parseColor("#4CAF50"),
        backgroundColor = Color.WHITE
    )
)

binding.timelineView.setAppointments(appointments)
```

3. **Handle clicks:**

```kotlin
binding.timelineView.setOnAppointmentClickListener { appointment ->
    Toast.makeText(this, "Clicked: ${appointment.title}", Toast.LENGTH_SHORT).show()
}
```

## 🎨 Customization

### Timeline Configuration

```kotlin
val config = TimelineConfig(
    hourHeight = 120,                    // Height of each hour in pixels
    use24HourFormat = false,             // Use 12-hour or 24-hour format
    showGridLines = true,                // Show horizontal grid lines
    gridLineColor = Color.GRAY,          // Grid line color
    gridLineWidth = 1f,                  // Grid line width
    showVerticalDivider = true,          // Show vertical divider between time and content
    verticalDividerColor = Color.GRAY,   // Vertical divider color
    verticalDividerWidth = 2f,           // Vertical divider width
    showCurrentTimeIndicator = true,     // Show current time indicator
    currentTimeIndicatorColor = Color.RED, // Current time indicator color
    currentTimeIndicatorWidth = 2f,      // Current time indicator width
    currentTimeDotRadius = 6f,           // Current time dot radius
    cardCornerRadius = 8f,               // Appointment card corner radius
    cardElevation = 2f,                  // Appointment card elevation
    customTimeLabels = listOf(           // Custom time labels (optional)
        "07:00 AM",
        "08:00 AM",
        "09:00 AM",
        // ... more labels
    )
)

binding.timelineView.setConfig(config)
```

### Custom Time Labels

You can now set custom time labels instead of using the default hourly labels:

```kotlin
val customLabels = listOf(
    "Early Morning",
    "Morning",
    "Late Morning",
    "Noon",
    "Afternoon",
    "Evening"
)

val config = TimelineConfig(
    customTimeLabels = customLabels
)
```

See [CUSTOM_TIME_LABELS.md](CUSTOM_TIME_LABELS.md) for more details.

## 📖 Documentation

- [Quick Start Guide](QUICKSTART.md) - Detailed setup instructions
- [Custom Time Labels](CUSTOM_TIME_LABELS.md) - Learn about custom time labels feature
- [Technical Documentation](TECHNICAL.md) - Architecture and implementation details
- [Publishing Guide](PUBLISHING.md) - How to publish this library
- [Changelog](CHANGELOG.md) - Version history and changes
- [New Features](NEW_FEATURES.md) - Latest features and enhancements

## 🔧 Requirements

### Minimum Requirements
- **Android API 21+** (Android 5.0 Lollipop or higher)
- **Kotlin 1.8.0+** (1.9.0+ recommended)
- **Android Gradle Plugin 8.0.0+** (8.1.0+ recommended)
- **Gradle 8.0+** (8.2+ recommended)
- **Java 11+** (Java 17 recommended)
- **AndroidX** libraries

### Compatibility
✅ **2 Years Backward Compatible** - Supports projects from November 2023 onwards

**Tested With:**
- AGP 8.0.x - 8.5.x
- Kotlin 1.8.x - 2.0.x
- Gradle 8.0 - 8.6
- Android Studio Hedgehog (2023.1.1) or newer

For detailed compatibility information, see [COMPATIBILITY.md](COMPATIBILITY.md)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

```
MIT License

Copyright (c) 2025 Fadhy Yusuf

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 👤 Author

**Fadhy Yusuf**

- GitHub: [@fadhyyusuf](https://github.com/fadhyyusuf)

## 🌟 Show Your Support

Give a ⭐️ if this project helped you!

## 📞 Contact

For questions, issues, or feature requests, please open an issue on GitHub.

---

Made with ❤️ and AI assistance

