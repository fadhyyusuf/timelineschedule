# Changelog

> **⚠️ AI-Generated Project Disclaimer**  
> This project was created with the assistance of Artificial Intelligence (AI). While the code has been reviewed and tested, users should verify functionality for their specific use cases.

All notable changes to the Timeline Schedule library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Planned
- Week view support
- Month view support
- Drag-and-drop appointment editing
- Appointment resize functionality
- Multi-day appointment spanning
- Zoom in/out functionality
- Material 3 theme support
- Custom appointment view templates

## [1.0.0] - 2025-01-10

### Added
- Initial release of Timeline Schedule library
- Vertical timeline view for displaying appointments
- Automatic handling of overlapping appointments with multi-column layout
- Current time indicator with animated dot
- Customizable timeline configuration via `TimelineConfig`
- Support for 12-hour and 24-hour time formats
- Custom time labels feature
- Configurable grid lines (horizontal)
- Configurable vertical divider between time and content
- Appointment click listener support
- Customizable colors for all UI elements
- Customizable card styling (corner radius, elevation)
- Customizable text sizes for time labels and appointments
- Smooth scrolling behavior
- Support for appointment titles and subtitles
- Color-coded appointments with custom background colors
- Responsive column layout for overlapping events
- Comprehensive documentation:
  - README.md with quick start guide
  - QUICKSTART.md with detailed setup instructions
  - TECHNICAL.md with architecture and implementation details
  - CUSTOM_TIME_LABELS.md with custom labels feature guide
  - PUBLISHING.md with library publishing guide

### Features in Detail

#### Timeline View
- Clean, scrollable vertical timeline layout
- Automatic calculation of timeline height based on appointments
- Efficient canvas-based rendering for performance
- Support for arbitrary time ranges

#### Appointment Management
- Simple data model with `Appointment` class
- Support for:
  - Unique IDs
  - Title and optional subtitle
  - Start and end times (Java `Date` objects)
  - Custom colors (border and background)
  - Click handling

#### Overlapping Appointments
- Automatic detection of overlapping appointments
- Smart column calculation algorithm
- Dynamic width adjustment based on number of overlapping events
- Visual separation with configurable padding

#### Current Time Indicator
- Real-time position indicator
- Customizable line color and width
- Animated dot at the timeline edge
- Auto-updates every minute
- Can be disabled via configuration

#### Customization Options
- **Layout**: Hour height, time column width, appointment padding
- **Time Format**: 12h/24h format, custom time labels
- **Grid Lines**: Show/hide, color, width
- **Vertical Divider**: Show/hide, color, width
- **Current Time Indicator**: Show/hide, color, width, dot radius
- **Card Styling**: Corner radius, elevation
- **Text Styling**: Font sizes for labels and appointments

#### Custom Time Labels
- Replace default hourly labels with custom text
- Support for any list of strings
- Useful for:
  - Non-standard time intervals
  - Named time blocks (e.g., "Morning", "Afternoon")
  - Event-based labels (e.g., "Registration", "Keynote")
  - Custom schedule labels (e.g., "Period 1", "Period 2")

### Dependencies
- androidx.core:core-ktx:1.12.0
- androidx.appcompat:appcompat:1.6.1
- com.google.android.material:material:1.11.0

### Requirements
- Minimum SDK: 21 (Android 5.0 Lollipop)
- Target SDK: 34 (Android 14)
- Kotlin: 1.9+
- Java: 8+

### Documentation
- Complete README with installation and usage instructions
- Quick start guide for new users
- Technical documentation with architecture details
- Custom time labels feature documentation
- Publishing guide for maintainers
- MIT License

### Example App
- Sample Android application demonstrating all features
- Example appointments with various configurations
- Sample code for common use cases:
  - Appointment booking system
  - Daily schedule view
  - Custom time periods
  - Overlapping events handling

### Known Limitations
- Only supports single-day view (24-hour period)
- No built-in animations for appointment changes
- Very large appointment lists (1000+) may impact performance
- Uses device local timezone only

---

## Version History

| Version | Release Date | Status | Notes |
|---------|-------------|--------|-------|
| 1.0.0   | 2025-01-10  | Stable | Initial release |

## Contributing

To add entries to this changelog:

1. Add entries under `[Unreleased]` section
2. Use the following categories:
   - `Added` for new features
   - `Changed` for changes in existing functionality
   - `Deprecated` for soon-to-be removed features
   - `Removed` for now removed features
   - `Fixed` for bug fixes
   - `Security` for vulnerability fixes
3. When releasing, move items from `[Unreleased]` to a new version section
4. Follow the format: `- Brief description [#issue-number]`

## Links

- [GitHub Repository](https://github.com/fadhyyusuf/timelineschedule)
- [Issue Tracker](https://github.com/fadhyyusuf/timelineschedule/issues)
- [JitPack](https://jitpack.io/#fadhyyusuf/timelineschedule)

---

Made with ❤️ and AI assistance

