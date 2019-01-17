package com.dima.tradechart.component

import android.util.Log
import com.dima.tradechart.series.LineSeries

class Chart(val height: Int = 0, var width: Int = 0) : ChartConfig {
    private val typeChart = TypeChart.LINE
    var isAlreadyInit = false
    var myLineSeries = LineSeries()

    var frame = 1
    var pointOnChart = 40
    var scaleFactor = 0

    private var screenStartPosition = 0

    init {
        myLineSeries.lineSeries = data()
        screenStartPosition = (myLineSeries.lineSeries.size - pointOnChart)
    }

    private val dY = myLineSeries.maxY - myLineSeries.minY

    private val renderHeight = height - 50f
    private val renderWidth = width - offsetRight

    fun getSceneXValue(position: Int): Float = (position) * renderWidth / pointOnChart
    fun getSceneYValue(bid: Double): Float = ((bid - getMinY()) * renderHeight / (getMaxY() - getMinY())).toFloat()

    fun getMinY() = myLineSeries.minY - dY
    fun getMaxY() = myLineSeries.maxY + dY

    fun updateLastQuote(lineSeries: Quote) {
        screenStartPosition++
        myLineSeries.addLastQuote(lineSeries, frame.toDouble())
    }

    @Synchronized
    fun scrolling(needGoToLeft: Boolean): Boolean {
        when {
            screenStartPosition == 0 && needGoToLeft -> return false
            screenStartPosition >= (myLineSeries.lineSeries.size - (pointOnChart / 2)) && !needGoToLeft -> return false
            needGoToLeft -> screenStartPosition--
            !needGoToLeft -> screenStartPosition++
        }

        return true
    }

//    fun scale() {
//        Log.d("ChartScale", "${screenStartPosition - scaleFactor}")
//        if ((screenStartPosition - scaleFactor) < 0) return
//
//        scaleFactor += frame
//        Log.d("Scale", "$scaleFactor")
//    }

    fun getStartScreenPosition(): Int {
        return if (screenStartPosition < 0) {
            0
        } else
            screenStartPosition
    }

    fun getEndScreenPosition(): Int {
        return when {
            screenStartPosition == 0 -> screenStartPosition + pointOnChart
            screenStartPosition + pointOnChart >= myLineSeries.lineSeries.size -> myLineSeries.lineSeries.size
            else -> screenStartPosition + pointOnChart + 1
        }
    }

    override fun toString(): String {
        return "Chart(height=$height, width=$width, typeChart=$typeChart)"
    }
}