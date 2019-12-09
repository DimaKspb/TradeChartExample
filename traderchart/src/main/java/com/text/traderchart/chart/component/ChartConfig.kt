package com.dima.tradechart.component

import android.util.DisplayMetrics


object ChartConfig {
    private var displayMetrics: DisplayMetrics? = null

    fun setDisplayMetrics(displayMetrics: DisplayMetrics?) {
        this.displayMetrics = displayMetrics
    }

    const val offsetRight = 140f
    const val offsetBottom = 60f

    var pointOnChart = 40

    fun convertDpToPx(dp: Float): Float {
        return dp * (displayMetrics?.density ?: 1f)
    }
}
