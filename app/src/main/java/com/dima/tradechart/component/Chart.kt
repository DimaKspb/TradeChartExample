package com.dima.tradechart.component

import android.graphics.Canvas
import android.widget.Scroller
import com.dima.tradechart.render.GridRender
import com.dima.tradechart.render.LineRender
import com.dima.tradechart.render.XYAxisRender
import com.dima.tradechart.series.LineSeries

class Chart(val height: Int = 0, var width: Int = 0) : ChartConfig {
    private val typeChart = TypeChart.LINE
    var isAlreadyInit = false
    var myLineSeries = LineSeries()
    var isEndChartVisible = true

    var frame = 1
    var pointOnChart = 40

    var screenStartPosition = 0
    private val dY = myLineSeries.maxY - myLineSeries.minY

    val chartHeight = height - 50f
    val chartWidth = width - offsetRight

    private val axisXYRender = XYAxisRender(this)
    private val lineRender = LineRender(this)
    private val gridRender = GridRender(this)

    init {
        myLineSeries.lineSeries = data()
        screenStartPosition = (myLineSeries.lineSeries.size - pointOnChart)
        isAlreadyInit = true
    }

    fun getSceneXValue(position: Int): Float = (position) * chartWidth / pointOnChart

    fun getSceneYValue(bid: Double): Float =
        ((chartHeight - (bid - getMinY()) * chartHeight / (getMaxY() - getMinY()) - offsetBottom).toFloat())

    private fun getMinY() = myLineSeries.minY - dY
    private fun getMaxY() = myLineSeries.maxY + dY

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

    fun drawRenders(canvas: Canvas) {
        axisXYRender.draw(canvas)
        lineRender.draw(canvas, myLineSeries)
        gridRender.draw(canvas, myLineSeries)

        if (itScrolling?.computeScrollOffset() == true) {
            scrolling(isLeft)
        }
    }

    private var itScrolling: Scroller? = null
    var isLeft = false

    fun setScroller(scrolling: Scroller?, value: Boolean = false) {
        itScrolling = scrolling
        isLeft = value
    }
}