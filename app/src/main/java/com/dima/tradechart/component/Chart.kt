package com.dima.tradechart.component

import com.dima.tradechart.series.LineSeries

class Chart(val height: Int = 0, var width: Int = 0) : ChartConfig {
    private val typeChart = TypeChart.LINE
    var isAlreadyInit = false
    var myLineSeries = LineSeries()
    var isEndChartVisible = true

    var frame = 1
    var pointOnChart = 40

    var screenStartPosition = 0

    init {
        myLineSeries.lineSeries = data()
        screenStartPosition = (myLineSeries.lineSeries.size - pointOnChart)
    }

    private val dY = myLineSeries.maxY - myLineSeries.minY

    val chartHeight = height - 50f
    val chartWidth = width - offsetRight

    fun getSceneXValue(position: Int): Float = (position) * chartWidth / pointOnChart

    @Synchronized
    fun getSceneYValue(bid: Double): Float = ((bid - getMinY()) * chartHeight / (getMaxY() - getMinY())).toFloat()

    fun getMinY() = myLineSeries.minY - dY
    fun getMaxY() = myLineSeries.maxY + dY

    fun updateLastQuote(lineSeries: Quote) {
        myLineSeries.addLastQuote(lineSeries, frame.toDouble())
        if (isEndChartVisible)
            screenStartPosition++
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

    fun scale(isZoomOut: Boolean) {
        when {
            screenStartPosition == 1 || pointOnChart >= 160 -> return
            pointOnChart == myLineSeries.lineSeries.size - 1 && isZoomOut || pointOnChart <= 20 && !isZoomOut -> return
            isZoomOut -> pointOnChart += 10
            else -> pointOnChart -= 10
        }
    }

    fun getEndScreenPosition(): Int {
        return when {
            screenStartPosition == 0 && pointOnChart <= myLineSeries.lineSeries.size -> screenStartPosition + pointOnChart
            screenStartPosition + pointOnChart >= myLineSeries.lineSeries.size -> {
                isEndChartVisible = true
                myLineSeries.lineSeries.size - 1
            }
            else -> {
                isEndChartVisible = false
                screenStartPosition + pointOnChart
            }
        }
    }

    override fun toString(): String {
        return "Chart(height=$height, width=$width, typeChart=$typeChart)"
    }
}