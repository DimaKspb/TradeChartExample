package com.text.traderchart.chart.component

import android.util.Log
import com.text.traderchart.chart.component.ChartConfig.offsetBottom
import com.text.traderchart.chart.component.ChartConfig.offsetRight
import com.text.traderchart.chart.series.LineSeries
import com.text.traderchart.chart.model.*
import com.text.traderchart.chart.utils.Animator

class Chart {
    private var typeChart = TypeChart.LINE
    var mySeries: BaseSeries<BaseQuote> = LineSeries()
    var animator = Animator(1000)

    var height = 0f
    var width = 0f
    var heightWithPadding = 0f
    var widthWithPadding = 0f

    var isChartInit = false
    var frame = 1

    fun initSize(height: Int = 0, width: Int = 0) {
        logging("init size: $height , $width")

        this.height = height.toFloat()
        this.width = width.toFloat()

        heightWithPadding = height - 50f
        widthWithPadding = width - offsetRight
    }

    var minY = 0f
    var maxY = 0f
    var minX = 0f
    var maxX = 0f

    fun getSceneXValue(time: Double): Float {
        return (((time - minX) * widthWithPadding / (maxX - minX)).toFloat())
    }

    fun getSceneYValue(bid: Double): Float {
        return (((heightWithPadding - (bid - minY) * heightWithPadding / (maxY - minY) - offsetBottom).toFloat()))
    }

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
        fractal = 1f
        isChartInit = true
    }

    fun setSeriesWithAnimate(mySeries: BaseSeries<BaseQuote>) {
        this.mySeries = mySeries
        updateExtremes()
    }

    var fractal = 0f
    private fun updateExtremes() {
        minX = (mySeries.minX).toFloat()
        maxX = (mySeries.maxX).toFloat()
        minY = (mySeries.minY).toFloat()
        maxY = (mySeries.maxY).toFloat()

        animator.getProgress {
            fractal = it
            Log.d("dssadads", "$it")
        }
    }

    fun getSeries() = mySeries

    fun copyThis(chart: Chart) {
        typeChart = chart.typeChart
        isChartInit = chart.isChartInit
        frame = chart.frame
        mySeries = chart.getSeries()

        initSize(chart.height.toInt(), chart.width.toInt())
    }

    override fun toString(): String {
        return "Chart(height=$height, width=$width, typeChart=$typeChart ,countSeries=${mySeries.getData().size})"
    }

    fun updateAnimator() {
        animator.onUpdate()
    }
}
