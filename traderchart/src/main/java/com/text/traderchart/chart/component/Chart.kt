package com.text.traderchart.chart.component

import android.graphics.Canvas
import com.text.traderchart.chart.component.ChartConfig.offsetBottom
import com.text.traderchart.chart.component.ChartConfig.offsetRight
import com.text.traderchart.chart.render.GridRender
import com.text.traderchart.chart.render.LineRender
import com.text.traderchart.chart.render.XYAxisRender
import com.text.traderchart.chart.series.LineSeries
import com.text.traderchart.chart.model.*

class Chart(val height: Int = 0, var width: Int = 0) {
    private val typeChart = TypeChart.LINE

    var isChartInit = false
    var mySeries: BaseSeries<BaseQuote> = LineSeries()
    private var isEndChartVisible = true

    var frame = 1

    private val dY = mySeries.maxY - mySeries.minY

    var isLeft = false

    val chartHeight = height - 50f
    val chartWidth = width - offsetRight

    val myRenders = arrayListOf(XYAxisRender(this), LineRender(this), GridRender(this))

    private fun getMinY() = mySeries.minY - dY
    private fun getMaxY() = mySeries.maxY + dY


    fun getSceneXValue(x: Double): Float =
        ((x - mySeries.minX) * chartWidth / (mySeries.maxX - mySeries.minX).toFloat()).toFloat()

    fun getSceneYValue(bid: Double): Float =
        ((chartHeight - (bid - getMinY()) * chartHeight / (getMaxY() - getMinY()) - offsetBottom).toFloat())

    fun updateLastQuote(lineSeries: Quote) {
        mySeries.addOnePoint(lineSeries, frame.toDouble())
    }

    fun scrolling(needGoToLeft: Boolean): Boolean {
//        return mySeries.moveAt(needGoToLeft)
        return false
    }

    fun scale(isZoomOut: Boolean) {
//        mySeries.scale(isZoomOut)
    }

    fun drawRenders(canvas: Canvas, ts: Long) {
        for (i in myRenders) {
            i.draw(canvas, mySeries)
        }
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