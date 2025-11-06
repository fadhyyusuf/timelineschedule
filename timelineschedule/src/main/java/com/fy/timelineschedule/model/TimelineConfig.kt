package com.fy.timelineschedule.model

import android.graphics.Color

/**
 * Configuration class for customizing the timeline schedule view
 */
data class TimelineConfig(
    // Time column settings
    val timeColumnWidth: Int = 80,
    val timeTextSize: Float = 12f,
    val timeTextColor: Int = Color.GRAY,
    val hourHeight: Int = 100,

    // Custom time labels (if null, will auto-generate from appointments)
    val customTimeLabels: List<String>? = null,

    // Grid settings
    val showGridLines: Boolean = true,
    val gridLineColor: Int = Color.parseColor("#E0E0E0"),
    val gridLineWidth: Float = 2f,
    val showVerticalDivider: Boolean = true,
    val verticalDividerColor: Int = Color.parseColor("#E0E0E0"),
    val verticalDividerWidth: Float = 2f,

    // Current time indicator
    val showCurrentTimeIndicator: Boolean = true,
    val currentTimeIndicatorColor: Int = Color.parseColor("#FF5252"),
    val currentTimeIndicatorWidth: Float = 2f,
    val currentTimeDotRadius: Float = 6f,

    // Appointment card settings
    val cardCornerRadius: Float = 8f,
    val cardElevation: Float = 2f,
    val cardPadding: Int = 8,
    val cardMargin: Int = 2,
    val cardMinHeight: Int = 50,

    // Indicator settings
    val indicatorWidth: Float = 4f,
    val showIndicatorDot: Boolean = true,
    val indicatorDotRadius: Float = 4f,

    // Text settings
    val titleTextSize: Float = 14f,
    val subtitleTextSize: Float = 12f,

    // Avatar settings
    val showAvatar: Boolean = true,
    val avatarSize: Int = 40,

    // Time format
    val use24HourFormat: Boolean = false,
    val showTimeZone: Boolean = false,

    // Overlap handling
    val overlapStrategy: OverlapStrategy = OverlapStrategy.SIDE_BY_SIDE,
    val maxOverlapColumns: Int = 3
)

/**
 * Strategy for handling overlapping appointments
 */
enum class OverlapStrategy {
    /**
     * Display overlapping appointments side by side with reduced width
     */
    SIDE_BY_SIDE,

    /**
     * Stack overlapping appointments with slight offset
     */
    STACKED,

    /**
     * Show only the first appointment, hide others (not recommended)
     */
    HIDE_OVERLAP
}

