package com.text.traderchart.chart.component

import android.util.DisplayMetrics
import android.util.Log
import kotlin.reflect.KClass


object ChartConfig {
    var logging = false
    private var displayMetrics: DisplayMetrics? = null

    fun setDisplayMetrics(displayMetrics: DisplayMetrics?) {
        ChartConfig.displayMetrics = displayMetrics
    }

    const val offsetRight = 140f
    const val offsetBottom = 60f

    var pointOnChart = 40

    fun convertDpToPx(dp: Float): Float {
        return dp * (displayMetrics?.density ?: 1f)
    }
}

fun <T : Any> T.logging(message: String) {
    if (ChartConfig.logging)
        Log.d("#!?${this::class.java.simpleName}", message)
}

fun <T : Any> T.logging(message: Long) {
    if (ChartConfig.logging)
        Log.d("#!?${this::class.java.simpleName}", message.toString())
}






