package com.text.traderchart.chart.component

import android.graphics.Canvas
import android.widget.Scroller
import com.dima.tradechart.component.ChartConfig.offsetBottom
import com.dima.tradechart.component.ChartConfig.offsetRight
import com.dima.tradechart.model.*
import com.dima.tradechart.render.GridRender
import com.dima.tradechart.render.LineRender
import com.dima.tradechart.render.XYAxisRender
import com.dima.tradechart.series.LineSeries

class Chart(val height: Int = 0, var width: Int = 0) {
    private val typeChart = TypeChart.LINE

    var isChartInit = false
    var mySeries: BaseSeries<BaseQuote> = LineSeries()
    private var isEndChartVisible = true

    var frame = 1

    private val dY = mySeries.maxY - mySeries.minY

    private var itScrolling: Scroller? = null
    var isLeft = false

    val chartHeight = height - 50f
    val chartWidth = width - offsetRight

    private val axisXYRender = XYAxisRender(this)
    private val lineRender: BaseRender = LineRender(this)
    private val gridRender = GridRender(this)

    private fun getMinY() = mySeries.minY - dY
    private fun getMaxY() = mySeries.maxY + dY


    init {
//        mySeries.allSeries = data()
//        isChartInit = true
    }

    fun getSceneXValue(x: Double): Float =
        ((x - mySeries.minX) * chartWidth / (mySeries.maxX - mySeries.minX).toFloat()).toFloat()

    fun getSceneYValue(bid: Double): Float =
        ((chartHeight - (bid - getMinY()) * chartHeight / (getMaxY() - getMinY()) - offsetBottom).toFloat())

    fun updateLastQuote(lineSeries: Quote) {
        mySeries.addOnePoint(lineSeries, frame.toDouble())
//        if (isEndChartVisible)
//            screenStartPosition++

    }

    fun scrolling(needGoToLeft: Boolean): Boolean {
//        return mySeries.moveAt(needGoToLeft)
        return false
    }

    fun scale(isZoomOut: Boolean) {
//        mySeries.scale(isZoomOut)
    }

    fun drawRenders(canvas: Canvas, ts: Long) {
        axisXYRender.draw(canvas)
        gridRender.draw(canvas, mySeries)
        lineRender.draw(canvas, mySeries as LineSeries)

//        if (itScrolling?.computeScrollOffset() == true) {
//            scrolling(isLeft)
//        }
    }

    fun setScroller(scrolling: Scroller?, value: Boolean = false) {
        itScrolling = scrolling
        isLeft = value
    }

    fun setSeries(mySeries: BaseSeries<BaseQuote>) {
        isChartInit = false
        this.mySeries = mySeries
        isChartInit = true
    }

    fun getSeries() = mySeries



    override fun toString(): String {
        return "Chart(height=$height, width=$width, typeChart=$typeChart)"
    }
}