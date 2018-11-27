package com.dima.tradechart.component

import android.graphics.Color
import android.graphics.Paint

interface ChartConfig {
    val offsetRight: Float
        get() = 20f
    val offsetBottom: Float
        get() = 20f

    val paintLine: Paint
        get() = Paint().apply {
            color = Color.BLUE
            style = Paint.Style.STROKE
            isAntiAlias = false
            strokeWidth = 2f
        }

}
