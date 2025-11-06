package com.fy.timelineschedule.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.fy.timelineschedule.R
import com.fy.timelineschedule.model.Appointment
import com.fy.timelineschedule.model.TimelineConfig
import com.fy.timelineschedule.utils.OverlapManager
import com.fy.timelineschedule.utils.TimeUtils
import com.google.android.material.card.MaterialCardView
import java.util.Calendar

/**
 * TimelineScheduleView - A custom view for displaying appointments in a timeline format
 *
 * Features:
 * - Displays appointments in a timeline with time labels
 * - Handles overlapping appointments automatically
 * - Fully customizable appearance
 * - Click listeners for appointments
 *
 * Usage:
 * ```
 * val timelineView = findViewById<TimelineScheduleView>(R.id.timelineView)
 * timelineView.setAppointments(appointmentList)
 * timelineView.setOnAppointmentClickListener { appointment ->
 *     // Handle click
 * }
 * ```
 */
class TimelineScheduleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var config = TimelineConfig()
    private var appointments = listOf<Appointment>()
    private var onAppointmentClickListener: ((Appointment) -> Unit)? = null
    private var onAppointmentLongClickListener: ((Appointment) -> Unit)? = null

    private val overlapManager = OverlapManager()

    private val scrollView: ScrollView
    private val timelineContainer: FrameLayout
    private val timeColumn: LinearLayout
    private val appointmentContainer: FrameLayout
    private val currentTimeIndicatorView: View

    // Paint objects for drawing
    private val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val timePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val currentTimeLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val currentTimeDotPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val verticalDividerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Time range
    private var startHour = 0
    private var endHour = 24

    init {
        // Create main scroll view
        scrollView = ScrollView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        }

        // Create timeline container (holds time column + appointment container)
        timelineContainer = FrameLayout(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        }

        // Create time column (left side with hours)
        timeColumn = object : LinearLayout(context) {
            override fun dispatchDraw(canvas: Canvas) {
                super.dispatchDraw(canvas)
                // Draw horizontal dividers between hours
                drawTimeColumnDividers(canvas)
            }
        }.apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LayoutParams(
                config.timeColumnWidth.dpToPx(),
                LayoutParams.WRAP_CONTENT
            )
            setWillNotDraw(false)
        }

        // Create appointment container (right side with appointments)
        appointmentContainer = FrameLayout(context).apply {
            val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            params.marginStart = config.timeColumnWidth.dpToPx()
            layoutParams = params
            setWillNotDraw(false)
        }

        // Create current time indicator overlay (renders on top)
        currentTimeIndicatorView = object : View(context) {
            override fun onDraw(canvas: Canvas) {
                super.onDraw(canvas)
                if (config.showCurrentTimeIndicator) {
                    drawCurrentTimeIndicator(canvas)
                }
            }
        }.apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            setWillNotDraw(false)
        }

        // Add views to hierarchy
        timelineContainer.addView(timeColumn)
        timelineContainer.addView(appointmentContainer)
        timelineContainer.addView(currentTimeIndicatorView)  // Add on top
        scrollView.addView(timelineContainer)
        addView(scrollView)

        // Initialize paint
        setupPaint()

        // Build time labels
        buildTimeLabels()
    }

    private fun setupPaint() {
        gridPaint.apply {
            color = config.gridLineColor
            strokeWidth = config.gridLineWidth
            style = Paint.Style.STROKE
        }

        timePaint.apply {
            color = config.timeTextColor
            textSize = config.timeTextSize.spToPx()
            textAlign = Paint.Align.RIGHT
        }

        currentTimeLinePaint.apply {
            color = config.currentTimeIndicatorColor
            strokeWidth = config.currentTimeIndicatorWidth
            style = Paint.Style.STROKE
        }

        currentTimeDotPaint.apply {
            color = config.currentTimeIndicatorColor
            style = Paint.Style.FILL
        }

        verticalDividerPaint.apply {
            color = config.verticalDividerColor
            strokeWidth = config.verticalDividerWidth
            style = Paint.Style.STROKE
        }
    }

    /**
     * Set the configuration for the timeline
     */
    fun setConfig(config: TimelineConfig) {
        this.config = config
        setupPaint()
        buildTimeLabels()
        renderAppointments()
    }

    /**
     * Set the appointments to display
     */
    fun setAppointments(appointments: List<Appointment>) {
        this.appointments = appointments
        updateTimeRange()
        buildTimeLabels()
        renderAppointments()
    }

    /**
     * Set click listener for appointments
     */
    fun setOnAppointmentClickListener(listener: (Appointment) -> Unit) {
        this.onAppointmentClickListener = listener
    }

    /**
     * Set long click listener for appointments
     */
    fun setOnAppointmentLongClickListener(listener: (Appointment) -> Unit) {
        this.onAppointmentLongClickListener = listener
    }

    /**
     * Update time range based on appointments
     */
    private fun updateTimeRange() {
        if (appointments.isEmpty()) {
            startHour = 8
            endHour = 18
            return
        }

        var minHour = 24
        var maxHour = 0

        appointments.forEach { appointment ->
            val startH = TimeUtils.getHour(appointment.startTime)
            val endH = TimeUtils.getHour(appointment.endTime)

            if (startH < minHour) minHour = startH
            if (endH > maxHour) maxHour = endH
        }

        // Add padding
        startHour = maxOf(0, minHour - 1)
        endHour = minOf(24, maxHour + 2)
    }

    /**
     * Build time labels on the left side
     */
    private fun buildTimeLabels() {
        timeColumn.removeAllViews()

        val hourHeight = config.hourHeight.dpToPx()

        // Use custom labels if provided, otherwise use auto-generated
        val customLabels = config.customTimeLabels
        if (customLabels != null) {
            buildCustomTimeLabels(customLabels, hourHeight)
        } else {
            buildAutoTimeLabels(hourHeight)
        }
    }

    /**
     * Build custom time labels from provided list
     */
    private fun buildCustomTimeLabels(labels: List<String>, hourHeight: Int) {
        val totalHeight = hourHeight * labels.size

        labels.forEach { label ->
            val timeView = TextView(context).apply {
                text = label
                textSize = config.timeTextSize
                setTextColor(config.timeTextColor)
                gravity = android.view.Gravity.TOP or android.view.Gravity.END
                setPadding(0, 0, 8.dpToPx(), 0)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    hourHeight
                )
            }
            timeColumn.addView(timeView)
        }

        // Set heights
        updateContainerHeights(totalHeight)
    }

    /**
     * Build automatic time labels based on appointments
     */
    private fun buildAutoTimeLabels(hourHeight: Int) {
        val totalHours = endHour - startHour + 1
        val totalHeight = hourHeight * totalHours

        for (hour in startHour..endHour) {
            val timeView = TextView(context).apply {
                text = formatHourLabel(hour)
                textSize = config.timeTextSize
                setTextColor(config.timeTextColor)
                gravity = android.view.Gravity.TOP or android.view.Gravity.END
                setPadding(0, 0, 8.dpToPx(), 0)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    hourHeight
                )
            }
            timeColumn.addView(timeView)
        }

        // Set heights
        updateContainerHeights(totalHeight)
    }

    /**
     * Update container heights
     */
    private fun updateContainerHeights(totalHeight: Int) {
        // Set appointment container height to match time column
        val params = appointmentContainer.layoutParams as LayoutParams
        params.height = totalHeight
        params.marginStart = config.timeColumnWidth.dpToPx()
        appointmentContainer.layoutParams = params

        // Set current time indicator overlay height
        val indicatorParams = currentTimeIndicatorView.layoutParams as LayoutParams
        indicatorParams.height = totalHeight
        currentTimeIndicatorView.layoutParams = indicatorParams

        // Set background to draw grid lines
        appointmentContainer.setWillNotDraw(false)
        appointmentContainer.background = GridBackground()

        // Trigger redraw of current time indicator
        currentTimeIndicatorView.invalidate()
    }

    /**
     * Format hour label (e.g., "08:00" or "8:00 AM")
     */
    private fun formatHourLabel(hour: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, 0)
        return TimeUtils.formatTime(calendar.time, config.use24HourFormat)
    }

    /**
     * Render all appointments
     */
    private fun renderAppointments() {
        appointmentContainer.removeAllViews()

        if (appointments.isEmpty()) return

        // Calculate positions
        val positionedAppointments = overlapManager.calculatePositions(appointments)

        // Render each appointment
        positionedAppointments.forEach { positioned ->
            val appointmentView = createAppointmentView(positioned)
            appointmentContainer.addView(appointmentView)
        }
    }

    /**
     * Create view for a positioned appointment
     */
    private fun createAppointmentView(
        positioned: OverlapManager.PositionedAppointment
    ): View {
        val appointment = positioned.appointment

        // Inflate appointment card
        val cardView = LayoutInflater.from(context)
            .inflate(R.layout.item_appointment, appointmentContainer, false) as MaterialCardView

        // Set card properties
        cardView.apply {
            radius = config.cardCornerRadius.dpToPixels()
            cardElevation = config.cardElevation.dpToPixels()
            setCardBackgroundColor(appointment.backgroundColor)
        }

        // Find views
        val colorIndicator = cardView.findViewById<View>(R.id.colorIndicator)
        val avatarImage = cardView.findViewById<ImageView>(R.id.avatarImage)
        val titleText = cardView.findViewById<TextView>(R.id.titleText)
        val subtitleText = cardView.findViewById<TextView>(R.id.subtitleText)

        // Set content
        colorIndicator.setBackgroundColor(appointment.color)
        titleText.text = appointment.title
        titleText.setTextColor(appointment.textColor)
        titleText.textSize = config.titleTextSize

        if (appointment.subtitle != null) {
            subtitleText.visibility = VISIBLE
            subtitleText.text = appointment.subtitle
            subtitleText.textSize = config.subtitleTextSize
        } else {
            subtitleText.visibility = GONE
        }

        // Handle avatar
        if (config.showAvatar && appointment.avatarDrawableRes != null) {
            avatarImage.visibility = VISIBLE
            avatarImage.setImageResource(appointment.avatarDrawableRes)
        } else {
            avatarImage.visibility = GONE
        }

        // Calculate position and size
        val hourHeight = config.hourHeight.dpToPx()
        val startY = calculateYPosition(positioned.startMinute, hourHeight)
        val endY = calculateYPosition(positioned.endMinute, hourHeight)
        val height = maxOf(endY - startY, config.cardMinHeight.dpToPx())

        // Calculate width based on column
        // Use screen width if view width is not measured yet
        val totalWidth = if (width > 0) width else resources.displayMetrics.widthPixels
        val containerWidth = totalWidth - config.timeColumnWidth.dpToPx()
        val columnWidth = containerWidth / positioned.totalColumns
        val left = positioned.column * columnWidth
        val viewWidth = columnWidth - config.cardMargin.dpToPx() * 2

        // Set layout params
        val params = LayoutParams(viewWidth, height).apply {
            topMargin = startY
            leftMargin = left + config.cardMargin.dpToPx()
        }
        cardView.layoutParams = params

        // Set click listeners
        cardView.setOnClickListener {
            onAppointmentClickListener?.invoke(appointment)
        }

        cardView.setOnLongClickListener {
            onAppointmentLongClickListener?.invoke(appointment)
            true
        }

        return cardView
    }

    /**
     * Calculate Y position for a given minute of day
     */
    private fun calculateYPosition(minuteOfDay: Int, hourHeight: Int): Int {
        val hoursSinceStart = (minuteOfDay / 60.0) - startHour
        return (hoursSinceStart * hourHeight).toInt()
    }

    /**
     * Draw horizontal dividers in time column
     */
    private fun drawTimeColumnDividers(canvas: Canvas) {
        if (!config.showGridLines) return

        val hourHeight = config.hourHeight.dpToPx()
        val width = timeColumn.width

        // Get number of labels (custom or auto)
        val labelCount = config.customTimeLabels?.size ?: (endHour - startHour + 1)

        // Draw horizontal line at each label position
        for (i in 0 until labelCount) {
            val y = i * hourHeight.toFloat()
            canvas.drawLine(0f, y, width.toFloat(), y, gridPaint)
        }
    }

    /**
     * Draw current time indicator (called from overlay view)
     */
    private fun drawCurrentTimeIndicator(canvas: Canvas) {
        val now = Calendar.getInstance()
        val currentHour = now.get(Calendar.HOUR_OF_DAY)
        val currentMinute = now.get(Calendar.MINUTE)

        // Only draw if current time is within the visible range
        if (currentHour in startHour..endHour) {
            val hourHeight = config.hourHeight.dpToPx()
            val currentMinuteOfDay = currentHour * 60 + currentMinute
            val y = calculateYPosition(currentMinuteOfDay, hourHeight).toFloat()

            // Get time column width to position dot in time column
            val timeColumnWidth = config.timeColumnWidth.dpToPx()
            val dotRadius = config.currentTimeDotRadius.dpToPixels()

            // Draw dot in the time column area (on the left)
            val dotX = timeColumnWidth - dotRadius - 4.dpToPx()
            canvas.drawCircle(dotX, y, dotRadius, currentTimeDotPaint)

            // Draw line from dot edge (menyatu with dot) to the end
            val lineStartX = dotX + dotRadius // Start from right edge of dot
            canvas.drawLine(
                lineStartX,
                y,
                width.toFloat(),
                y,
                currentTimeLinePaint
            )
        }
    }

    /**
     * Custom drawable for grid background
     */
    private inner class GridBackground : android.graphics.drawable.Drawable() {
        override fun draw(canvas: Canvas) {
            val hourHeight = config.hourHeight.dpToPx()
            val width = bounds.width()

            // Get number of labels (custom or auto)
            val labelCount = config.customTimeLabels?.size ?: (endHour - startHour + 1)

            // Draw horizontal grid lines
            if (config.showGridLines) {
                for (i in 0 until labelCount) {
                    val y = i * hourHeight.toFloat()
                    canvas.drawLine(0f, y, width.toFloat(), y, gridPaint)
                }
            }

            // Draw vertical divider between time column and appointments
            if (config.showVerticalDivider) {
                canvas.drawLine(0f, 0f, 0f, bounds.height().toFloat(), verticalDividerPaint)
            }
        }

        override fun setAlpha(alpha: Int) {
            gridPaint.alpha = alpha
            currentTimeLinePaint.alpha = alpha
            currentTimeDotPaint.alpha = alpha
            verticalDividerPaint.alpha = alpha
        }

        override fun setColorFilter(colorFilter: android.graphics.ColorFilter?) {
            gridPaint.colorFilter = colorFilter
            currentTimeLinePaint.colorFilter = colorFilter
            currentTimeDotPaint.colorFilter = colorFilter
            verticalDividerPaint.colorFilter = colorFilter
        }

        @Deprecated("Deprecated in Java")
        override fun getOpacity(): Int = android.graphics.PixelFormat.TRANSLUCENT
    }

    /**
     * Extension function to convert dp to pixels
     */
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    /**
     * Extension function to convert Float dp to pixels
     */
    private fun Float.dpToPixels(): Float {
        return this * resources.displayMetrics.density
    }

    /**
     * Extension function to convert sp to pixels
     */
    private fun Float.spToPx(): Float {
        return this * resources.displayMetrics.scaledDensity
    }
}

