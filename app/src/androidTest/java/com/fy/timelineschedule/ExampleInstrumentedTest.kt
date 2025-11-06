/*
 * Timeline Schedule - Android Library
 *
 * ⚠️ AI-GENERATED PROJECT DISCLAIMER
 * This project was created with the assistance of Artificial Intelligence (AI).
 * While the code has been reviewed and tested, users should verify functionality
 * for their specific use cases.
 *
 * Copyright (c) 2025 Fadhy Yusuf
 * Licensed under the MIT License
 */

package com.fy.timelineschedule

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.fy.timelineschedule", appContext.packageName)
    }
}