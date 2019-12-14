package com.text.traderchart.chart.component

import com.text.traderchart.chart.component.ChartConfig.offsetBottom
import com.text.traderchart.chart.component.ChartConfig.offsetRight
import com.text.traderchart.chart.series.LineSeries
import com.text.traderchart.chart.model.*

class Chart {
    private val typeChart = TypeChart.LINE

    var isChartInit = false
    var mySeries: BaseSeries<BaseQuote> = LineSeries()

    var frame = 1

    private val dY = mySeries.maxY - mySeries.minY

    var height = 0f
    var width = 0f
    var heightWithPadding = 0f
    var widthWithPadding = 0f

    fun initSize(height: Int = 0, width: Int = 0) {
        logging("init size: $height , $width")

        this.height = height.toFloat()
        this.width = width.toFloat()

        heightWithPadding = height - 50f
        widthWithPadding = width - offsetRight
    }

    private fun getMinY() = mySeries.minY - dY
    private fun getMaxY() = mySeries.maxY + dY


    fun getSceneXValue(x: Double): Float = ((x - mySeries.minX) * widthWithPadding / (mySeries.maxX - mySeries.minX).toFloat()).toFloat()

    fun getSceneYValue(bid: Double): Float = ((heightWithPadding - (bid - getMinY()) * heightWithPadding / (getMaxY() - getMinY()) - offsetBottom).toFloat())

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

    fun setSeries(mySeries: BaseSeries<BaseQuote>) {
        isChartInit = false
        this.mySeries = mySeries
        isChartInit = true
    }

    fun getSeries() = mySeries


    override fun toString(): String {
        return "Chart(height=$height, width=$width, typeChart=$typeChart ,countSeries=${mySeries.getData().size})"
    }
}
