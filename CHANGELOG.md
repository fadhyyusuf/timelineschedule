# Changelog

All notable changes to the Timeline Schedule Library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Planned Features
- Image loading support (Glide, Coil, Picasso)
- Drag and drop to reschedule appointments
- Pinch to zoom
- Custom header with date picker
- Export to calendar
- Dark mode support
- Animations for appointment changes

## [1.0.0] - 2024-11-06

### Added
- Initial release of Timeline Schedule Library
- Core timeline view component (`TimelineScheduleView`)
- Automatic overlap detection and handling
- Side-by-side display for overlapping appointments
- Customizable configuration via `TimelineConfig`
- Material Design card-based appointments
- Click and long-click listeners
- Support for custom colors per appointment
- Color indicator bar for each appointment
- Optional avatar support
- Optional subtitle for appointments
- Grid lines for time visualization
- Configurable time format (12/24 hour)
- Configurable hour height
- Minimum SDK support: Android 5.0 (API 21)
- Target SDK: Android 14 (API 34)

### Features Detail

#### TimelineScheduleView
- Scrollable timeline view
- Automatic time range calculation based on appointments
- Customizable time column width
- Customizable grid appearance
- Efficient rendering with FrameLayout positioning

#### Appointment Model
- Unique ID for each appointment
- Title and optional subtitle
- Start and end time
- Custom color indicator
- Custom background color
- Custom text color
- Avatar support (URL or drawable resource)

#### TimelineConfig
- Configurable hour height (affects zoom level)
- Configurable time column width
- Configurable text sizes
- Configurable colors (time text, grid lines)
- Configurable card appearance (corner radius, elevation)
- Configurable overlap strategy
- Configurable maximum overlap columns

#### OverlapManager
- Intelligent overlap detection algorithm
- Automatic column assignment for overlapping appointments
- Optimized positioning calculation
- Support for multiple levels of overlap

#### Utilities
- TimeUtils for time formatting and manipulation
- Extension functions for unit conversion (dp/sp to pixels)

### Dependencies
- AndroidX Core KTX
- AndroidX AppCompat
- Material Components for Android

### Documentation
- Comprehensive README with quick start guide
- Technical documentation (TECHNICAL.md)
- Publishing guide (PUBLISHING.md)
- Sample app demonstrating all features
- Inline code documentation (KDoc)

### Testing
- Unit test structure
- Instrumented test structure
- Sample data for testing

## Version History

### Version Numbering Scheme

- **Major (X.0.0)**: Breaking API changes, major new features
- **Minor (1.X.0)**: New features, backward compatible
- **Patch (1.0.X)**: Bug fixes, minor improvements

## Upgrade Guide

### Upgrading from Pre-release to 1.0.0

This is the initial stable release. If you were using pre-release versions, please:

1. Update your dependency to `1.0.0`
2. Review the README for current API usage
3. Check for any API changes in your code
4. Test thoroughly before deploying

## Known Issues

### Version 1.0.0
- None reported yet

## Future Roadmap

### Version 1.1.0 (Planned)
- [ ] Image loading library integration
- [ ] Improved avatar handling
- [ ] Custom appointment view support
- [ ] Performance optimizations for large datasets
- [ ] Accessibility improvements

### Version 1.2.0 (Planned)
- [ ] Drag and drop support
- [ ] Appointment resizing
- [ ] Multi-day view support
- [ ] Week view mode
- [ ] Month view mode

### Version 2.0.0 (Planned)
- [ ] Complete redesign with Jetpack Compose
- [ ] Compose Multiplatform support
- [ ] Advanced animations
- [ ] Better theming support

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for information on contributing to this project.

## Support

If you encounter any issues or have questions:
1. Check the [documentation](README.md)
2. Search [existing issues](https://github.com/fadhyyusuf/timelineschedule/issues)
3. Create a [new issue](https://github.com/fadhyyusuf/timelineschedule/issues/new)

## License

Copyright 2024 Fadhy Yusuf

Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for details.

